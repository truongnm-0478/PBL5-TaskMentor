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


        watchEffect(() => {
            spinning.value = storeSpin.loading
        })

        watch (
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
                    layout.value = markRaw(component)
                }
            },
            {immediate: true}
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
