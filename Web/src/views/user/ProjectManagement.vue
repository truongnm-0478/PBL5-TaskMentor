<template>
    <div class="container">
        <a-tabs v-model:activeKey="activeKey" >
            <a-tab-pane key="1" tab="Requirement">
                <div class="container-add">
                    <span v-if="role === 0 || role === 1">
                        <a-button v-if="isWriteRequirement === false"  type="primary" @click="handleOpenWriteRequirement" >Create <PlusOutlined /></a-button>
                        <a-button v-if="isWriteRequirement === true" type="primary" @click="handleOpenWriteRequirement" >Back <EnterOutlined /></a-button>
                    </span>
                </div>
                <Requirement v-if="isWriteRequirement" />
                <ListRequirement v-else />
            </a-tab-pane>
            <a-tab-pane key="2" tab="Comment">
                <Comment />
            </a-tab-pane>
            <a-tab-pane key="3" tab="Plan">
                <Timeline />
            </a-tab-pane>
            <a-tab-pane key="4" tab="Task">
                <Task />
            </a-tab-pane>
            <a-tab-pane key="5" tab="Evaluation">
                <TeamEvaluation />
            </a-tab-pane>
        </a-tabs>
    </div>
</template>
<script setup>
import { ref } from 'vue'
import router from '@/router/index.js'
import Requirement from '@/views/user/student/Requirement.vue'
import ListRequirement from '@/views/user/ListRequirement.vue'
import { PlusOutlined, EnterOutlined } from '@ant-design/icons-vue'
import { useUserStore } from '@/stores/userStore.js'
import Comment from '@/views/user/Comment.vue'
import Timeline from '@/views/user/Plan.vue'
import Task from '@/views/user/Task.vue'
import TeamEvaluation from "@/views/user/TeamEvaluation.vue";

const role = ref(useUserStore().getUserRole)
const activeKey = ref('1')
const isWriteRequirement = ref(false)
const projectId = ref(null)


const getCode = () => {
    const code = router.currentRoute.value.query.code
}
getCode()

const handleOpenWriteRequirement = () => {
    isWriteRequirement.value = !isWriteRequirement.value
}
</script>
<style scoped>
.container {
    padding: 10px 20px;
    background-color: var(--color-white);
    overflow: auto;
    border-radius: 10px;
    height: calc(100vh - 100px);
}
.container-add {
    display: flex;
    justify-content: end;
    margin-bottom: 16px;
}
</style>