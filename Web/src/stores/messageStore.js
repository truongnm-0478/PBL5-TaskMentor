import { defineStore } from 'pinia'

export const useMessageStore = defineStore('message', {
    state: () => ({
        messages: [],
    }),
    actions: {
        addMessage(status, message) {
            const messageToast = {
                id: Math.floor(Math.random() * 10000),
                message,
                status,
            }
            this.messages = [messageToast, ...this.messages]
        },
        clearMessages() {
            this.messages = []
        },
    },
    getters: {
        getMessageCount() {
            return this.messages.length
        },
    },
});

