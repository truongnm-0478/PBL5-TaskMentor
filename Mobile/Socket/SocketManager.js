// SocketManager.js
import { WebSocket } from "react-native-websocket";

const SOCKET_URL = "ws://192.168.206.247:8080/TaskMentor/notification";

export default class SocketManager {
    constructor(userId) {
        this.socket = new WebSocket(SOCKET_URL);
        this.userId = userId;
        this.connect();
    }

    connect() {
        this.socket.onopen = () => {
            this.socket.send(JSON.stringify({ userId: this.userId }));
        };

        this.socket.onmessage = (e) => {
            const message = JSON.parse(e.data);
            // Xử lý thông báo ở đây, ví dụ: lưu vào state, hiển thị thông báo, vv.
        };

        this.socket.onerror = (e) => {
            console.error("Socket error:", e.message);
        };

        this.socket.onclose = () => {
            console.log("Socket closed");
        };
    }

    disconnect() {
        if (this.socket) {
            this.socket.close();
            this.socket = null;
        }
    }
}
