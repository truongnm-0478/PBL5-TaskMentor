<template>
    <TimeLine />
    <a-form ref="formRef" name="dynamic_form_nest_item" :model="dynamicValidateForm" @finish="onFinish">
        <a-space v-for="(sprint, index) in dynamicValidateForm.sprints" :key="sprint.id" style="display: flex; margin-bottom: 8px" align="baseline">
            <a-form-item
                :stage="['sprints', index, 'stage']"
                :rules="{ required: true, message: 'Enter stage'}"
            >
                <a-input v-model:value="sprint.stage" placeholder="Stage" />
            </a-form-item>

            <a-form-item
                :name="['sprints', index, 'name']"
                :rules="{ required: true,message: 'Enter name',}"
                style="width: 200px"
            >
                <a-input v-model:value="sprint.name" placeholder="Name" />
            </a-form-item>
            <MinusCircleOutlined @click="removeUser(sprint)" />
        </a-space>
        <a-form-item>
            <a-button type="dashed" block @click="addUser">
                <PlusOutlined />
                Add Stage
            </a-button>
        </a-form-item>
        <a-form-item>
            <a-button type="primary" html-type="submit">Submit</a-button>
        </a-form-item>
    </a-form>
</template>
<script setup>
import router from '@/router/index.js'
import projectApi from '@/repositories/projectApi.js'
import {onMounted, ref, reactive} from 'vue'
import {MinusCircleOutlined, PlusOutlined} from '@ant-design/icons-vue'
import {useMessageStore} from '@/stores/messageStore.js'
import TimeLine from '@/components/data/TimeLine.vue'

const formRef = ref()

const dynamicValidateForm = reactive({
    sprints: [],
})
const removeUser = item => {
    const index = dynamicValidateForm.sprints.indexOf(item)
    if (index !== -1) {
        dynamicValidateForm.sprints.splice(index, 1)
    }
}
const addUser = () => {
    dynamicValidateForm.sprints.push({
        stage: '',
        name: '',
        id: Date.now(),
    })
}
const onFinish = () => {
    const data = {
        sprints: dynamicValidateForm.sprints,
        teamId: router.currentRoute.value.query.id
    }

    projectApi.createPlan(data)
        .then(res => {
            useMessageStore().addMessage('success', 'Create successfully!')
        })
        .catch(err => {
            useMessageStore().addMessage('error', 'Create failure!')
        })
    window.location.reload()
}

</script>