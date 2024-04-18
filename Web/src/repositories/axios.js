import axios from 'axios';
import router from "@/router/index.js";

const instance = axios.create({
    baseURL: import.meta.env.VITE_APP_API,
    withCredentials: true
});

instance.interceptors.request.use(
    function (config) {
        const accessToken = localStorage.getItem('accessToken');
        if (accessToken) {
            config.headers['Authorization'] = 'Bearer ' + accessToken;
        }
        return config;
    },
    function (error) {
        return Promise.reject(error);
    }
);

instance.interceptors.response.use(
    function (response) {
        return response.data;
    },
    function (error) {
        console.log("RESPONSE ERROR: ", error);
        return Promise.reject(error);
    }
);

export default instance;
