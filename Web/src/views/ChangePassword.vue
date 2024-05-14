<template>
    <div class="container-change">
        <h2 class="header">Change password</h2>
        <div class="body">
            <a-form
                class="form"
                :ref="formRef"
                name="custom-validation"
                :model="formState"
                :rules="rules"
                layout="vertical"
                @finish="handleFinish"
                @validate="handleValidate"
                @finishFailed="handleFinishFailed"
            >
                <a-form-item has-feedback label="Current Password" name="current">
                    <a-input v-model:value="formState.current" type="password" autocomplete="off" />
                </a-form-item>
                <a-form-item has-feedback label="New Password" name="pass">
                    <a-input v-model:value="formState.pass" type="password" autocomplete="off" />
                </a-form-item>
                <a-form-item has-feedback label="Confirm Password" name="checkPass">
                    <a-input v-model:value="formState.checkPass" type="password" autocomplete="off" />
                </a-form-item>
                <a-form-item :wrapper-col="{ span: 14, offset: 4 }">
                    <a-button @click="changePassword" type="primary" html-type="submit">Submit</a-button>
                    <a-button style="margin-left: 10px" @click="resetForm">Reset</a-button>
                </a-form-item>
            </a-form>
        </div>
    </div>
</template>

<script setup>
import userApi from '@/repositories/userApi.js';
import { useMessageStore } from '@/stores/messageStore.js';
import { useSpinStore } from '@/stores/spinStore.js';
import { reactive, ref } from 'vue';

const formRef = ref(null);
const formState = reactive({
    pass: '',
    checkPass: '',
    current: '',
})

// Validate data
const validatePass = async (_rule, value) => {
    if (value === '') {
        return Promise.reject('Please input the password')
    } else {
        if (formRef.value) {
            await formRef.value.validateFields('checkPass')
        }
        return Promise.resolve();
    }
}

const validatePass2 = async (_rule, value) => {
    if (value === '') {
        return Promise.reject('Please input the password again');
    } else if (value !== formState.pass) {
        return Promise.reject("Two inputs don't match!");
    } else {
        return Promise.resolve();
    }
};

const rules = {
    pass: [
        {
            required: true,
            validator: validatePass,
            trigger: 'change',
        },
    ],
    checkPass: [
        {
            validator: validatePass2,
            trigger: 'change',
        },
    ],
    current: [
        {
            required: true,
            trigger: 'change',
        },
    ],
};

const handleFinish = values => {
    console.log(values, formState);
};

const handleFinishFailed = errors => {
    console.log(errors);
};

const resetForm = () => {
    formRef.value.resetFields()
}

const handleValidate = (...args) => {
    console.log(args)
}

// Call API
const changePassword = () => {
    useSpinStore().startLoading()
    userApi.changePassword(formState)
        .then(res => {
            useSpinStore().stopLoading()
            useMessageStore().addMessage('success', 'Change successfully!')
        })
        .catch(err => {
            useSpinStore().stopLoading()
            useMessageStore().addMessage('error', 'Change failed!')
        })
}
</script>

<style scoped>
.container-change {
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
    width: 100%;
    max-width: 600px;
    margin-top: 20px;
}
</style>
