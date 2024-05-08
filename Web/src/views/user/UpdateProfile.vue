<template>
    <div class="update-container">
        <h2 class="header">Update profile</h2>
        <div class="body">
            <a-form
                class="form"
                ref="formRef"
                name="custom-validation"
                :model="formState"
                :rules="rules"
                layout="vertical"
                @finish="handleFinish"
                @validate="handleValidate"
                @finishFailed="handleFinishFailed"
            >
                <a-row>
                    <a-col :span="11">
                        <a-form-item has-feedback label="Username" name="username">
                            <a-input v-model:value="formState.username" type="text" />
                        </a-form-item>
                    </a-col>
                    <a-col :span="2"></a-col>
                    <a-col :span="11">
                        <a-form-item has-feedback label="Phone number" name="phone">
                            <a-input v-model:value="formState.phone" type="phone" />
                        </a-form-item>
                    </a-col>
                </a-row>

                <a-form-item has-feedback label="Email" name="email">
                    <a-input v-model:value="formState.email" type="email" />
                </a-form-item>

                <a-form-item has-feedback label="Name" name="name">
                    <a-input v-model:value="formState.name" type="text" />
                </a-form-item>

                <a-form-item >
                    <a-button type="primary" html-type="submit" @click="handleUpdateUser">Submit</a-button>
                    <a-button style="margin-left: 10px" @click="resetForm">Reset</a-button>
                </a-form-item>
            </a-form>
        </div>
    </div>
</template>

<script setup>
import { useRouter } from 'vue-router'
import userApi from '@/repositories/userApi.js'
import {onMounted} from 'vue'
import {getLastLetter} from '@/utils/stringUtils.js'
import { getColorForLastLetter } from '@/utils/colorUtils.js'

import { reactive, ref } from 'vue'
import {useMessageStore} from "@/stores/messageStore.js";
import {useUserStore} from "@/stores/userStore.js";
const formRef = ref()
const formState = reactive({
    name: '',
    email: '',
    username: '',
    id: '',
    role: '',
    phone: ''
})

const checkEmail = async (_rule, value) => {
    if (!value) {
        return Promise.reject('Please input the email')
    }
    const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    if (!emailRegex.test(value)) {
        return Promise.reject('Invalid email format')
    } else {
        return Promise.resolve()
    }
}

const checkPhone = async (_rule, value) => {
    if (!value) {
        return Promise.reject('Please input the phone number')
    }
    const phoneRegex = /^\d{10}$/
    if (!phoneRegex.test(value)) {
        return Promise.reject('Invalid phone number format')
    } else {
        return Promise.resolve()
    }
}

const rules = {
    name: [
        {
            required: true,
            trigger: 'change'
        }
    ],
    email: [
        {
            required: true,
            trigger: 'change',
            validator: checkEmail,
        }
    ],
    role: [
        {
            required: true,
            trigger: 'change',
        }
    ],
    phone: [
        {
            required: true,
            trigger: 'change',
            validator: checkPhone
        }
    ],
    username: [
        {
            required: true,
            trigger: 'change',
        }
    ]
}
const handleFinish = values => {
    console.log(values, formState)
}
const handleFinishFailed = errors => {
    console.log(errors);
}
const resetForm = () => {
    formRef.value.resetFields()
}
const handleValidate = (...args) => {
    console.log(args)
}

const handleUpdateUser = () => {
    userApi.updateUser(formState)
        .then(res => {
            useMessageStore().addMessage('success', 'Update user successfully!')
        })
        .catch(err => {
            useMessageStore().addMessage('error',err.response.message || err.message)
        })
}

// Get API
const router = useRouter()
const goBack = () => { router.go(-1) }

const getUser = () => {
    formState.id = useUserStore().getUser.id
    formState.email = useUserStore().getUser.email
    formState.name = useUserStore().getUser.name
    formState.phone = useUserStore().getUser.phone
    formState.username = useUserStore().getUser.username
    formState.role = useUserStore().getUserRole
}

onMounted(() => getUser())

</script>

<style scoped>
.update-container {
    background-color: var(--color-white);
    height: calc(100vh - 100px);
    border-radius: 5px;
    overflow: auto;
}

.header {
    border-bottom: 1px solid rgb(235, 237, 240);
    padding: 13px;
}

.body {
    width: 100%;
    display: flex;
    justify-content: center;
}

.form {
    margin: 20px;
    width: 600px;
}
</style>