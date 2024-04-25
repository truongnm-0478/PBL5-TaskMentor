<template>
    <div>
        <div v-for="notification in notifications" :key="notification.id">
            {{ notification.message }}
        </div>
        <input v-model="newMessage" placeholder="Enter new message" />
        <button @click="sendMessage">Send Message</button>
    </div>
</template>

<script>
import { useUserStore } from "@/stores/userStore.js";

export default {
    data() {
        return {
            socket: null,
            notifications: [],
            newMessage: '',
        };
    },
    created() {
        this.initWebSocket();
    },
    methods: {
        initWebSocket() {
            // Khởi tạo kết nối WebSocket
            this.socket = new WebSocket('ws://localhost:8080/TaskMentor/notification');
            this.socket.onopen = () => {
                const user = useUserStore().getUser;
                const credentials = {
                    username: user.username,
                    password: user.password
                };
                this.socket.send(JSON.stringify(credentials));
                console.log('Connected to server');
            };

            // Xử lý sự kiện khi nhận được thông báo từ máy chủ
            this.socket.onmessage = (event) => {
                const message = JSON.parse(event.data);
                this.notifications.push(message); // Thêm thông báo vào danh sách
            };

            // Xử lý sự kiện khi kết nối bị đóng
            this.socket.onclose = () => {
                console.log('Connection closed');
            };
        },
        sendMessage() {
            if (this.newMessage.trim() !== '') {
                // Gửi tin nhắn lên máy chủ thông qua kết nối WebSocket
                this.socket.send(this.newMessage);
                this.newMessage = ''; // Xóa nội dung tin nhắn sau khi gửi
            }
        }
    }
};
</script>

<style>
/* CSS cho giao diện nếu cần */
</style>
