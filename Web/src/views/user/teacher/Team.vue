<template>
    Trang giáo viên:
    + có 1 ô nút gợi ý cách chia chẵn số <br/>
    + có 1 nút nhập số lượng thành viên mong muốn của mỗi team <br />
    + có 1 nút random các thành viên trong team <br />
    <a-form ref="formRef" :model="formState" name="form_context" v-bind="layout" @finish="onFinish">
        <a-form-item
            name="group"
            label="Group Name"
            :rules="[{ required: true, message: 'Please input group name!' }]"
        >
            <a-input v-model:value="formState.group" />
        </a-form-item>

        <a-form-item label="User List">
            <template v-if="formState.users.length">
                <ul>
                    <template v-for="user in formState.users" :key="user.key">
                        <li class="user">
                            <a-avatar>
                                <template #icon><UserOutlined /></template>
                            </a-avatar>
                            {{ user.name }} - {{ user.age }}
                        </li>
                    </template>
                </ul>
            </template>
            <template v-else>
                <a-typography-text class="ant-form-text" type="secondary">
                    (
                    <SmileOutlined />
                    No user yet. )
                </a-typography-text>
            </template>
        </a-form-item>

        <a-form-item v-bind="tailLayout">
            <a-button html-type="submit" type="primary">Submit</a-button>
            <a-button html-type="button" style="margin: 0 8px" @click="visible = true">Add User</a-button>
        </a-form-item>
    </a-form>
    <a-modal v-model:open="visible" title="Basic Drawer" @ok="onOk">
        <a-form ref="modalFormRef" :model="modalFormState" layout="vertical" name="userForm">
            <a-form-item name="name" label="User Name" :rules="[{ required: true }]">
                <a-input v-model:value="modalFormState.name" />
            </a-form-item>
            <a-form-item name="age" label="User Age" :rules="[{ required: true }]">
                <a-input-number v-model:value="modalFormState.age" />
            </a-form-item>
        </a-form>
    </a-modal>
</template>
<script setup>
import { reactive, ref, watch, toRaw } from 'vue'
import { SmileOutlined } from '@ant-design/icons-vue'
const formRef = ref();
const modalFormRef = ref();
const visible = ref(false);
const formState = reactive({
    group: '',
    users: [],
});
const modalFormState = ref({});
watch(
    visible,
    () => {
        modalFormState.value = {};
    },
    {
        flush: 'post',
    },
);
const onOk = () => {
    modalFormRef.value.validateFields().then(() => {
        formState.users.push({
            ...modalFormState.value,
            key: Date.now(),
        });
        visible.value = false;
    });
};
const onFinish = () => {
    console.log('Finish:', toRaw(formState));
};
const layout = {
    labelCol: {
        span: 8,
    },
    wrapperCol: {
        span: 16,
    },
};
const tailLayout = {
    wrapperCol: {
        offset: 8,
        span: 16,
    },
};
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