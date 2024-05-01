<template>
    <a-timeline mode="alternate">
        <a-timeline-item v-for="(item, index) in planning" :key="item.id" :color="getItemColor(index)">
            <span @click="editStage(item)" style="cursor: pointer;">
                <strong style="color: #1A5DB6">Stage {{ item.stage }}:</strong> {{ item.name }}
            </span>
        </a-timeline-item>
    </a-timeline>
    <!-- Modal -->
    <a-modal
        v-model:open="visible"
        title="Create a new collection"
        ok-text="Create"
        cancel-text="Cancel"
    >
        <a-form ref="formRef" :model="formState" layout="vertical" name="form_in_modal">
            <a-input v-model:value="formState.id" style="display: none" />
            <a-form-item name="stage" label="Stage" :rules="[{ required: true, message: 'Please input the stage!' }]">
                <a-input v-model:value="formState.stage" />
            </a-form-item>
            <a-form-item name="name" label="Name">
                <a-textarea v-model:value="formState.name" :rules="[{ required: true, message: 'Please input the name!' }]" />
            </a-form-item>
        </a-form>
        <template #footer>
            <a-button key="delete" @click="deleteEvent" type="primary" danger>Delete</a-button>
            <a-button key="cancel" @click="cancelEvent">Cancel</a-button>
            <a-button key="ok" @click="updateEvent" type="primary">OK</a-button>
        </template>
    </a-modal>
</template>

<script setup>
import { onMounted, ref, toRaw, reactive } from 'vue'
import projectApi from '@/repositories/projectApi.js'
import router from '@/router/index.js'
import { useMessageStore } from '@/stores/messageStore.js'

const planning = ref([])
const formRef = ref();
const visible = ref(false);
const formState = reactive({
    stage: 0,
    name: '',
    id: 0,
})

const getPlanning = () => {
    projectApi.getPlan(router.currentRoute.value.query.id)
        .then(res => {
            planning.value = res.data.map(sprint => ({
                id: sprint.sprintId,
                stage: sprint.stage,
                name: sprint.name,
                color: sprint.color || 'blue'
            }))
        })
        .catch(err => {
            console.log("ERR: ", err)
        })
}

const getItemColor = (index) => {
    if (index === 0) return 'green'
    else if (index === planning.value.length - 1) return 'red'
    else return 'blue'
}

const editStage = (stage) => {
    formState.stage = stage.stage
    formState.name = stage.name
    formState.id = stage.id
    visible.value = true
    console.log("UPDATE: ", stage)
}

onMounted(getPlanning)

const updateEvent = () => {
    formRef.value
        .validateFields()
        .then(values => {
            projectApi.updatePlan(toRaw(formState))
                .then(res => {
                    useMessageStore().addMessage('success', 'Update successfully!')
                    getPlanning()
                })
                .catch(err => {
                    useMessageStore().addMessage('error', 'Update failure!')
                })
            visible.value = false
            formRef.value.resetFields()
        })
        .catch(info => {
            console.log('Validate Failed:', info)
        })
}

const deleteEvent = () => {
    projectApi.deletePlan(formState.id)
        .then(res => {
            useMessageStore().addMessage('success', 'Delete successfully!')
            getPlanning()
        })
        .catch(err => {
            useMessageStore().addMessage('error', 'Delete failure!')
        })
    visible.value = false
}

const cancelEvent = () => {visible.value = false}
</script>

<style scoped>
.collection-create-form_last-form-item {
    margin-bottom: 0;
}
</style>
