<template>
    <div class="container-add">
        <a-button type="primary" @click="showDrawer">
            <PlusOutlined/>
            Add Team
        </a-button>
    </div>
    <a-drawer
        title="Create a new account"
        :width="720"
        :open="open"
        :body-style="{ paddingBottom: '80px' }"
        :footer-style="{ textAlign: 'right' }"
        @close="onClose"
    >
        <AddTeam />
    </a-drawer>
    <TeamTable />
</template>
<script setup>
import { ref } from 'vue'
import { PlusOutlined } from '@ant-design/icons-vue'
import AddTeam from '@/components/form/AddTeam.vue'
import TeamTable from '@/components/Table/TeamTable.vue'

const open = ref(false)
const showDrawer = () => {
    open.value = true
}
const onClose = () => {
    open.value = false
}
</script>

<style scoped>

.container-add {
    display: flex;
    justify-content: end;
    margin-bottom: 16px;
}
</style>