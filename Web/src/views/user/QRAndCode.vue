<template>
    <div class="container">
        <a-space direction="vertical" align="center">
            <a-qrcode
                :value="generatedCode"
                color="#4880FF"
                bg-color="#FFFFFF"
                error-level="H"
                size="300"
            />
            <a-input-group compact class="input-group">
                <a-input readonly v-model:value="generatedCode" class="input" />
                <a-tooltip title="Copy git URL">
                    <template #default>
                        <a-button type="default" @click="copyToClipboard" class="copy-button">
                            <template #icon><CopyOutlined /></template>
                        </a-button>
                    </template>
                </a-tooltip>
            </a-input-group>
        </a-space>
    </div>
</template>

<script setup>
import { ref } from 'vue'
import { message } from 'ant-design-vue'
import { useRoute } from 'vue-router'
import { CopyOutlined } from '@ant-design/icons-vue'

const route = useRoute()
const generatedCode = ref(route.query.code)

const copyToClipboard = () => {
    const inputElement = document.querySelector('input[type="text"]')
    if (inputElement) {
        inputElement.select()
        document.execCommand('copy')
        message.success('Copied to clipboard')
    }
}
</script>

<style scoped>
.container {
    padding: 50px 20px;
    background-color: var(--color-white);
    border-radius: 10px;
    height: calc(100vh - 106px);
    overflow: auto;
    display: flex;
    align-items: center;
    justify-content: center;
}

.input-group {
    width: 300px;
}

.input {
    width: calc(100% - 36px);
    color: var(--color-blue);
    background-color: var(--color-white);
}

.copy-button {
    width: 36px;
}
</style>
