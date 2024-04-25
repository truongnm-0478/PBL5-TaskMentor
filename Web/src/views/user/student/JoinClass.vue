<template>
    <div class="container">
        <a-form
            :model="formState"
            name="horizontal_login"
            layout="vertical"
            autocomplete="off"
            @finish="onFinish"
            @finishFailed="onFinishFailed"
        >
            <a-form-item
                label="Student ID:"
                name="studentID"
                :rules="[{ required: true, message: 'Please input your student ID!' }]"
            >
                <a-input v-model:value="formState.studentID">
                    <template #prefix>
                        <UserOutlined class="site-form-item-icon" />
                    </template>
                </a-input>
            </a-form-item>

            <a-form-item
                label="Class code"
                name="classCode"
                :rules="[{ required: true, message: 'Please input class code!' }]"
            >
                <a-input v-model:value="formState.classCode">
                    <template #prefix>
                        <BarcodeOutlined class="site-form-item-icon" />
                    </template>
                </a-input>
            </a-form-item>

            <a-form-item>
                <a-button :disabled="disabled" type="primary" html-type="submit">Join team <UsergroupAddOutlined /></a-button>
            </a-form-item>
        </a-form>
    </div>
</template>

<script setup>
import { reactive, computed } from 'vue'
import { UserOutlined, BarcodeOutlined, UsergroupAddOutlined } from '@ant-design/icons-vue'
import classApi from '@/repositories/classApi.js'
import router from '@/router/index.js'
import { useMessageStore } from '@/stores/messageStore.js'

const useMessage = useMessageStore()
const formState = reactive({
    studentID: '',
    classCode: '',
});
const onFinish = async values => {
    await classApi.joinClass({
        studentId: values.studentID,
        classCode: values.classCode
    })
        .then(res => {
            if(res.data == true) {
                useMessage.addMessage('success', 'Successfully participate in class.')
                router.push('/joinedClass')
            } else {
                useMessage.addMessage('error', 'Student ID or Class incorrect or already used.')
            }
        })
        .catch(err => {
            seMessage.addMessage('error', 'Student ID or Class incorrect or already used.')
            console.log(err)
        })
};
const onFinishFailed = errorInfo => {
    console.log('Failed:', errorInfo)
};
const disabled = computed(() => {
    return !(formState.studentID && formState.classCode);
});

</script>

<style scoped>
.container {
    padding: 50px 20px;
    background-color: var(--color-white);
    border-radius: 10px;
    height: calc(100vh - 106px);
    overflow: auto;
}
</style>