<template>
    <div>
        <ToastMessage
            v-for="message in storeMessage.messages"
            :key="message.id"
            :message="message.message"
            :status="message.status"
        />
        <template v-if="spinning">
            <a-spin :spinning="spinning" size="large" :delay="spinDelay">
                <component :is="layout">
                    <router-view />
                </component>
            </a-spin>
        </template>
        <template v-else>
            <component :is="layout">
                <router-view />
            </component>
        </template>
    </div>
</template>

<script>
import { markRaw, ref, watchEffect, watch } from 'vue'
import { useRoute } from 'vue-router'
import ToastMessage from '@/components/feedback/ToastMessage.vue'
import { useMessageStore } from '@/stores/messageStore.js'
import { useSpinStore } from '@/stores/spinStore.js'
import { useSocketStore } from '@/stores/socketStore.js'
import { useUserStore } from '@/stores/userStore.js'
import { notification } from 'ant-design-vue'
import router from "@/router/index.js";
const [api, contextHolder] = notification.useNotification()


export default {
    components: { ToastMessage },
    setup() {
        const defaultLayout = 'DefaultLayout'
        const route = useRoute()
        const layout = ref()
        const storeMessage = useMessageStore()
        const storeSpin = useSpinStore()
        const spinning = ref(storeSpin.loading)
        const spinDelay = 1000
        const socketStore = useSocketStore()
        const userStore = useUserStore()

        const connectSocket = (userId) => {
            socketStore.connectSocket(userId)
        }

        watchEffect(() => {
            const userId = userStore.getUser?.id || 0
            connectSocket(userId);
        })

        watchEffect(() => {
            if (socketStore.newMessage) {
                const placement = 'topRight'
                openNotification(placement, socketStore.newMessage)
                socketStore.newMessage = null;
            }
        })

        const openNotification = (placement, message) => {
            notification.success({
                message: 'Notification',
                description: message.content,
                placement: placement,
                onClick: () => {
                    console.log('Notification clicked: ', message.classCode);
                    router.push('/notification')
                }
            })
        }


        watchEffect(() => {
            spinning.value = storeSpin.loading
        })

        watch(
            () => route.meta?.layout,
            async (metaLayout) => {
                try {
                    let component = await import(`./layouts/${defaultLayout}.vue`)

                    if (metaLayout) {
                        component = await import(`./layouts/${metaLayout}.vue`)
                    }
                    layout.value = markRaw(component?.default)
                } catch (error) {
                    layout.value = markRaw(defaultLayout)
                }
            },
            { immediate: true }
        )

        return {
            layout,
            storeMessage,
            storeSpin,
            spinning,
            spinDelay
        }
    }
}
</script>
