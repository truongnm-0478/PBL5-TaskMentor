<template>
    <a-table :columns="columns" :data-source="data" class="components-table-demo-nested">
        <template #bodyCell="{ column, record }">
            <template v-if="column.key === 'operation'">
                <a-button v-if="role === 2" @click="handleDelete(record)"
                          style="margin-right: 5px; background-color: #FFF1EF; color: #FF4D4E" type="primary" danger>
                    <DeleteOutlined/>
                </a-button>
                <a-button @click="handleShowMember(record)" type="primary" style="background-color: #E6F4FF; color: #1677FF">
                    <InfoCircleOutlined/>
                </a-button>
            </template>
        </template>
    </a-table>
    <a-drawer
        v-model:open="open"
        class="custom-class"
        root-class-name="root-class-name"
        title="List member of team"
        placement="right"
        :width="736"
        @after-open-change="afterOpenChange"
    >
        <div v-for="item in listMember" :key="item.studentID">
            <a-descriptions :title="item.name" style="padding: 10px">
                <a-descriptions-item label="Role" >
                    <a-tag color="#f50" v-if="item.isLeader === true">Leader</a-tag>
                    <a-tag color="#2db7f5" v-else>Member</a-tag>
                </a-descriptions-item>
                <a-descriptions-item label="Student Id">{{ item.studentID }}</a-descriptions-item>
                <a-descriptions-item label="Phone">{{ item.phone }}</a-descriptions-item>
                <a-descriptions-item label="Email">{{ item.mail }}</a-descriptions-item>
            </a-descriptions>
            <a-divider />
        </div>

    </a-drawer>

</template>

<script setup>
import teamApi from '@/repositories/teamApi.js'
import router from '@/router/index.js'
import {createVNode, onMounted, ref} from 'vue'
import dayjs from 'dayjs'
import {DeleteOutlined, ExclamationCircleOutlined, InfoCircleOutlined} from '@ant-design/icons-vue'
import {Modal} from 'ant-design-vue'
import classApi from '@/repositories/classApi.js'
import {useMessageStore} from '@/stores/messageStore.js'
import {useUserStore} from "@/stores/userStore.js";

const open = ref(false)
const role = ref(useUserStore().getUserRole)
const code = router.currentRoute.value.query.code
const columns = [
    {
        title: 'ID',
        dataIndex: 'id',
        key: 'id',
    },
    {
        title: 'Name',
        dataIndex: 'name',
        key: 'name',
    },
    {
        title: 'Create Time',
        dataIndex: 'insertTime',
        key: 'insertTime',
    },
    {
        title: 'Action',
        key: 'operation',
    },
];
const data = ref([])
const listMember = ref([])

const getListTeam = () => {
    teamApi.getListTeam(code)
        .then(res => {
            data.value = res.data.map(item => ({
                key: item.id,
                id: item.id,
                name: item.name,
                insertTime: dayjs(item.insertTime).format('MMMM D, YYYY')
            }))
        })
        .catch(err => {
            console.log(err)
        })
}
onMounted(() => {
    getListTeam()
})

const handleDelete = (record) => { // Pass 'record' as an argument
    Modal.confirm({
        title: 'Do you want to remove this team?',
        icon: createVNode(ExclamationCircleOutlined),
        onOk() {
            teamApi.removeTeamToClass(record.id)
                .then(res => {
                    if (res.data === true) {
                        useMessageStore().addMessage('success', 'Delete successfully.')
                        data.value = data.value.filter(item => item.id !== record.id)
                    }
                })
                .catch(err => {
                    useMessageStore().addMessage('error', 'Something went wrong.')
                })
        },
        onCancel() {
            console.log('Cancel')
        },
    })
}

const afterOpenChange = bool => {
    console.log('open', bool)
}

const handleShowMember = (record) => {
    open.value = true
    teamApi.getListMember(record.id)
        .then(res => {
            listMember.value = res.data
            console.log(listMember.value)
        })
        .catch(err => {
            useMessageStore().addMessage('error', 'Something went wrong!')
        })
}

</script>
