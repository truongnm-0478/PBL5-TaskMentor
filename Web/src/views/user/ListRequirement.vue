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
                    <a-popover trigger="hover">
                        <template #content>
                            <a-button @click="handleClick(item)" type="text" style="width: 100%">Delete</a-button>
                        </template>
                        <EllipsisOutlined @click="handleApprove" />
                    </a-popover>
                </template>

            </a-collapse-panel>
        </a-collapse>
    </div>
</template>
<script setup>
import {createVNode, onMounted, ref, watch} from 'vue'
import projectApi from '@/repositories/projectApi.js'
import router from '@/router/index.js'
import pako from 'pako'
import {EllipsisOutlined, ExclamationCircleOutlined} from '@ant-design/icons-vue'
import {Modal} from 'ant-design-vue'
import {useMessageStore} from '@/stores/messageStore.js'
const expandIconPosition = ref('start')

const listRequirement = ref([])
const activeKey = ref('1')
const getListRequirement = () => {
    const id = router.currentRoute.value.query.id
    projectApi.getListRequirement(id)
        .then(res => {
            listRequirement.value = res.data.map(item => {
                // Convert Base64 String to byte[]
                const compressedData = atob(item.content)
                // Convert string data to Uint8Array
                const charData = compressedData.split('').map(x => x.charCodeAt(0))
                const binData = new Uint8Array(charData)
                // Decompress data
                const data = pako.inflate(binData, {to: 'string'})
                return {
                    id: item.id,
                    title: item.title,
                    content: data,
                }
            })
            activeKey.value = (listRequirement.value.length - 1).toString()
        })
        .catch(err => {
            console.log("ERR: ", err)
        })

}
onMounted(() => {
    getListRequirement()
})

const handleClick = item => {
   console.log(item)
    Modal.confirm({
        title: 'Do you Want to requirement?',
        icon: createVNode(ExclamationCircleOutlined),
        onOk() {
            projectApi.deleteRequirement(item.id)
                .then(() => {
                    useMessageStore().addMessage('success', 'Successfully!')
                    getListRequirement()
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

const handleApprove = event => {
    console.log("EVENT: ", event)
    // If you don't want click extra trigger collapse, you can prevent this:
    event.stopPropagation();
};
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
