<template>
    <a-form ref="formRef" layout="vertical" :model="formState" name="form_context" v-bind="layout"
            @finish="onFinish">
        <a-form-item
            name="group"
            label="Group Name"
            :rules="[{ required: true, message: 'Please input group name!' }]"
        >
            <a-input v-model:value="formState.group"/>
        </a-form-item>

        <a-form-item label="Member List" :rules="[{ required: true, message: 'Please input group name!' }]">
            <template v-if="formState.members.length">
                <table style="width: 100%">
                    <tbody>
                    <tr v-for="user in formState.members" :key="user.key">
                        <td style="width: 50%">{{ user.studentId }}</td>
                        <td>
                            <span v-if="user.leader === 'true'"><a-tag color="#f50">Leader</a-tag></span>
                            <span v-else><a-tag color="#2db7f5">Member</a-tag></span>
                        </td>
                    </tr>
                    </tbody>
                </table>
            </template>
            <template v-else>
                <a-typography-text class="ant-form-text" type="secondary">
                    (
                    <SmileOutlined/>
                    No student yet. )
                </a-typography-text>
            </template>
        </a-form-item>

        <a-form-item v-bind="tailLayout">
            <a-button html-type="submit" type="primary">Create team</a-button>
            <a-button html-type="button" style="margin: 0 8px" @click="visible = true">Add Member</a-button>
        </a-form-item>
    </a-form>
    <a-modal v-model:open="visible" title="Add member to group" @ok="onOk">
        <a-form ref="modalFormRef" :model="modalFormState" layout="vertical" name="userForm">
            <a-form-item name="studentId" label="Student ID"
                         :rules="[{ required: true, message: 'Please input the student ID' }]">
                <a-select
                    v-model:value="modalFormState.studentId"
                    show-search
                    placeholder="Select member"
                    style="width: 100%"
                    :options="options"
                ></a-select>
            </a-form-item>
            <a-form-item name="leader" label="Leader"
                         :rules="[{ required: true, message: 'Please select leader type' }]">
                <a-radio-group v-model:value="modalFormState.leader" style="width: 100%">
                    <a-radio value="false">Member</a-radio>
                    <a-radio value="true">Leader</a-radio>
                </a-radio-group>
            </a-form-item>
        </a-form>
    </a-modal>
</template>

<script setup>
import { SmileOutlined } from '@ant-design/icons-vue'
import { onMounted, reactive, ref, watch } from 'vue'
import router from '@/router/index.js'
import teamApi from '@/repositories/teamApi.js'
import { useMessageStore } from '@/stores/messageStore.js'
import classApi from '@/repositories/classApi.js'

const formRef = ref()
const modalFormRef = ref()
const visible = ref(false)
const formState = reactive({
    group: '',
    members: [],
})
const modalFormState = ref({})
const options = ref([])
watch(
    visible,
    () => {
        modalFormState.value = {}
    },
    {
        flush: 'post',
    },
)
const onOk = () => {
    modalFormRef.value.validateFields().then(() => {
        formState.members.push({
            ...modalFormState.value,
        })
        visible.value = false;
    })
}
const onFinish = () => {
    const data = {
        ...formState,
        code: router.currentRoute.value.query.code
    }
    teamApi.createTeam(data)
        .then(res => {
            useMessageStore().addMessage('success', 'Create successfully!')
        })
        .catch(err => {
            useMessageStore().addMessage('error', 'Something went wrong!')
        })
}
const layout = {
    labelCol: {
        span: 8,
    },
    wrapperCol: {
        span: 16,
    },
}
const tailLayout = {
    wrapperCol: {
        offset: 8,
        span: 16,
    },
}

const getListStudent = async () => {
    const code = router.currentRoute.value.query.code
    try {
        const res = await classApi.getListStudentInClass(code)
        options.value = res.data.map(student => ({
            value: student.studentId,
            label: student.name,
        }))
    } catch (error) {
        console.error(error)
    }
}
onMounted(() => getListStudent())
</script>

<style scoped>
#components-form-demo-form-context .user {
    margin-bottom: 8px;
}

#components-form-demo-form-context .user .ant-avatar {
    margin-right: 8px;
}

.ant-row-rtl #components-form-demo-form-context .user .ant-avatar {
    margin-right: 0;
    margin-left: 8px;
}
</style>