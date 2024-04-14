<template>
    <div>
        <component :is="layout">
            <router-view />
        </component>
    </div>
</template>
    
<script>
import { markRaw, ref, watch } from 'vue'
import { useRoute } from 'vue-router'


export default {
    setup() {
        const defaultLayout = 'DefaultLayout'
        const route = useRoute()
        const layout = ref()

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
                    layout.value = markRaw(component)
                }
            },
            { immediate: true }
        )

        return {
            layout,
        }
    }
}
</script>
