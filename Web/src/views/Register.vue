<template>
    <div class="container">
        <a-form
            layout="vertical"
            ref="formRef"
            name="custom-validation"
            :model="formState"
            :rules="rules"
            v-bind="layout"
            @finish="handleFinish"
            @validate="handleValidate"
            @finishFailed="handleFinishFailed"
            class="form"
        >
            <a-form-item>
                <h2 class="header">Create an Account</h2>
                <p class="title">Create a account to continue</p>
            </a-form-item>
            <a-form-item has-feedback label="Username" name="username" >
                <a-input v-model:value="formState.username" class="input" placeholder="example"/>
            </a-form-item>
            <a-form-item has-feedback label="Email" name="email">
                <a-input v-model:value="formState.email" class="input" placeholder="example@gmail.com"/>

            </a-form-item>
            <a-form-item has-feedback label="Name" name="name">
                <a-input v-model:value="formState.name" class="input" placeholder="example"/>

            </a-form-item>
            <a-form-item has-feedback label="Password" name="pass">
                <a-input v-model:value="formState.pass" type="password" autocomplete="off" class="input" placeholder="password@777"/>
            </a-form-item>
            <a-form-item has-feedback label="Confirm" name="checkPass">
                <a-input v-model:value="formState.checkPass" type="password" autocomplete="off" class="input" placeholder="password@777" />
            </a-form-item>
            <a-form-item has-feedback label="Phone" name="phone">
                <a-input v-model:value="formState.phone" type="input" class="input" placeholder="0123456789"/>
            </a-form-item>
            <a-form-item class="footer" style="text-align: center;">
                <a-button type="primary" html-type="submit" class="submit-button">
                    <span class="submit-text"></span>Submit</a-button>
            </a-form-item>
            <a-form-item class="footer" style="text-align: center;">
                <a-button type="primary" @click="resetForm" class="reset-button">
                    <span class="reset-text"></span>Reset</a-button>
            </a-form-item>
        </a-form>
    </div>
</template>

<script lang="ts" setup>
import type { Rule } from 'ant-design-vue/es/form';
import type { FormInstance } from 'ant-design-vue';
import { defineComponent, reactive, ref} from 'vue';
import { useMessageStore } from '@/stores/messageStore'
import authApi from '../repositories/authApi'
import router from '../router'
import { useSpinStore } from '@/stores/spinStore.js'

const messageStore = useMessageStore()
const spinStore = useSpinStore()

interface FormState {
    username: string;
    email: string;
    name: string;
    pass: string;
    checkPass: string;
    phone: string;
    age: number | undefined;
}

const formRef = ref<FormInstance>();
const formState = reactive<FormState>({
    username: '',
    email: '',
    name: '',
    pass: '',
    checkPass: '',
    phone: '',
    age: undefined,
});

const checkAge = async (_rule: Rule, value: number) => {
    if (!value) {
        return Promise.reject('Please input the age')
    }
    if (!Number.isInteger(value)) {
        return Promise.reject('Please input digits')
    } else {
        if (value < 18) {
            return Promise.reject('Age must be greater than 18')
        } else {
            return Promise.resolve()
        }
    }
};

const validatePass = async (_rule: Rule, value: string) => {
    if (value === '') {
        return Promise.reject('Please input the password')
    } else {
        if (formState.checkPass !== '') {
            formRef.value.validateFields('checkPass')
        }
        return Promise.resolve();
    }
};

const validatePass2 = async (_rule: Rule, value: string) => {
    if (value === '') {
        return Promise.reject('Please input the password again')
    } else if (value !== formState.pass) {
        return Promise.reject("Two inputs don't match!")
    } else {
        return Promise.resolve();
    }
};

const validatePhoneNumber = async (_rule: Rule, value: string) => {
    if (value === '') {
        return Promise.reject('Please input the phone number')
    } else {
        return Promise.resolve()
    }
};

const validateUsername = async (_rule: Rule, value: string) => {
    if (value === '') {
        return Promise.reject('Please input the username')
    } else {
        return Promise.resolve()
    }
};

const validateEmail = async (_rule: Rule, value: string) => {
    if (value === '') {
        return Promise.reject('Please input the email')
    } else {
        return Promise.resolve()
    }
};

const validateName = async (_rule: Rule, value: string) => {
    if (value === '') {
        return Promise.reject('Please input the name')
    } else {
        return Promise.resolve()
    }
};

const rules: Record<string, Rule[]> = {
    username: [{ required: true, validator: validateUsername, trigger: 'change' }],
    email: [{ required: true, validator: validateEmail, trigger: 'change' }],
    name: [{ required: true, validator: validateName, trigger: 'change' }],
    pass: [{ required: true, validator: validatePass, trigger: 'change' }],
    checkPass: [{required: true, validator: validatePass2, trigger: 'change' }],
    phone: [{ required: true, validator: validatePhoneNumber, trigger: 'change' }],
    age: [{ validator: checkAge, trigger: 'change' }],
};

const layout = {
    labelCol: { span: 4 },
    wrapperCol: { span: 14 },
};

// const handleFinish = (values: FormState) => {
//     console.log("Logging in with:", values.username, values.pass, values.name, values.email, values.phone);
// };

const handleFinish = async (values: FormState) => {
    spinStore.startLoading()
    try {
        const response = await authApi.register({
            username: formState.username,
            password: formState.pass,
            email: formState.email,
            name: formState.name,
            phone: formState.phone
        })
        if (response.status === 201) {
            spinStore.stopLoading()
            messageStore.addMessage('success', response.message)
            router.push('/login')
        }
    } catch (error) {
        spinStore.stopLoading()
        console.log("ERROR: ", error)
        if (error.response && error.response.status === 400) {
            messageStore.addMessage('error', error.response.data.message)
        } else {
            messageStore.addMessage('error', 'An error occurred while logging in')
        }
    }
}

const handleFinishFailed = errors => {
    console.log(errors);
};

const resetForm = () => {
    formRef.value.resetFields();
};

const handleValidate = (...args) => {
    console.log(args);
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
    width: 580px;
    border-radius: 10px;
    padding: 40px;
    box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
    background-color: var(--color-white);
}

.form .header {
    width: 500px;
    text-align: center;
    font-weight: 600;
    font-size: 24px;
}

.form .title {
    width: 500px;
    text-align: center;
    font-size: 14px;
    color: var(--color-gray-dark);
}

.form .input {
    width: 400px;
    height: 40px;
}

.submit-button {
    width: 500px;
    height: 40px;
    font-weight: 600;
    background-color: var(--color-blue);
    color: var(--color-white);
}

.submit-button .submit-text {
    font-weight: bold;
}

.reset-button {
    width: 500px;
    height: 40px;
    font-weight: 600;
    background-color: var(--color-blue);
    color: var(--color-white);
}

.reset-button .reset-text {
    font-weight: bold;
}
</style>