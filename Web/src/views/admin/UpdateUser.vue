<template>
    <div class="update-container">
        <a-page-header
            class="header"
            title="Update user"
            @back="goBack"
        />
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
                        <a-form-item has-feedback label="User ID" name="id">
                            <a-input v-model:value="formState.id" type="text" readonly="true" />
                        </a-form-item>
                    </a-col>
                    <a-col :span="2"></a-col>
                    <a-col :span="11">
                        <a-form-item has-feedback label="Username" name="username">
                            <a-input v-model:value="formState.username" type="text" />
                        </a-form-item>
                    </a-col>
                </a-row>

                <a-form-item has-feedback label="Email" name="email">
                    <a-input v-model:value="formState.email" type="email" />
                </a-form-item>

                <a-row>
                    <a-col :span="11">
                        <a-form-item has-feedback label="Phone number" name="phone">
                            <a-input v-model:value="formState.phone" type="phone" />
                        </a-form-item>
                    </a-col>
                    <a-col :span="2"></a-col>
                    <a-col :span="11">
                        <a-form-item has-feedback label="Role" name="role">
                            <a-select v-model:value="formState.role">
                                <a-select-option :value="0">User</a-select-option>
                                <a-select-option :value="1">Leader</a-select-option>
                                <a-select-option :value="2">Teacher</a-select-option>
                                <a-select-option :value="3">Admin</a-select-option>
                            </a-select>
                        </a-form-item>
                    </a-col>
                </a-row>

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
    userApi.getUserInformation(router.currentRoute.value.params.id)
        .then(res => {
            console.log("DATA: ", res.data)
            const { id, username, email, role, phone, name } = res.data
            formState.id = id
            formState.username = username
            formState.email = email
            formState.role = role
            formState.phone = phone
            formState.name = name

        })
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
    border-bottom: 1px solid rgb(235, 237, 240)
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