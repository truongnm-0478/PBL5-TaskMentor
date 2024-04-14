<template class="container">
    <a-layout-sider
        theme="light"
        width="260"
        v-model:collapsed="isCollapsed"
        collapsible
        style="position: sticky; top: 0; height: 100vh;"
        class="main"
    >
        <a-menu
            theme="light"
            v-model:selectedKeys="selectedKeys"
            mode="inline"
            class="menu"
            style="border-right: none"
        >
            <div class="logo" v-show="isCollapsed">
                <img src="@/assets/img/logo.png" alt="Logo"/>
            </div>
            <div class="logo" v-show="!isCollapsed">
                <img src="@/assets/img/logo.png" alt="Logo"/>
                <h1 class="brand">Task </h1>
                <h1 class="brand brand-sub">&nbsp;Mentor</h1>
            </div>
            <a-menu-item key="1">
                <pie-chart-outlined/>
                <span>Option 1</span>
            </a-menu-item>
            <a-menu-item key="2">
                <desktop-outlined/>
                <span>Option 2</span>
            </a-menu-item>
            <a-sub-menu key="sub1">
                <template #title>
            <span>
              <user-outlined/>
              <span>User</span>
            </span>
                </template>
                <a-menu-item key="3">Tom</a-menu-item>
                <a-menu-item key="4">Bill</a-menu-item>
                <a-menu-item key="5">Alex</a-menu-item>
            </a-sub-menu>
            <a-sub-menu key="sub2">
                <template #title>
            <span>
              <team-outlined/>
              <span>Team</span>
            </span>
                </template>
                <a-menu-item key="6">Team 1</a-menu-item>
                <a-menu-item key="8">Team 2</a-menu-item>
            </a-sub-menu>
            <a-menu-item key="9">
                <file-outlined/>
                <span>File</span>
            </a-menu-item>
        </a-menu>
    </a-layout-sider>
</template>

<script lang="ts">
import {defineComponent, ref} from 'vue';
import {DesktopOutlined, FileOutlined, PieChartOutlined, TeamOutlined, UserOutlined} from "@ant-design/icons-vue";

export default defineComponent({
    components: {DesktopOutlined, TeamOutlined, PieChartOutlined, FileOutlined, UserOutlined},
    props: {
        selectedKeys: {
            type: Array,
            required: true,
        },
    },
    setup() {
        const isCollapsed = ref(false); // Biến để theo dõi trạng thái của sidebar đã được collapse hay không
        const collapsedWidth = ref(80); // Chiều rộng khi sidebar được collapse

        return {isCollapsed, collapsedWidth};
    },
});
</script>

<style>

.main {
    position: sticky;
    top: 0;
    height: 100vh;
}

.menu {
    min-height: 100vh;
}

.logo {
    height: 80px;
    display: flex;
    align-items: center;
    justify-content: center;
}

.logo img {
    height: 50px;
}

.brand {
    display: flex;
    align-items: center;
    justify-content: center;
    font-size: 22px;
    line-height: 60px;
    margin-bottom: 0;
}

.brand-sub {
    color: var(--color-blue);
}
</style>
