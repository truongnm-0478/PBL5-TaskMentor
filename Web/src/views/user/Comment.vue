<template>
    <div class="comment-container">
        <a-list
            v-if="comments.length"
            :data-source="comments"
            :header="`${comments.length} ${comments.length > 1 ? 'replies' : 'reply'}`"
            item-layout="horizontal"
            class="comment-list"
        >
            <template #renderItem="{ item }">
                <a-list-item :style="{ backgroundColor: item.status ? 'rgb(230,244,255)' : '#fff' }">
                    <a-comment
                        :author="item.author"
                        :content="item.content"
                        :datetime="item.datetime"
                        style="width: 100%"
                    >
                        <template #avatar>
                            <a-avatar :style="{ backgroundColor: getColorForLastLetter(item.author) }">{{ getLastLetter(item.author) }}</a-avatar>
                        </template>
                        <a-dropdown>
                            <template #overlay>
                                <a-menu>
                                    <a-button @click="handleDelete(item)" type="text" danger style="width: 100%">Delete</a-button>
                                </a-menu>
                            </template>
                            <a-button type="link" class="more-button">
                                <EllipsisOutlined />
                            </a-button>
                        </a-dropdown>
                    </a-comment>
                </a-list-item>
            </template>
        </a-list>
    </div>
</template>
<script setup>
import {onMounted, ref} from 'vue'
import dayjs from 'dayjs'
import relativeTime from 'dayjs/plugin/relativeTime'
import projectApi from '@/repositories/projectApi.js'
import router from "@/router/index.js"
import { EllipsisOutlined } from '@ant-design/icons-vue'
import ProjectApi from '@/repositories/projectApi.js'
import {useMessageStore} from "@/stores/messageStore.js";

dayjs.extend(relativeTime)
const comments = ref([])
const value = ref('')
const getListComment = () => {
    const id = router.currentRoute.value.query.id
    projectApi.getListComment(id)
        .then(res => {
            comments.value = res.data.map(item => {
                return {
                    id: item.id,
                    status: item.status,
                    author: item.name,
                    avatar: `https://api.adorable.io/avatars/{size}/${item.name}`,
                    content: item.comment,
                    datetime: dayjs(item.insertTime).fromNow()
                }
            })
        })
        .catch(err => {
            console.log(err)
        })
}
onMounted(() => {
    getListComment()
})
const getColorForLastLetter = (name) => {
    const colors = ['#1A5DB6', '#2E8B57', '#FF8C00', '#FF1493', '#8A2BE2']
    const lastLetter = getLastLetter(name)
    const index = lastLetter.charCodeAt(0) % colors.length
    return colors[index]
}
const getLastLetter = (name) => {
    return name.charAt(0).toUpperCase()
}
const showMore = (item) => {
    console.log('More content:', item.content);
}

const handleDelete = (item) => {
    projectApi.deleteComment(item.id)
        .then(res => {
            useMessageStore().addMessage('success', 'Delete successfully!')
            getListComment()
        })
        .catch(err => {
            useMessageStore().addMessage('error', 'Delete failure!')
        })
}
</script>
<style scoped>
.comment-container {
    margin: 10px;
}
.comment-list {
    width: 100%;
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

