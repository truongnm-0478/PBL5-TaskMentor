import {defineStore} from 'pinia';

export const useSocketStore = defineStore('socket', {
    state: () => ({
        socket: null,
        isConnected: false,
        newMessage: null,
        notifications: [],
    }),
    actions: {
        connectSocket(userId) {

            const socket = new WebSocket('ws://localhost:8080/TaskMentor/notification')

            socket.onopen = () => {
                socket.send(String(userId))
                console.log("Connected to socket")
                this.isConnected = true
            }

            socket.onmessage = (event) => {
                this.newMessage = JSON.parse(event.data)
            };

            socket.onerror = () => {
                console.error('WebSocket connection error')
                this.disconnectSocket()
            }

            socket.onclose = () => {
                console.log('WebSocket connection closed')
                this.isConnected = false
            }

            this.socket = socket
        },
        disconnectSocket() {
            if (this.socket) {
                this.socket.close()
                this.socket = null
            }
            this.isConnected = false
        },
    },
})
