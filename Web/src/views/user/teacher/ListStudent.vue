<template>
    <a-table :columns="columns" :data-source="data" :scroll="{ x: 1300, y: 1000 }">
        <template #bodyCell="{ column, record }">
            <template v-if="column.key === 'operation'">
                <a @click="handleClickAction(record)"><UserDeleteOutlined/> Remove</a>
            </template>
        </template>
    </a-table>
</template>

<script setup>
import classApi from "@/repositories/classApi.js";
import router from "@/router/index.js";
import {createVNode, onMounted, ref} from "vue";
import {ExclamationCircleOutlined, UserDeleteOutlined} from '@ant-design/icons-vue'
import {useMessageStore} from "@/stores/messageStore.js";
import {useSpinStore} from "@/stores/spinStore.js";
import {Modal} from "ant-design-vue";

const useMessage = useMessageStore()
const useSpin = useSpinStore()

const columns = [
    {
        title: 'Student ID',
        width: 150,
        dataIndex: 'studentId',
        key: 'studentId',
        fixed: 'left',
    },
    {
        title: 'Name',
        width: 300,
        dataIndex: 'name',
        key: 'age',
        fixed: 'left',
    },
    {
        title: 'Email',
        dataIndex: 'email',
        key: 'email',
    },
    {
        title: 'Phone',
        dataIndex: 'phone',
        key: 'phone',
    },
    {
        title: 'Action',
        key: 'operation',
        fixed: 'right',
        width: 120,
    },
];

const data = ref([]);

const getListStudent = async () => {
    const code = router.currentRoute.value.query.code;
    try {
        const res = await classApi.getListStudentInClass(code);
        data.value = res.data;
    } catch (error) {
        console.error(error);
    }
}

onMounted(() => getListStudent());

const handleClickAction = (record) => {

    Modal.confirm({
        title: 'Do you want to remove student?',
        icon: createVNode(ExclamationCircleOutlined),
        onOk() {
            useSpin.startLoading()
            classApi.removeStudentToClass(record.id)
                .then(res => {
                    console.log(res.data)
                    useSpin.stopLoading()
                    if(res.data === true) {
                        useMessage.addMessage('success', 'Delete successfully.')
                    }
                })
                .catch(err => {
                    useSpin.stopLoading()
                    useMessage.addMessage('error', 'Something went wrong.')
                })
        },
        onCancel() {
            console.log('Cancel')
        },
    })
}
</script>
