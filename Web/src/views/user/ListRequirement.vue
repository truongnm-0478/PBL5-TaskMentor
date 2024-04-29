<template>
    <div class="list-container">
        <a-collapse v-model:activeKey="activeKey" style="background: rgb(255, 255, 255)" :expand-icon-position="expandIconPosition">
            <a-collapse-panel v-for="(item, index) in listRequirement" :key="index">
                <template #header>
                    <div class="custom-header">
                        <span>
                            {{ item.title }}
                            <a-tag :color="listRequirement.length - 1 === index ? '#f50' : '#2db7f5'">ver {{ index + 1}}.0</a-tag>
                        </span>

                    </div>
                </template>
                <p v-html="item.content"></p>
                <template #extra>
                    <a-tooltip title="Comment">
                        <a-button type="link" :icon="h(CommentOutlined)" @click="handleComment(item)" style="margin: 0 3px"/>
                    </a-tooltip>
                    <a-tooltip title="Delete">
                        <a-button danger type="link" :icon="h(DeleteOutlined)"  @click="handleClick(item)" style="margin: 0 3px"/>
                    </a-tooltip>
                    <a-tooltip title="Approve">
                        <a-button v-if="star.includes(item.id)" type="link" :icon="h(StarFilled)" style="margin: 0 3px; color: var(--color-yellow);"/>
                        <a-button v-else type="link" :icon="h(StarOutlined)"  @click="handleApprove(item)" style="margin: 0 3px; color: var(--color-yellow);"/>
                    </a-tooltip>
                </template>
            </a-collapse-panel>
        </a-collapse>
        <a-modal v-model:open="open" title="Comment" @ok="handleOk">
            <a-form-item>
                <a-textarea v-model:value="comment" :rows="4" />
            </a-form-item>
            <a-form-item style="display: none">
                <a-input v-model:value="id" :rows="4" />
            </a-form-item>
        </a-modal>
        <a-modal v-model:open="openApprove" title="Comment" @ok="handleSubmit">
            <a-form-item>
                <a-textarea v-model:value="comment" :rows="4" />
            </a-form-item>
            <a-form-item style="display: none">
                <a-input v-model:value="id" :rows="4" />
            </a-form-item>
        </a-modal>
    </div>
</template>
<script setup>
import { createVNode, h, onMounted, ref } from 'vue';
import projectApi from '@/repositories/projectApi.js';
import router from '@/router/index.js';
import pako from 'pako';
import { DeleteOutlined, ExclamationCircleOutlined, StarOutlined, StarFilled, CommentOutlined } from '@ant-design/icons-vue';
import { Modal } from 'ant-design-vue';
import { useMessageStore } from '@/stores/messageStore.js';
const id = ref(0);
const comment = ref('');
const expandIconPosition = ref('start');
const open = ref(false);
const openApprove = ref(false);
const listRequirement = ref([]);
const activeKey = ref('1');
const star = ref([]);
const getListRequirement = () => {
    const id = router.currentRoute.value.query.id;
    projectApi.getListRequirement(id)
        .then(res => {
            listRequirement.value = res.data.map(item => {
                // Convert Base64 String to byte[]
                const compressedData = atob(item.content)
                // Convert string data to Uint8Array
                const charData = compressedData.split('').map(x => x.charCodeAt(0))
                const binData = new Uint8Array(charData);
                // Decompress data
                const data = pako.inflate(binData, { to: 'string' })
                return {
                    id: item.id,
                    title: item.title,
                    content: data,
                }
            })
            activeKey.value = (listRequirement.value.length - 1).toString();
        })
        .catch(err => {
            console.log("ERR: ", err);
        })
}
const getListComment = () => {
    const id = router.currentRoute.value.query.id;
    projectApi.getListComment(id)
        .then(res => {
            for (let i = 0; i < res.data.length; i++) {
                if (res.data[i].status === true) {
                    star.value.push(res.data[i].projectId);
                }
            }
        })
        .catch(err => {
            console.log(err);
        })
}
onMounted(() => {
    getListRequirement()
    getListComment()
})
const handleClick = item => {
    event.stopPropagation();
    Modal.confirm({
        title: 'Do you want to delete the requirement?',
        icon: createVNode(ExclamationCircleOutlined),
        onOk() {
            projectApi.deleteRequirement(item.id)
                .then(() => {
                    useMessageStore().addMessage('success', 'Successfully!');
                    getListRequirement();
                })
                .catch(() => {
                    useMessageStore().addMessage('error', 'Something went wrong!');
                });
        },
        onCancel() {
            console.log('Cancel');
        },
    });
};
const handleApprove = item => {
    id.value = item.id;
    openApprove.value = true;
    event.stopPropagation();
};
const handleComment = item => {
    id.value = item.id;
    open.value = true;
    event.stopPropagation();
};
const handleSubmit = () => {
    const data = {
        projectId: id.value,
        status: true,
        comment: comment.value
    };
    projectApi.addComment(data)
        .then(res => {
            useMessageStore().addMessage('success', 'Create successfully!')
            comment.value = '';
        })
        .catch(err => {
            console.log(err);
            useMessageStore().addMessage('error', 'Create failed!')
        });
    openApprove.value = false;
};
const handleOk = () => {
    const data = {
        projectId: id.value,
        status: false,
        comment: comment.value
    };
    projectApi.addComment(data)
        .then(res => {
            useMessageStore().addMessage('success', 'Create successfully!')
            comment.value = '';
        })
        .catch(err => {
            console.log(err)
            useMessageStore().addMessage('error', 'Create failed!')
        });
    open.value = false
}
</script>
<style scoped>
.list-container {
    padding: 10px;
    background-color: var(--color-white);
    overflow: auto;
    border-radius: 10px;
    height: 100%;
}
</style>
