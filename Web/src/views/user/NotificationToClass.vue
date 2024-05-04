<template>
    <div class="container-add">
        <a-button @click="createNotification" type="primary" v-if="useUserStore().getUserRole === 2">
            New Notification <PlusOutlined />
        </a-button>
    </div>

    <a-modal v-model:open="isModalOpen" title="New Notification" @ok="handleOk">
        <a-form-item :label-col="{ span: 24 }">
            <a-textarea v-model:value="content"/>
        </a-form-item>
    </a-modal>

    <a-list item-layout="horizontal" :data-source="data" style="width: 100%">
        <template #renderItem="{ item }">
            <a-comment class="comment">
                <template #avatar>
                    <div class="avatar">
                        <a-avatar size="large" :style="{ backgroundColor: getColorForLastLetter(item.name) }">{{ getLastLetter(item.name) }}</a-avatar>
                    </div>
                </template>
                <template #author><a>{{ item.username }} ({{item.name}})</a></template>
                <template #content>
                    <p>{{ item.content }}</p>
                </template>
                <template #datetime>
                    <span>{{ dayjs(item.dateTime).format('YYYY-MM-DD HH:mm')}}</span>
                </template>
                <a-dropdown>
                    <template #overlay>
                        <a-menu>
                            <a-button @click="handleDelete(item)" type="text" danger style="width: 100%">Delete</a-button>
                        </a-menu>
                    </template>
                    <a-button v-if="useUserStore().getUserRole === 2" type="link" class="more-button">
                        <EllipsisOutlined />
                    </a-button>
                </a-dropdown>
            </a-comment>
        </template>
    </a-list>

</template>

<script setup>
import {EllipsisOutlined, PlusOutlined} from '@ant-design/icons-vue'
import {onMounted, ref, watchEffect} from 'vue'
import notificationApi from '@/repositories/notificationApi.js'
import dayjs from 'dayjs'
import router from '@/router/index.js'
import { useUserStore } from '@/stores/userStore.js'
import projectApi from '@/repositories/projectApi.js'
import {useMessageStore} from '@/stores/messageStore.js'
import {getLastLetter} from '@/utils/stringUtils.js'
import {getColorForLastLetter} from '@/utils/colorUtils.js'
import {useSocketStore} from '@/stores/socketStore.js'

const socketStore = useSocketStore()
const code = router.currentRoute.value.query.code;
const data = ref([])
const isModalOpen = ref(false)
const content = ref('')

const createNotification = () => {
    isModalOpen.value = true
}

const handleCancel = () => {

}

const handleOk = async () => {
    await notificationApi.createNotificationToClass({
        classCode: code,
        content: content.value
    })
        .then(res => {
            isModalOpen.value = false
            console.log(res)
            getListNotification()
        })
        .catch(err => {
            isModalOpen.value = false
            console.log(err)
        })
}

const getListNotification = async () => {
    await notificationApi.getNotificationsClass(code)
        .then(res => {
            data.value = res.data
        })
}
onMounted(() => {getListNotification()})

const handleDelete = (item) => {
    notificationApi.deleteNotification(item.id)
        .then(res => {
            useMessageStore().addMessage('success', 'Delete successfully!')
            getListNotification()
        })
        .catch(err => {
            useMessageStore().addMessage('error', 'Delete failure!')
        })
}

watchEffect(() => {
    if (socketStore.newMessage) {
        getListNotification()
    }
})

</script>

<style scoped>

.comment {
    border-radius: 8px;
    padding: 0 10px;
}

.comment:hover {
    background-color: rgb(230,244,255);
}

.container-add {
    display: flex;
    justify-content: end;
    margin-bottom: 16px;
}

.more-button {
    position: absolute;
    top: 5px;
    right: 5px;
    color: #999;
    cursor: pointer;
    font-size: 16px;
}

</style>