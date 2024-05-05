<template>
    <div class="update-container">
        <a-page-header
            class="header"
            title="User detail"
            @back="goBack"
        />
        <div class="body">
            <a-descriptions layout="vertical" bordered style="width: 100%">
                <a-descriptions-item label="ID">{{ formState.id }}</a-descriptions-item>
                <a-descriptions-item label="Username">{{ formState.username }}</a-descriptions-item>
                <a-descriptions-item label="Phone number">{{ formState.phone }}</a-descriptions-item>
                <a-descriptions-item label="Insert time">{{ formState.insertTime }}</a-descriptions-item>
                <a-descriptions-item label="Email">{{ formState.email }}</a-descriptions-item>
                <a-descriptions-item label="Role">
                    <a-tag v-if="formState.role === 'admin'" color="#f50"  >{{ formState.role }}</a-tag>
                    <a-tag v-if="formState.role === 'teacher'" color="#87d068"  >{{ formState.role }}</a-tag>
                    <a-tag v-if="formState.role === 'leader'" color="#108ee9"  >{{ formState.role }}</a-tag>
                    <a-tag v-if="formState.role === 'student'" color="#2db7f5"  >{{ formState.role }}</a-tag>
                </a-descriptions-item>
                <a-descriptions-item label="Status" >
                    <a-badge v-if="formState.state === 'Active'" status="processing" :text="formState.state" />
                    <a-badge v-else  status="error" :text="formState.state" />
                </a-descriptions-item>
            </a-descriptions>
        </div>
    </div>
</template>

<script setup>
import { useRouter } from 'vue-router'
import userApi from '@/repositories/userApi.js'
import {onMounted} from 'vue'
import dayjs from 'dayjs'

import { reactive, ref } from 'vue'
import {useMessageStore} from "@/stores/messageStore.js";
import {CopyOutlined} from "@ant-design/icons-vue";
const formRef = ref()
const formState = reactive({
    name: '',
    email: '',
    username: '',
    id: '',
    role: '',
    phone: '',
    insertTime: null,
    state: ''
})

// Get API
const router = useRouter()
const goBack = () => { router.go(-1) }

const getUser = () => {
    userApi.getUserInformation(router.currentRoute.value.params.id)
        .then(res => {
            const { id, username, email, role, phone, name, insertTime , updateTime, deleteTime} = res.data
            formState.id = id
            formState.username = username
            formState.email = email
            formState.role = role === 3 ? 'admin' : (role === 2 ? 'teacher' : (role === 1 ? 'leader' : 'student'))
            formState.phone = phone
            formState.name = name
            formState.insertTime = dayjs(insertTime).format('MMMM D, YYYY')
            formState.state = deleteTime != null ? 'Disabled' : 'Active'

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
    padding: 20px;
}

</style>