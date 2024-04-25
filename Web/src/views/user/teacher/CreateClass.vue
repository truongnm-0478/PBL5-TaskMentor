<template>
    <div class="container">
        <a-form
            :model="formState"
            name="nest-messages"
            layout="vertical"
        >
            <a-form-item label="Name" :rules="[{ required: true, message: 'Please input name!' }]">
                <a-input v-model:value="formState.information.name"/>
            </a-form-item>
            <a-form-item label="Introduction" :rules="[{ required: true, message: 'Please input introduction!' }]">
                <a-textarea v-model:value="formState.information.introduction" :auto-size="{ minRows: 6}"/>
            </a-form-item>
            <a-button :disabled="!isValid(current)" type="primary" @click="submit">Create</a-button>
        </a-form>
    </div>
    <div class="steps-action">
    </div>
</template>

<script setup>
import {reactive, ref} from 'vue'
import {message} from 'ant-design-vue'
import {useUserStore} from '@/stores/userStore.js'
import classApi from "@/repositories/classApi.js";
import {useMessageStore} from "@/stores/messageStore.js";
import {useSpinStore} from "@/stores/spinStore.js";
import router from "@/router/index.js";

const current = ref(0)
const userStore = useUserStore()
const generatedCode = ref('')
const messageStore = useMessageStore()
const spinStore = useSpinStore()
const formState = reactive({
    information: {
        name: '',
        introduction: '',
        year: null,
    },
})

const currentYear = new Date().getFullYear()

const generateCodeAndQR = () => {
    const teacherName = userStore.getUsername
    let number = 0

    for (let i = 0; i < teacherName.length; i++) {
        number = (number + teacherName.charCodeAt(i)) % 100000
    }
    const currentDate = new Date()
    const day = currentDate.getDate()
    const month = currentDate.getMonth() + 1
    const year = currentDate.getFullYear()
    const hours = currentDate.getHours()
    const minutes = currentDate.getMinutes()
    const seconds = currentDate.getSeconds()
    generatedCode.value = `${year}${month}${day}${hours}${minutes}${seconds}${number}`
}
generateCodeAndQR()

const submit = async () => {
    spinStore.startLoading()
    await classApi.createClass({
        name: formState.information.name,
        year: currentYear,
        description: formState.information.introduction,
        code: generatedCode.value
    })
        .then(res => {
            spinStore.stopLoading()
            messageStore.addMessage('success', res.message)
            router.push({ path: '/QRAndCode', query: { code: res.data.code } });
        })
        .catch(err => {
            spinStore.stopLoading()
            messageStore.addMessage('error', 'An error occurred while logging in')
        })
}

const onFinish = () => {
    if (isValid(current.value)) {
        message.success('Processing complete!');
    }
}

const isValid = (step) => {
    const nameValid = formState.information.name.trim() !== ''
    const introductionValid = formState.information.introduction.trim() !== ''
    return !(!nameValid || !introductionValid)
}

</script>

<style scoped>
.container {
    padding: 50px 20px;
    background-color: var(--color-white);
    border-radius: 10px;
    height: calc(100vh - 106px);
    overflow: auto;
}

.steps-content {
    margin-top: 16px;
    border: 1px dashed #e9e9e9;
    border-radius: 6px;
    background-color: #fafafa;
    min-height: 200px;
    text-align: center;
    padding-top: 80px;
}

.steps-action {
    margin-top: 24px;
}
</style>
