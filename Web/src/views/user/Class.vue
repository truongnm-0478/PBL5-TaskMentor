<template>
    <div class="container">
        <a-tabs v-model:activeKey="activeKey" >
            <a-tab-pane key="1" tab="Notification">
                <NotificationToClass />
            </a-tab-pane>
            <a-tab-pane key="2" tab="List student" force-render>
                <ListStudent />
            </a-tab-pane>
            <a-tab-pane key="3" tab="Team">
                <Team />
            </a-tab-pane>
            <a-tab-pane key="4" tab="Introduction">
                <Introduction />
            </a-tab-pane>
        </a-tabs>
    </div>
</template>
<script setup>
import { ref } from 'vue'
import router from '@/router/index.js'
import ListStudent from '@/views/user/ListStudent.vue'
import NotificationToClass from '@/views/user/NotificationToClass.vue'
import Introduction from '@/views/user/Introduction.vue'
import Team from '@/views/user/teacher/Team.vue'
const activeKey = ref('1')

const getCode = () => {
    const code = router.currentRoute.value.query.code;
}
getCode()
</script>
<style scoped>
.container {
    padding: 10px 20px;
    background-color: var(--color-white);
    overflow: auto;
    border-radius: 10px;
    height: calc(100vh - 100px);
}
</style>