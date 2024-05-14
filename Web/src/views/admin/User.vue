<template>
    <div class="container">
        <a-table :columns="columns" :data-source="data" :scroll="{ x: 1500, y: 500 }">
            <template #bodyCell="{ record, column, text }">
                <template v-if="column.dataIndex === 'username'" >
                    <a @click="handleDetail(record)">{{text}}</a>
                </template>
                <template v-if="column.dataIndex === 'role'" >
                    <a-tag v-if="text === 'admin'" color="#f50"  >{{ text }}</a-tag>
                    <a-tag v-if="text === 'teacher'" color="#87d068"  >{{ text }}</a-tag>
                    <a-tag v-if="text === 'leader'" color="#108ee9"  >{{ text }}</a-tag>
                    <a-tag v-if="text === 'student'" color="#2db7f5"  >{{ text }}</a-tag>
                </template>
                <template v-if="column.dataIndex === 'state'" >
                    <a-tag v-if="text === 'Active'" color="#55acee"  >{{ text }}</a-tag>
                    <a-tag v-if="text === 'Disabled'" color="#cd201f"  >{{ text }}</a-tag>
                </template>
                <template v-if="column.key === 'operation'">
                    <a-button v-if="record.state === 'Active'" @click="() => handleDelete(record)" style="margin-right: 5px; background-color: #FFF1EF; color: #FF4D4E" type="primary" danger>
                        <LockOutlined />
                    </a-button>
                    <a-button v-else @click="() => handleActive(record)" style="margin-right: 5px; border: none; background-color: #F6FFEC; color: #379E0E;" >
                        <UnlockOutlined />
                    </a-button>
                    <a-button @click="handleUpdate(record)" type="primary" style="background-color: #E6F4FF; color: #1677FF"><FormOutlined /></a-button>
                </template>
            </template>
        </a-table>

    </div>
</template>
<script setup>
import userApi from '@/repositories/userApi.js'
import router from '@/router/index.js'
import { useMessageStore } from '@/stores/messageStore.js'
import { ExclamationCircleOutlined, FormOutlined, LockOutlined, UnlockOutlined } from '@ant-design/icons-vue'
import { Modal } from 'ant-design-vue'
import dayjs from 'dayjs'
import { createVNode, onMounted, ref } from 'vue'

const columns = [
    {
        title: 'ID',
        width: 40,
        dataIndex: 'id',
        key: 'id',
        fixed: 'left',
    },
    {
        title: 'Username',
        width: 120,
        dataIndex: 'username',
        key: 'username',
        fixed: 'left',
    },
    {
        title: 'Name',
        width: 140,
        dataIndex: 'name',
        key: 'name',
        fixed: 'left',
        sorter: {
            compare: (a, b) => a.name - b.name,
            multiple: 3,
        },
    },
    {
        title: 'Email',
        dataIndex: 'email',
        key: 'email',
        width: 180,
    },

    {
        title: 'Role',
        dataIndex: 'role',
        key: 'role',
        width: 100,
        filters: [
            {
                text: 'Admin',
                value: 'admin',
            },
            {
                text: 'Teacher',
                value: 'teacher',
            },
            {
                text: 'Student',
                value: 'student',
            },
        ],
        onFilter: (value, record) => record.role.indexOf(value) === 0,
    },
    {
        title: 'Phone number',
        dataIndex: 'phone',
        key: 'phone',
        width: 150,
    },
    {
        title: 'State',
        dataIndex: 'state',
        key: 'state',
        width: 80,
        filters: [
            {
                text: 'Active',
                value: 'Active',
            },
            {
                text: 'Disabled',
                value: 'Disabled',
            },
        ],
        onFilter: (value, record) => record.state.indexOf(value) === 0,
    },
    {
        title: 'Date Insert',
        key: 'insertTime',
        dataIndex: 'insertTime',
        width: 200
    },
    {
        title: 'Action',
        key: 'operation',
        fixed: 'right',
        width: 100,
    }
];
const data = ref([])
const formatUserData = (userData) => {
    return userData.map(user => ({
        id: user.id,
        username: user.username,
        name: user.name,
        phone: user.phone,
        email: user.email,
        role: user.role === 3 ? 'admin' : (user.role === 2 ? 'teacher' : (user.role === 1 ? 'leader' : 'student')),
        address: user.address,
        state: user.deleteTime != null ? 'Disabled' : 'Active',
        insertTime : new dayjs(user.insertTime).format('MMMM D, YYYY')
    }))
}

const getListUser = () => {
    userApi.getListUserAdmin()
        .then(res => {
            data.value = formatUserData(res.data)
        })
        .catch(err => {
            console.log(err.response)
        })
}
onMounted(() => {getListUser()})

const handleDelete = (record) => {
    Modal.confirm({
        title: 'Do you want to disabled user?',
        icon: createVNode(ExclamationCircleOutlined),
        onOk() {
            userApi.disableUser(record.id)
                .then(() => {
                    useMessageStore().addMessage('success', 'Successfully!')
                    getListUser();
                })
                .catch(() => {
                    useMessageStore().addMessage('error', 'Something went wrong!')
                })
        },
        onCancel() {
            console.log('Cancel')
        },
    })
}

const handleActive = (record) => {
    Modal.confirm({
        title: 'Do you want to enable user?',
        icon: createVNode(ExclamationCircleOutlined),
        onOk() {
            userApi.disableUser(record.id)
                .then(() => {
                    useMessageStore().addMessage('success', 'Successfully!')
                    getListUser();
                })
                .catch(() => {
                    useMessageStore().addMessage('error', 'Something went wrong!')
                })
        },
        onCancel() {
            console.log('Cancel')
        },
    })
}

const handleUpdate = (record) => {
    router.push(`/admin/user/update/${record.id}`)
}

const handleDetail = (record) => {
    router.push(`/admin/user/detail/${record.id}`)
}
</script>
<style scoped>
.container {
    padding: 20px;
    background-color: var(--color-white);
    border-radius: 10px;
    height: calc(100vh - 106px);
    overflow: auto;
    display: flex;
    //align-items: center;
    justify-content: center;
}
</style>