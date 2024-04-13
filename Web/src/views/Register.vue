<template>
    <a-form
        ref="formRef"
        name="custom-validation"
        :model="formState"
        :rules="rules"
        v-bind="layout"
        @finish="handleFinish"
        @validate="handleValidate"
        @finishFailed="handleFinishFailed"
    >
        <a-form-item has-feedback label="Username" name="username">
            <a-input v-model:value="formState.username" />
        </a-form-item>
        <a-form-item has-feedback label="Email" name="email">
            <a-input v-model:value="formState.email" />
        </a-form-item>
        <a-form-item has-feedback label="Name" name="name">
            <a-input v-model:value="formState.name" />
        </a-form-item>
        <a-form-item has-feedback label="Password" name="pass">
            <a-input v-model:value="formState.pass" type="password" autocomplete="off" />
        </a-form-item>
        <a-form-item has-feedback label="Confirm" name="checkPass">
            <a-input v-model:value="formState.checkPass" type="password" autocomplete="off" />
        </a-form-item>
        <a-form-item :wrapper-col="{ span: 14, offset: 4 }">
            <a-button type="primary" html-type="submit">Submit</a-button>
            <a-button style="margin-left: 10px" @click="resetForm">Reset</a-button>
        </a-form-item>
    </a-form>
</template>

<script lang="ts" setup>
import { reactive, ref } from 'vue';
import type { Rule } from 'ant-design-vue/es/form';
import type { FormInstance } from 'ant-design-vue';
import { UserOutlined } from "@ant-design/icons-vue";

interface FormState {
    username: string;
    email: string;
    name: string;
    pass: string;
    checkPass: string;
    age: number | undefined;
}

const formRef = ref<FormInstance>();
const formState = reactive<FormState>({
    username: '',
    email: '',
    name: '',
    pass: '',
    checkPass: '',
    age: undefined,
});

const checkAge = async (_rule: Rule, value: number) => {
    if (!value) {
        return Promise.reject('Please input the age');
    }
    if (!Number.isInteger(value)) {
        return Promise.reject('Please input digits');
    } else {
        if (value < 18) {
            return Promise.reject('Age must be greater than 18');
        } else {
            return Promise.resolve();
        }
    }
};

const validatePass = async (_rule: Rule, value: string) => {
    if (value === '') {
        return Promise.reject('Please input the password');
    } else {
        if (formState.checkPass !== '') {
            formRef.value.validateFields('checkPass');
        }
        return Promise.resolve();
    }
};

const validatePass2 = async (_rule: Rule, value: string) => {
    if (value === '') {
        return Promise.reject('Please input the password again');
    } else if (value !== formState.pass) {
        return Promise.reject("Two inputs don't match!");
    } else {
        return Promise.resolve();
    }
};

const validateUsername = async (_rule: Rule, value: string) => {
    if (value === '') {
        return Promise.reject('Please input the username');
    } else {
        // Add any additional validation logic here
        return Promise.resolve();
    }
};

const validateEmail = async (_rule: Rule, value: string) => {
    if (value === '') {
        return Promise.reject('Please input the email');
    } else {
        // Add any additional validation logic here
        return Promise.resolve();
    }
};

const validateName = async (_rule: Rule, value: string) => {
    if (value === '') {
        return Promise.reject('Please input the name');
    } else {
        // Add any additional validation logic here
        return Promise.resolve();
    }
};

const rules: Record<string, Rule[]> = {
    username: [{ required: true, validator: validateUsername, trigger: 'change' }],
    email: [{ required: true, validator: validateEmail, trigger: 'change' }],
    name: [{ required: true, validator: validateName, trigger: 'change' }],
    pass: [{ required: true, validator: validatePass, trigger: 'change' }],
    checkPass: [{ validator: validatePass2, trigger: 'change' }],
    age: [{ validator: checkAge, trigger: 'change' }],
};

const layout = {
    labelCol: { span: 4 },
    wrapperCol: { span: 14 },
};

const handleFinish = (values: FormState) => {
    console.log("Logging in with:", values.username, values.pass);
    // Perform login logic here, such as sending credentials to a server
};

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
