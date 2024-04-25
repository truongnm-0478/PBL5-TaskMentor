<template>
    <div class="container">
        <a-tabs v-model:activeKey="activeKey" >
            <a-tab-pane key="1" tab="Notification">
                <NotificationToClass />
            </a-tab-pane>
            <a-tab-pane key="2" tab="List student" force-render>
                <ListStudent />
            </a-tab-pane>
            <a-tab-pane key="3" tab="Introduction"></a-tab-pane>
        </a-tabs>
    </div>
</template>
<script setup>
import { ref } from 'vue'
import router from "@/router/index.js";
import ListStudent from '@/views/user/teacher/ListStudent.vue'
import NotificationToClass from "@/views/user/teacher/NotificationToClass.vue";
const activeKey = ref('1')

const getCode = () => {
    const code = router.currentRoute.value.query.code;
    console.log(code);
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