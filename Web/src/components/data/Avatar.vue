<template>
    <a-avatar shape="circle" size="large" :style="{ backgroundColor: color, verticalAlign: 'middle' }">
        {{ avatarValue }}
    </a-avatar>
</template>
<script>
import { defineComponent, ref } from 'vue'

const UserList = ['U', 'Lucy', 'Tom', 'Edward']
const colorList = ['#f56a00', '#7265e6', '#ffbf00', '#00a2ae']
export default defineComponent({
    setup() {
        const avatarValue = ref(UserList[0])
        const color = ref(colorList[0])

        return {
            avatarValue,
            color,
        }
    },
})
</script>