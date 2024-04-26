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
            </a-comment>
        </template>
    </a-list>

</template>

<script setup>
import { PlusOutlined } from '@ant-design/icons-vue'
import { onMounted, ref } from 'vue'
import notificationApi from '@/repositories/notificationApi.js'
import dayjs from 'dayjs'
import router from '@/router/index.js'
import { useUserStore } from '@/stores/userStore.js'


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

const getLastLetter = (name) => {
    const words = name.trim().split(/\s+/);
    if (words.length < 2) {
        return ''
    }
    const lastWord = words[words.length - 1];
    const secondLastWord = words[words.length - 2];
    const firstTwoLetters = `${secondLastWord.charAt(0)}${lastWord.charAt(0)}`.toUpperCase();
    return firstTwoLetters;
}


const getColorForLastLetter = (name) => {
    const colors = ['#1A5DB6', '#2E8B57', '#FF8C00', '#FF1493', '#8A2BE2']
    const lastLetter = getLastLetter(name)
    const index = lastLetter.charCodeAt(0) % colors.length
    return colors[index]
}


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

</style>