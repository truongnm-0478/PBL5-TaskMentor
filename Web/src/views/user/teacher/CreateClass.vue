<template>
    <div class="container">
        <a-steps :current="current" :items="items"></a-steps>
        <div class="steps-content">
            <div v-if="current === 0" class="step">
                <a-form
                    :model="formState"
                    name="nest-messages"
                    @finish="onFinish"
                    layout="vertical"
                >
                    <a-form-item label="Name" :rules="[{ required: true, message: 'Please input name!' }]">
                        <a-input v-model:value="formState.information.name" />
                    </a-form-item>
                    <a-form-item label="Introduction" :rules="[{ required: true, message: 'Please input introduction!' }]">
                        <a-textarea v-model:value="formState.information.introduction" :auto-size="{ minRows: 6}" />
                    </a-form-item>
                </a-form>
            </div>
            <div v-if="current === 1" class="step">
                <a-form layout="vertical">
                    <a-form-item label="Student ID number: " :rules="[{ required: true, message: 'Please paste users!' }]">
                        <a-textarea v-model:value="pastedUsers" :auto-size="{ minRows: 8, maxRows: 8 }" />
                    </a-form-item>
                    <a-button type="primary" @click="convertToList">Convert to List</a-button>
                </a-form>
            </div>
            <div v-if="current === 2" class="step">
                <a-space direction="vertical" align="center">
                    <a-qrcode
                        :value="generatedCode"
                        color="#4880FF"
                        bg-color="#FFFFFF"
                        error-level="H"
                        size="210"
                    />
                    <a-input-group compact>
                        <a-input readonly v-model:value="generatedCode" style="width: calc(100% - 36px); color: var(--color-blue); background-color: var(--color-white)" />
                        <a-tooltip title="Copy git URL">
                            <template #default>
                                <a-button type="default" @click="copyToClipboard">
                                    <template #icon><CopyOutlined /></template>
                                </a-button>
                            </template>
                        </a-tooltip>
                    </a-input-group>
                </a-space>
            </div>
        </div>
        <div class="steps-action">
            <a-button v-if="current < steps.length - 1 && isStepValid(current)" type="primary" @click="next">Next</a-button>
            <a-button v-if="current === steps.length - 1" type="primary" @click="onFinish">
                Done
            </a-button>
            <a-button v-if="current > 0" style="margin-left: 8px" @click="prev">Previous</a-button>
        </div>
    </div>
</template>

<script setup>
import { ref, reactive } from 'vue'
import { message } from 'ant-design-vue'
import { CopyOutlined } from "@ant-design/icons-vue"


const current = ref(0)
const pastedUsers = ref('')
const userList = ref([])

const generatedCode = ref('')


// Phương thức để tạo mã code có 6 ký tự và mã QR tương ứng
const generateCodeAndQR = () => {
    const currentDate = new Date().toLocaleDateString()
    const userId = '123456' // Thay bằng id của user thực tế
    // Kết hợp ngày tháng và id của user để tạo mã code
    const code = currentDate.replace(/\//g, '') + userId
    // Gán mã code vào biến generatedCode
    generatedCode.value = code

}
generateCodeAndQR()

const copyToClipboard = () => {
    const inputElement = document.querySelector('input[type="text"]')
    if (inputElement) {
        inputElement.select()
        document.execCommand('copy')
        message.success('Copied to clipboard')
    }
}



const convertToList = () => {
    userList.value = pastedUsers.value.split('\n').filter(user => user.trim() !== '')
    console.log(userList)
}

const next = () => {
    if (isStepValid(current.value)) {
        current.value++;
    }
};
const prev = () => {
    current.value--;
};
const steps = ['Information', 'Add Members', 'Done']
const items = steps.map((title) => ({
    key: title,
    title: title,
}))

const formState = reactive({
    information: {
        name: '',
        introduction: '',
    },
})
const onFinish = () => {
    if (isStepValid(current.value)) {
        message.success('Processing complete!');
    }
}
const isStepValid = (step) => {
    if (step === 0) {
        const nameValid = formState.information.name.trim() !== ''
        const introductionValid = formState.information.introduction.trim() !== ''

        if (!nameValid || !introductionValid) {
            return false
        }
    }
    return true
}
</script>

<style scoped>
.container {
    padding: 50px 20px;
    background-color: var(--color-white);
    border-radius: 10px;
    height: calc(100vh - 32px);
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

.step {
    margin: 10px 50px 100px;
}

</style>
