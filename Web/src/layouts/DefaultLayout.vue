<template>
    <a-layout style="min-height: 100vh" ref="layout">
        <SiteSideBar ref="sidebar" style="z-index: 4"/>
        <a-layout>
            <a-layout-header :style="headerStyle">
                <SiteHeader />
            </a-layout-header>
            <a-layout-content style="margin:80px 16px 0 16px; overflow: hidden;">
                <router-view></router-view>
            </a-layout-content>
        </a-layout>
    </a-layout>
</template>

<script lang="ts">
import {
    PieChartOutlined,
    DesktopOutlined,
    UserOutlined,
    TeamOutlined,
    FileOutlined,
} from '@ant-design/icons-vue';
import { defineComponent, ref, watch } from 'vue';
import SiteSideBar from "@/components/partials/SiteSideBar.vue";
import SiteHeader from "@/components/partials/SiteHeader.vue";
export default defineComponent({
    components: {
        PieChartOutlined,
        DesktopOutlined,
        UserOutlined,
        TeamOutlined,
        FileOutlined,
        SiteSideBar,
        SiteHeader
    },
    data() {
        return {
            collapsed: ref<boolean>(false),
            selectedKeys: ref<string[]>(['1']),
            headerStyle: {
                textAlign: 'center',
                backgroundColor: '#fff',
                lineHeight: '64px',
                position: 'fixed',
                right: 0,
                width: '100%',
                zIndex: 3
            }
        };
    },
    mounted() {
        this.setHeaderWidth();
        window.addEventListener('resize', this.setHeaderWidth);
    },
    methods: {
        setHeaderWidth() {
            const layoutWidth = (this.$refs.layout as HTMLElement).offsetWidth;
            const sidebarWidth = (this.$refs.sidebar as HTMLElement).offsetWidth;
            this.headerStyle.width = `${layoutWidth - sidebarWidth}px`;
        }
    },
    beforeUnmount() {
        window.removeEventListener('resize', this.setHeaderWidth);
    }
});
</script>

<style>
#components-layout-demo-side .logo {
    height: 32px;
    margin: 16px;
    background: rgba(255, 255, 255, 0.3);
}

.site-layout .site-layout-background {
    background: #fff;
}

[data-theme='dark'] .site-layout .site-layout-background {
    background: #141414;
}
</style>
