<template>
    <div>
        <div v-if="evaluationData.length > 0">
            <h2>User Evaluation Summary</h2>
            <a-table :dataSource="userEvaluationSummary" :columns="columns" bordered>
                <template #averageCompletedDifficulty="{ text }">
                    {{ text }}
                </template>
                <template #averageUncompletedDifficulty="{ text }">
                    {{ text }}
                </template>
            </a-table>
        </div>
        <div v-else>
            <div class="spin">
                <a-spin />
            </div>
        </div>
    </div>
</template>

<script setup>
import taskApi from '@/repositories/taskApi.js'
import router from '@/router/index.js'
import { ref, computed, onMounted } from 'vue'
import { Spin, Table } from 'ant-design-vue'

const evaluationData = ref([])

const getListTaskEvaluation = async () => {
    try {
        const res = await taskApi.getListTaskEvaluationByTeamId(router.currentRoute.value.query.id)
        console.log("DATA: ", res.data);
        evaluationData.value = res.data;
    } catch (error) {
        console.error("Error fetching evaluation data: ", error)
    }
}

onMounted(getListTaskEvaluation);

const userEvaluationSummary = computed(() => {
    const summary = {};
    evaluationData.value.forEach(task => {
        if (!summary[task.studentId]) {
            summary[task.studentId] = {
                userId: task.studentId,
                userName: userName(task.studentId),
                totalTasks: 0,
                totalCompletedTasks: 0,
                totalDifficulty: 0,
                totalUncompletedTasks: 0,
                totalUncompletedDifficulty: 0
            };
        }
        summary[task.studentId].totalTasks++;
        if (task.status === 3) {
            summary[task.studentId].totalCompletedTasks++;
            summary[task.studentId].totalDifficulty += task.difficulty;
        } else {
            summary[task.studentId].totalUncompletedTasks++;
            summary[task.studentId].totalUncompletedDifficulty += task.difficulty;
        }
    });
    for (const userId in summary) {
        const userSummary = summary[userId];
        userSummary.averageCompletedDifficulty = userSummary.totalCompletedTasks > 0 ? (userSummary.totalDifficulty / userSummary.totalCompletedTasks).toFixed(3) : 0;
        userSummary.averageUncompletedDifficulty = userSummary.totalUncompletedTasks > 0 ? (userSummary.totalUncompletedDifficulty / userSummary.totalUncompletedTasks).toFixed(3) : 0;
    }
    return Object.values(summary);
});

const userName = (userId) => {
    const user = evaluationData.value.find(item => item.studentId === userId);
    return user ? user.userName : 'Unknown';
};

const columns = [
    {
        title: 'User ID',
        dataIndex: 'userId',
        key: 'userId'
    },
    {
        title: 'User Name',
        dataIndex: 'userName',
        key: 'userName'
    },
    {
        title: 'Total Tasks',
        dataIndex: 'totalTasks',
        key: 'totalTasks'
    },
    {
        title: 'Completed Tasks',
        dataIndex: 'totalCompletedTasks',
        key: 'totalCompletedTasks'
    },
    {
        title: 'Uncompleted Tasks',
        dataIndex: 'totalUncompletedTasks',
        key: 'totalUncompletedTasks'
    },
    {
        title: 'Avg. Completed Difficulty',
        dataIndex: 'averageCompletedDifficulty',
        key: 'averageCompletedDifficulty',
        scopedSlots: { customRender: 'averageCompletedDifficulty' }
    },
    {
        title: 'Avg. Uncompleted Difficulty',
        dataIndex: 'averageUncompletedDifficulty',
        key: 'averageUncompletedDifficulty',
        scopedSlots: { customRender: 'averageUncompletedDifficulty' }
    }
];

</script>

<style scoped>
.spin {
    text-align: center;
    background: rgba(0, 0, 0, 0.05);
    border-radius: 4px;
    margin-bottom: 20px;
    padding: 30px 50px;
    margin: 20px 0;
}
</style>
