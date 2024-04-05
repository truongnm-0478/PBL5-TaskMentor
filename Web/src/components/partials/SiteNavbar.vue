
<template>
    <a-layout class="layout">
        <a-layout-header>
            <div class="logo" />
            <a-menu theme="dark" mode="horizontal" :default-selected-keys="['2']" :items="items1"
                style="line-height: 64px;">
            </a-menu>
        </a-layout-header>
    </a-layout>
</template>

<script>
import { LaptopOutlined, NotificationOutlined, UserOutlined } from '@ant-design/icons-vue';
import { theme } from 'ant-design-vue';
import { computed } from 'vue';


export default {
    setup() {
        const items1 = ['1', '2', '3'].map((key) => ({
            key,
            label: `nav ${key}`,
        }));

        const items2 = [UserOutlined, LaptopOutlined, NotificationOutlined].map((icon, index) => {
            const key = `sub${index + 1}`;
            return {
                key,
                icon: () => icon,
                label: `subnav ${index + 1}`,
                children: new Array(4).fill(null).map((_, j) => ({
                    key: index * 4 + j + 1,
                    label: `option${index * 4 + j + 1}`,
                })),
            };
        });

        const { token } = theme.useToken();
        const colorBgContainer = computed(() => token.value.colorBgContainer);
        const borderRadiusLG = computed(() => token.value.borderRadiusLG);

        return {
            items1,
            items2,
            colorBgContainer,
            borderRadiusLG,
        };
    },
};
</script>

<style scoped>
.layout {
    min-height: 100vh;
}

.logo {
    width: 120px;
    height: 31px;
    background: rgba(255, 255, 255, 0.2);
    margin: 16px 24px 16px 0;
    float: left;
}

.menu-item {
    line-height: 64px;
}
</style>
