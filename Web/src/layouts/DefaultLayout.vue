<template>
    <a-layout id="app">
        <!-- Sidebar -->
        <a-layout-sider>
            <Sidebar />
        </a-layout-sider>

        <a-layout>
            <!-- Header with Tabs -->
            <a-layout-header style="background: #fff; padding: 0">
                <TabBar :selected-tab="selectedTab" @tabChange="handleTabChange" />
            </a-layout-header>

            <!-- Content area -->
            <a-layout-content style="margin: 24px 16px 0">
                <router-view></router-view>
            </a-layout-content>

            <!-- Footer -->
            <a-layout-footer>
                <FooterSection />
            </a-layout-footer>
        </a-layout>
    </a-layout>
</template>

<script>
import FooterSection from '../components/partials/SiteFooter.vue';
import TabBar from '../components/partials/SiteNavbar.vue';
import Sidebar from '../components/partials/SiteSideBar.vue';

export default {
    data() {
        return {
            selectedTab: 'home',
        };
    },
    methods: {
        handleTabChange(key) {
            // Handle tab change event
            this.selectedTab = key;
        },
    },
    components: {
        TabBar,
        FooterSection,
        Sidebar,
    },
};
</script>

<style scoped>
/* Your layout styles go here */
</style>