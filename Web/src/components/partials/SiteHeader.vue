<template>
    <div class="container-header" >
        <a-flex justify="flex-end" align="center">
            <a-dropdown placement="bottomRight">
                <span style="cursor: pointer;">
                    Hello,
                    <strong style="color: var(--color-blue)"> {{ user.username }} <UserOutlined/> </strong>
                </span>
                <template #overlay>
                    <a-menu>
                        <div class="information">
                            <span style="line-height: 30px; font-weight: bold; ">{{ user.name }}</span><br/>
                            <span style="color: var(--color-gray-dark); font-size: 13px">{{ user.email }}</span>
                        </div>
                        <a-menu-item @click="handleChangePassword">
                            <a>Setting password</a>
                        </a-menu-item>
                        <a-menu-item @click="handleUpdateProfile">
                            <a>Update profile</a>
                        </a-menu-item>
                        <a-menu-item @click="handleLogout">
                            <a>Log out</a>
                        </a-menu-item>
                    </a-menu>
                </template>
            </a-dropdown>
        </a-flex>
    </div>
</template>

<script setup>
import router from "@/router/index.js"
import { useSocketStore } from "@/stores/socketStore.js"
import { useUserStore } from "@/stores/userStore.js"
import { UserOutlined } from "@ant-design/icons-vue"
import { ref } from "vue"

const user = ref({
    username: '',
    name: '',
    email: ''
})

const userStore = useUserStore()

const u = userStore.getUser
if(u) {
    user.value.email = u.email;
    user.value.name = u.name;
    user.value.username = u.username
}

// Call api login
const handleLogout = () => {
    userStore.clearUser()
    useSocketStore().disconnectSocket()
    router.push("/login")
}

const handleChangePassword = () => {
    router.push("/change-password")
}

const handleUpdateProfile = () => {
    router.push("/update-profile")
}


</script>

<style>

.container-header {
    height: 100%;
    display: flex;
    justify-content: end;
}

.information {
    padding: 10px;
    border-bottom: 1px solid var(--color-gray-light);
}
</style>