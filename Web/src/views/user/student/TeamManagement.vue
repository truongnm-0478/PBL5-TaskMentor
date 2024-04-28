<template>
    <div class="container">
        <a-table :data-source="data" :columns="columns">
            <template #customFilterDropdown="{ setSelectedKeys, selectedKeys, confirm, clearFilters, column }">
                <div style="padding: 8px">
                    <a-input ref="searchInput" :placeholder="`Search ${column.dataIndex}`" :value="selectedKeys[0]"
                        style="width: 188px; margin-bottom: 8px; display: block"
                        @change="e => setSelectedKeys(e.target.value ? [e.target.value] : [])"
                        @pressEnter="handleSearch(selectedKeys, confirm, column.dataIndex)" />
                    <a-button type="primary" size="small" style="width: 90px; margin-right: 8px"
                        @click="handleSearch(selectedKeys, confirm, column.dataIndex)">
                        <template #icon>
                            <SearchOutlined />
                        </template>
                        Search
                    </a-button>
                    <a-button size="small" style="width: 90px" @click="handleReset(clearFilters)">
                        Reset
                    </a-button>
                </div>
            </template>
            <template #customFilterIcon="{ filtered }">
                <search-outlined :style="{ color: filtered ? '#108ee9' : undefined }" />
            </template>
            <template #bodyCell="{ text, column, record }">
                <span v-if="state.searchText && state.searchedColumn === column.dataIndex ">
                    <template v-for="(fragment, i) in text.toString().split(new RegExp(`(?<=${state.searchText})|(?=${state.searchText})`, 'i'))">
                        <a @click="handleShowDetail(record)" v-if="fragment.toLowerCase() === state.searchText.toLowerCase()" :key="i" class="highlight">
                            {{ fragment }}
                        </a>
                        <a @click="handleShowDetail(record)" v-else>{{ fragment }}</a>
                    </template>
                </span>
                <span v-else-if="column.key === 'operation'">
                    <a-button @click="handleDelete(record)"
                              style="margin-right: 5px; background-color: #FFF1EF; color: #FF4D4E" type="primary" danger>
                        <DeleteOutlined/>
                    </a-button>
                </span>
                <span v-else-if="column.key === 'name'">
                    <a @click="handleShowDetail(record)" style="cursor: pointer">{{ text }}</a>
                </span>
            </template>
        </a-table>
    </div>

</template>
<script setup>
import teamApi from '@/repositories/teamApi.js';
import {
    DeleteOutlined,
    EllipsisOutlined,
    ExclamationCircleOutlined,
    InfoCircleOutlined,
    SearchOutlined
} from '@ant-design/icons-vue';
import {createVNode, onMounted, reactive, ref} from 'vue';
import {Modal} from "ant-design-vue";
import {useMessageStore} from '@/stores/messageStore.js'
import router from "@/router/index.js";

const state = reactive({
    searchText: '',
    searchedColumn: '',
})
const searchInput = ref()
const columns = [
    {
        title: 'Name',
        dataIndex: 'name',
        key: 'name',
        customFilterDropdown: true,
        onFilter: (value, record) => record.name.toString().toLowerCase().includes(value.toLowerCase()),
        onFilterDropdownOpenChange: visible => {
            if (visible) {
                setTimeout(() => {
                    searchInput.value.focus()
                }, 100);
            }
        },
    },
    {
        title: 'Class',
        dataIndex: 'class',
        key: 'class',
    },
    {
        title: 'Leader',
        dataIndex: 'leader',
        key: 'leader',
        customFilterDropdown: true,
        onFilter: (value, record) =>
            record.leader.toString().toLowerCase().includes(value.toLowerCase()),
        onFilterDropdownOpenChange: visible => {
            if (visible) {
                setTimeout(() => {
                    searchInput.value.focus();
                }, 100)
            }
        },
    },
    {
        title: 'Action',
        key: 'operation',
    },
]
const handleSearch = (selectedKeys, confirm, dataIndex) => {
    confirm();
    state.searchText = selectedKeys[0]
    state.searchedColumn = dataIndex
}
const handleReset = clearFilters => {
    clearFilters({
        confirm: true,
    })
    state.searchText = ''
}

const data = ref([])

const getListTeam = () => {
    teamApi.getTeamByUser()
        .then(res => {
            data.value = res.data.map(item => ({
                key: item.id,
                name: item.name,
                class: item.className,
                leader: item.leader.name
            }))
        })
        .catch(err => console.log(err))
}
onMounted(() => { getListTeam() })

const handleDelete = (record) => {
    Modal.confirm({
        title: 'Do you want to remove this team?',
        icon: createVNode(ExclamationCircleOutlined),
        onOk() {
            teamApi.removeTeamToClass(record.key)
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

const handleShowDetail = (record) => {
    router.push({ path: '/project', query: { id: record.key } })
}
</script>

<style scoped>
.highlight {
    background-color: rgb(255, 192, 105);
    padding: 0px;
}

.container {
    padding: 10px 20px;
    background-color: var(--color-white);
    overflow: auto;
    border-radius: 10px;
    height: calc(100vh - 100px);
}
</style>