<template>
    <a-list item-layout="horizontal" :data-source="data" style="width: 100%">
        <template #renderItem="{ item }">
            <a-comment class="comment" @click="handleClickItem(item)">
                <template #avatar>
                    <div class="avatar">
                        <a-avatar size="large" :style="{ backgroundColor: getColorForLastLetter(item.teacherName) }">{{ getLastLetter(item.teacherName) }}</a-avatar>
                    </div>
                </template>
                <template #content>
                    <p style="color: #1A5DB6">You have a notification in class <strong>{{item.className}}</strong> from <strong>{{item.teacherName}}</strong></p>
                    <strong></strong>
                    <p>{{ item.content }}</p>
                    <span style="color: #4880FF; font-size: 12px">{{ dayjs(item.insertTime).fromNow() }}</span>
                </template>
            </a-comment>
        </template>
    </a-list>
</template>

<script setup>
import {onMounted, ref, watchEffect} from 'vue'
import notificationApi from '@/repositories/notificationApi.js'
import dayjs from 'dayjs'
import relativeTime from 'dayjs/plugin/relativeTime'
dayjs.extend(relativeTime)
import { getColorForLastLetter } from '@/utils/colorUtils.js'
import { getLastLetter } from '@/utils/stringUtils.js'
import router from '@/router/index.js'
import {useSocketStore} from "@/stores/socketStore.js";

const data = ref([])
const isModalOpen = ref(false)
const socketStore = useSocketStore()

const getListNotification = async () => {
    await notificationApi.getListNotificationForUser()
        .then(res => {
            data.value = res.data
        })
}
onMounted(() => {getListNotification()})

const handleClickItem = (item) => {
    router.push({ path: '/class', query: { code: item.classCode } })
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
    background-color: var(--color-white);
    margin-bottom: 10px;
    cursor: pointer;
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