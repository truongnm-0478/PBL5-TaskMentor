<template v-if="message"></template>

<script>
import { message } from 'ant-design-vue'
export default {
    props: ['message', 'status'],
    setup(props) {
        const showMessage = () => {
            if (props.status === 'success') {
                message.success(props.message)
            } else if (props.status === 'error') {
                message.error(props.message)
            } else if (props.status === 'warning') {
                message.warning(props.message)
            }
        }
        showMessage()
    },
};
</script>
