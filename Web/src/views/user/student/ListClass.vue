<template>
    <a-space direction="vertical" style="width: 100%; padding-bottom: 20px" gap="middle">
        <a-flex wrap="wrap" gap="large">
            <template v-if="listClass.length">
                <a-badge-ribbon v-for="item in listClass" :key="item.id" :text="item.year" :color="getBadgeColor(item.year)">
                    <a-card :bordered="false" :class="['card']">
                        <div class="avatar">
                            <a-avatar size="large" :style="{ backgroundColor: getColorForLastLetter(item.name) }">{{ getLastLetter(item.name) }}</a-avatar>
                        </div>
                        <p @click="handleNameClick(item)" style="margin-top: 20px">{{ item.name }}</p>
                        <template #actions>
                            <ExclamationCircleOutlined key="edit" @click="showEditModal(item)" />
                            <a-popover style="width: 300px" trigger="click" placement="bottom">
                                <template #content>
                                    <a-button type="text" style="width: 100%" @click="showQRAndCode(item)"><UsergroupAddOutlined style="margin-right: 10px"/>Add menber  </a-button>
                                </template>
                                <template #default><EllipsisOutlined /></template>
                            </a-popover>
                        </template>
                    </a-card>
                </a-badge-ribbon>
            </template>
            <template v-else>
                <a-card :bordered="false" style="width: 100%; text-align: center;">
                    <a-empty />
                </a-card>
            </template>
        </a-flex>
        <a-modal v-model:open="isModalOpen" title="Class Information" @cancel="handleCancel" @ok="handleOk" >
            <a-form-item label="Class Name" :label-col="{ span: 24 }">
                <a-input v-model:value="editedClassName" readonly="true" />
                <span style="color: red">{{ messageErr }}</span>
            </a-form-item>
            <a-form-item label="Class Description" :label-col="{ span: 24 }">
                <a-textarea v-model:value="editedClassDescription" readonly="true" />
            </a-form-item>
            <a-form-item label="Class Code" :label-col="{ span: 24 }" style="display: none">
                <a-input v-model:value="editedClassCode" />
            </a-form-item>

            <template #footer>
                <a-button key="cancel" @click="handleCancel">Cancel</a-button>
            </template>
        </a-modal>
    </a-space>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { ExclamationCircleOutlined, EllipsisOutlined, UsergroupAddOutlined } from '@ant-design/icons-vue'
import classApi from '@/repositories/classApi.js'
import { useMessageStore } from "@/stores/messageStore.js";
import { useSpinStore } from "@/stores/spinStore.js";
import router from "@/router/index.js"

const listClass = ref([])
const isModalOpen = ref(false)
const editedClassName = ref('')
const editedClassDescription = ref('')
const messageErr = ref('')
const editedClassCode = ref('')
const messageStore = useMessageStore()
const spinStore = useSpinStore()

const getListClass = () => {
    classApi.getListJoinedClass()
        .then(res => {
            listClass.value = res.data
        })
}
onMounted(getListClass)

const getLastLetter = (name) => {
    return name.charAt(name.length - 1).toUpperCase()
}

const getColorForLastLetter = (name) => {
    const colors = ['#1A5DB6', '#2E8B57', '#FF8C00', '#FF1493', '#8A2BE2']
    const lastLetter = getLastLetter(name)
    const index = lastLetter.charCodeAt(0) % colors.length
    return colors[index]
}

const getBadgeColor = (year) => {
    const currentYear = new Date().getFullYear()
    return year === currentYear ? '#4880FF' : '#ccc'
}

const showEditModal = (item) => {
    editedClassName.value = item.name
    editedClassDescription.value = item.description
    editedClassCode.value = item.code
    isModalOpen.value = true
}

const handleCancel = () => {
    isModalOpen.value = false
}

const validateForm = () => {
    if (!editedClassName.value.trim()) {
        messageErr.value = 'Class name is required'
        return false
    }
    return true
}

const handleOk = async () => {
    spinStore.startLoading()
    if (validateForm()) {
        try {
            await classApi.updateClassInfo({
                name: editedClassName.value,
                description: editedClassDescription.value,
                code: editedClassCode.value
            })
            spinStore.stopLoading()
            messageStore.addMessage('success', 'Successfully!')
            getListClass()
            isModalOpen.value = false
        } catch (error) {
            spinStore.stopLoading()
            messageStore.addMessage('error', 'Something went wrong!')
        }
    }
}

const showQRAndCode = (item) => {
    router.push({path: '/QRAndCode', query: {code: item.code}})
}

const handleNameClick = (item) => {
    router.push({ path: '/class', query: { code: item.code } })
}

</script>

<style scoped>
.card {
    width: 265px;
    height: 200px;
    cursor: pointer;
}

.avatar {
    display: flex;
    justify-content: center;
    align-items: center;
    width: 100%;
}
</style>
