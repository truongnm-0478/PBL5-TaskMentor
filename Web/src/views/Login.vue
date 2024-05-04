<template>
    <div class="container">
        <a-form
            layout="vertical"
            :model="formState"
            @finish="handleFinish"
            @finishFailed="handleFinishFailed"
            class="form"
        >
            <a-form-item>
                <h2 class="header">Login to Account</h2>
                <p class="title">Please enter your username and password to continue</p>
            </a-form-item>
            <a-form-item label="Username" :rules="{ required: true}">
                <a-input v-model:value="formState.user" placeholder="example" class="input">
                    <template #prefix>
                        <UserOutlined style="color: rgba(0, 0, 0, 0.25)"/>
                    </template>
                </a-input>
            </a-form-item>
            <a-form-item label="Password" :rules="{ required: true }" style="position: relative;">
                <a-input-password v-model:value="formState.password" placeholder="password@777" class="input">
                    <template #prefix>
                        <LockOutlined style="color: rgba(0, 0, 0, 0.25)"/>
                    </template>
                </a-input-password>
                <a href="#" class="forget-password">Forget password?</a>
            </a-form-item>

            <a-form-item class="footer" style="text-align: center;">
                <a-button type="primary" html-type="submit" :disabled="formState.user === '' || formState.password === ''" class="login-button">
                    <span class="login-text">Sign in</span>
                </a-button>
                <span class="sub-text">
          Don’t have an account?
          <a href="/register" style="text-decoration: underline">Create Account</a>
        </span>
            </a-form-item>
        </a-form>
    </div>
</template>

<script setup>
import { UserOutlined, LockOutlined } from '@ant-design/icons-vue'
import { reactive } from 'vue'
import authApi from '../repositories/authApi'
import { useMessageStore } from '../stores/messageStore'
import router from '../router'
import { useSpinStore } from '@/stores/spinStore.js'
import { useUserStore } from '@/stores/userStore.js'
import { useSocketStore } from '@/stores/socketStore.js'


const formState = reactive({
    user: '',
    password: '',
})

const messageStore = useMessageStore()
const spinStore = useSpinStore()
const userStore = useUserStore()

const handleFinish = async () => {
    spinStore.startLoading()
    try {
        const response = await authApi.login({
            username: formState.user,
            password: formState.password
        })
        if (response.status === 200) {
            spinStore.stopLoading()
            const accessToken = response.data.accessToken;
            const refreshToken = response.data.refreshToken;

            userStore.setAccessToken(accessToken)
            userStore.setRefreshToken(refreshToken)
            userStore.setIsLoggedIn(true)

            messageStore.addMessage('success', response.message)

            const response1 = await authApi.getCurrentUser()
            if(response1.status === 200) {
                const userInfo = response1.data;
                userStore.setUser(userInfo)
            }
            useSocketStore().connectSocket(userStore.getUser.id)
            await router.push('/')
        }
    } catch (error) {
        spinStore.stopLoading()
        if (error.response && error.response.status === 401) {
            messageStore.addMessage('error', error.response.data.message)
        } else {
            messageStore.addMessage('error', 'An error occurred while logging in')
        }
    }
}

const handleFinishFailed = (errors) => {
    console.log('Form errors:', errors)
};
</script>

<style scoped>
.container {
    display: flex;
    justify-content: center;
    align-items: center;
    height: 100%;
    min-height: 100vh;
    background: var(--color-blue);
}

.form {
    width: 450px;
    border-radius: 10px;
    padding: 46px;
    box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
    background-color: #fff;
    position: relative;
}

.form .header {
    text-align: center;
    font-weight: 600;
    font-size: 24px;
}

.form .title {
    text-align: center;
    font-size: 14px;
    color: var(--color-gray-dark);
}

.form .input {
    height: 40px;
}

.form .footer {
    margin-top: 60px;
}

.login-button {
    width: 100%;
    height: 40px;
    font-weight: 600;
    background-color: var(--color-blue);
    color: var(--color-white);
}

.login-button .login-text {
    font-weight: bold;
}

.forget-password {
    color: var(--color-gray-dark);
    line-height: 40px;
    font-size: 12px;
    position: absolute;
    right: 0;
    top: 32px;
}

.sub-text {
    font-size: 13px;
    line-height: 40px;
}
</style>
