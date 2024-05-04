<template class="container">
    <a-layout-sider
        theme="light"
        width="220"
        v-model:collapsed="isCollapsed"
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
            <div class="logo">
                <img src="@/assets/img/logo.png" alt="Logo"/>
                <div v-show="!isCollapsed" class="brand">
                    <h1 class="brand">Task </h1>
                    <h1 class="brand brand-sub">&nbsp;Mentor</h1>
                </div>
            </div>
            <a-menu-item key="1" @click="handleMenuItemClick('1')">
                <BellOutlined/>
                <span>Notification</span>
            </a-menu-item>
            <a-menu-item key="3" @click="handleMenuItemClick('3')">
                <CalendarOutlined/>
                <span>Appointment</span>
            </a-menu-item>

            <a-sub-menu key="sub1">
                <template #title>
                    <span>
                      <team-outlined/>
                      <span>Class</span>
                    </span>
                </template>
                <a-menu-item key="4" v-if="role === '2'" @click="handleMenuItemClick('4')">Create class</a-menu-item>
                <a-menu-item key="5" v-if="role === '0' || role === '1'" @click="handleMenuItemClick('5')">Join class</a-menu-item>
                <a-menu-item key="6" v-if="role === '2'" @click="handleMenuItemClick('6')">List class</a-menu-item>
                <a-menu-item key="8" v-if="role === '0' || role === '1'" @click="handleMenuItemClick('8')">List class</a-menu-item>
            </a-sub-menu>
            <a-menu-item key="7" v-if="role === '3'" @click="handleMenuItemClick('7')">
                <user-outlined/>
                <span>User</span>
            </a-menu-item>
            <a-menu-item key="9" v-if="role === '0' || role === '1'" @click="handleMenuItemClick('9')">
                <ProjectOutlined />
                <span>Team</span>
            </a-menu-item>
        </a-menu>
    </a-layout-sider>
</template>

<script setup>
import {onMounted, onUnmounted, ref} from 'vue'
import {useUserStore} from '@/stores/userStore.js'
import {
    CalendarOutlined,
    BellOutlined,
    MessageOutlined,
    FileOutlined,
    TeamOutlined,
    UserOutlined,
    ProjectOutlined
} from "@ant-design/icons-vue"

const isCollapsed = ref(false)
const collapsedWidth = 80
import router from '@/router/index.js'

const role = ref('0')
const useUser = useUserStore()

if (useUser.getUserRole === 1) {
    role.value = '1'
} else if (useUser.getUserRole === 2) {
    role.value = '2'
} else if (useUser.getUserRole === 3) {
    role.value = '3'
}

const handleMenuItemClick = (key) => {
    switch (key) {
        case '1':
            router.push('/notification')
            break
        case '2':
            router.push('/option2')
            break
        case '3':
            router.push('/appointment')
            break
        case '4':
            router.push('/createClass')
            break
        case '5':
            router.push('/joinClass')
            break
        case '6':
            router.push('/listClass')
            break
        case '7':
            router.push('/admin/user')
            break
        case '8':
            router.push('/student/listClass')
            break
        case '9':
            router.push('/student/team')
            break
    }
}


const handleResize = () => {
    isCollapsed.value = window.innerWidth <= 1024;
}

onMounted(() => {
    window.addEventListener('resize', handleResize)
    handleResize()
})

onUnmounted(() => {
    window.removeEventListener('resize', handleResize)
})

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

.logo {
    height: 80px;
    display: flex;
    align-items: center;
    justify-content: center;
}

.logo img {
    height: 50px;
    display: block;
    margin-right: 8px;
}


@media (max-width: 1024px) {
    .main {
        position: sticky;
        top: 0;
        height: 100vh;
        width: 80px;
    }

    .brand {
        display: none;
    }
}


</style>
