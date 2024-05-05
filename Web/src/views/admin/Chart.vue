<template>
    <h3 style="font-weight: 600">Dashboard</h3>
    <div style="padding: 2px">
        <a-row :gutter="16">
            <a-col :span="24" :lg="{ span: 12 }" :xl="{ span: 6 }">
                <a-card class="card card-blue sha-down">
                    <a-row>
                        <a-col flex="auto">
                            <h4 class="card-title" style="color: var(--color-blue)">Total User</h4>
                            <span class="card-value">200</span>
                        </a-col>
                        <a-col flex="40px" class="icon" style="color: #DCDFEB;"><UserOutlined /></a-col>
                    </a-row>
                </a-card>
            </a-col>
            <a-col :span="24" :lg="{ span: 12 }" :xl="{ span: 6 }">
                <a-card class="card card-green sha-down">
                    <a-row>
                        <a-col flex="auto">
                            <h4 class="card-title" style="color: #1BC88A">Projects</h4>
                            <span class="card-value">+20</span>
                        </a-col>
                        <a-col flex="40px" class="icon" style="color: #DCDFEB;"><ProjectOutlined /></a-col>
                    </a-row>
                </a-card>
            </a-col>
            <a-col :span="24" :lg="{ span: 12 }" :xl="{ span: 6 }">
                <a-card class="card card-yellow sha-down">
                    <a-row>
                        <a-col flex="auto">
                            <h4 class="card-title" style="color: #F6C23D">Teachers</h4>
                            <span class="card-value">
                                <span style="font-weight: 600">+3</span>
                                <span style="width: 70%; margin: 0 10%">
                                    <a-progress :percent="50" :show-info="false" stroke-color="#F6C23D" />
                                </span>
                            </span>
                        </a-col>
                        <a-col flex="40px" class="icon" style="color: #DCDFEB;"><SolutionOutlined /></a-col>
                    </a-row>
                </a-card>
            </a-col>
            <a-col :span="24" :lg="{ span: 12 }" :xl="{ span: 6 }">
                <a-card class="card card-bluelight sha-down">
                    <a-row>
                        <a-col flex="auto">
                            <h4 class="card-title" style="color: #37B8CC">Teams</h4>
                            <span class="card-value">+12</span>
                        </a-col>
                        <a-col flex="40px" class="icon" style="color: #DCDFEB;"><TeamOutlined /></a-col>
                    </a-row>
                </a-card>
            </a-col>
        </a-row>
        <a-row>
            <a-col :span="24" :xl="{ span: 16 }" :lg="{ span: 16 }" >
                <apexchart class="chart sha-down" type="area" :options="areaChartOptions" :series="areaChartSeries"></apexchart>
            </a-col>
            <a-col :span="24" :xl="{ span: 8 }" :lg="{ span: 8 }">
                <apexchart class="chart sha-down donut" type="donut" :options="chartOptions" :series="series"></apexchart>
            </a-col>
        </a-row>
    </div>
</template>
<script setup>
import { TeamOutlined, ProjectOutlined, UserOutlined, SolutionOutlined } from '@ant-design/icons-vue'
const series = [30, 20, 33]
const chartOptions = {
    chart: {
        type: 'donut',
    },
    labels: ['Student', 'Leader', 'Teacher'],
    colors: ['#4E73DF', '#35B9CC', '#1BC88A', '#E74A3A'],
    legend: {
        position: 'bottom',
    },
}

const areaChartSeries = [
    {
        name: 'Todo',
        data: [30, 40, 60, 70, 91, 125]
    },
    {
        name: 'In progress',
        data: [20, 25, 23, 50, 55, 60]
    },
    {
        name: 'In review ',
        data: [10, 15, 35, 40, 45, 50]
    },
    {
        name: 'Complete',
        data: [5, 15, 20, 25, 30, 45]
    }
]

const areaChartOptions = {
    chart: {
        type: 'area',
        toolbar: {
            show: false
        }
    },
    colors: ['#4E73DF', '#1BC88A', '#F6C23D', '#E74A3A'],
    xaxis: {
        categories: ['Jan', 'Mar', 'May', 'Jul', 'Sep', 'Nov'],
    },
    dataLabels: {
        enabled: false,
    },
}
</script>


<style scoped>
.ant-statistic-title {
    font-weight: 800;
}

.card {
    background-color: var(--color-white);
    margin-bottom: 10px;
    height: 120px;
}

.card-title {
    font-size: 12px;
    font-weight: bold;
    text-transform: uppercase;
}

.card-blue {
    border-left: 4px solid var(--color-blue);
}

.card-green {
    border-left: 4px solid #1BC88A;
}

.card-bluelight {
    border-left: 4px solid #37B8CC;
}

.card-yellow {
    border-left: 4px solid #F6C23D;
}

.icon {
    font-size:  30px;
    display: flex;
    justify-content: center;
    align-items: center
}

.card-value {
    font-size: 24px;
    font-weight: 600;
    color: #595C69;
    display: flex;
    align-items: center;
}

.chart {
    margin-bottom: 20px;
    background-color: var(--color-white);
    padding: 10px;
    border-radius: 6px;
    display: flex;
    justify-content: center;
    align-items: center;
    height: 500px;
}

.donut {
    padding: 0;
    margin-left: 20px;
}

@media (max-width: 1200px) {
    .donut {
        //padding: 20px;
        margin-left: 0;
    }
}


.sha-down {
    box-shadow: 0 .15rem 1.75rem 0 rgb(245,255,255, 0.5)!important;
}
</style>
