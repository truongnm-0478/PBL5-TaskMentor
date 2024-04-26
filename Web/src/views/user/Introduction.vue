<template>
    <a-descriptions layout="vertical" bordered>
        <a-descriptions-item :span="3" label="Name">{{ classIntroduction.name }}</a-descriptions-item>
        <a-descriptions-item label="Class code">
            <a-input-group compact class="input-group">
                <a-input readonly v-model:value="generatedCode" class="input" />
                <a-tooltip title="Copy git URL">
                    <template #default>
                        <a-button type="default" @click="copyToClipboard" class="copy-button">
                            <template #icon><CopyOutlined /></template>
                        </a-button>
                    </template>
                </a-tooltip>
            </a-input-group>
        </a-descriptions-item>
        <a-descriptions-item label="Config Info">
            <a-qrcode
                :value="generatedCode"
                color="#4880FF"
                bg-color="#FFFFFF"
                error-level="H"
                size="100"
            />
        </a-descriptions-item>
        <a-descriptions-item label="Create time">
            <a-badge status="processing" />
            {{ classIntroduction.createDate }}
        </a-descriptions-item>
        <a-descriptions-item label="Teacher">{{ classIntroduction.teacherName }} ({{ classIntroduction.teacherUsername }})</a-descriptions-item>
        <a-descriptions-item label="Phone ">{{ classIntroduction.teacherPhone }}</a-descriptions-item>
        <a-descriptions-item label="Email">{{ classIntroduction.teacherEmail }}</a-descriptions-item>
        <a-descriptions-item label="Description" :span="3">{{ classIntroduction.description }}</a-descriptions-item>
    </a-descriptions>
</template>
<script setup>
import classApi from '@/repositories/classApi.js'
import router from '@/router/index.js'
import { onMounted, ref } from 'vue'
import dayjs from 'dayjs'
import { CopyOutlined } from '@ant-design/icons-vue'
import { useRoute } from "vue-router";
import { message } from 'ant-design-vue'

const route = useRoute()
const generatedCode = ref(route.query.code)
const copyToClipboard = () => {
    const inputElement = document.querySelector('input[type="text"]')
    if (inputElement) {
        inputElement.select()
        document.execCommand('copy')
        message.success('Copied to clipboard')
    }
}

const code = router.currentRoute.value.query.code
const classIntroduction = ref({
    name: '',
    description: '',
    teacherName: '',
    teacherUsername: '',
    teacherPhone: '',
    teacherEmail: '',
    code: '',
    createDate: dayjs(),
})


const getClassIntroduction = () => {
    classApi.getClassIntroduction(code)
        .then(res => {
            classIntroduction.value = {
                name: res.data.name,
                description: res.data.description,
                teacherName: res.data.teacher.user.name,
                teacherUsername: res.data.teacher.user.username,
                teacherPhone: res.data.teacher.user.phone,
                teacherEmail: res.data.teacher.user.email,
                code: res.data.code,
                createDate: dayjs(res.data.createDate).format('MMMM D, YYYY')
            }
        })
}
onMounted(() => { getClassIntroduction() })
</script>

<style scoped>

.input-group {
    width: 300px;
}

.input {
    width: calc(100% - 36px);
    color: var(--color-blue);
    background-color: var(--color-white);
}

</style>