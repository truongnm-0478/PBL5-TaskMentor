import axios from 'axios'
import router from '@/router/index.js'
import qs from 'qs'
import authApi from '@/repositories/authApi.js'

const instance = axios.create({
    baseURL: import.meta.env.VITE_APP_API,
    withCredentials: true
})

instance.interceptors.request.use(
    function (config) {
        const accessToken = localStorage.getItem('accessToken')
        if (accessToken) {
            config.headers['Authorization'] = 'Bearer ' + accessToken;
        }
        return config;
    },
    function (error) {
        return Promise.reject(error)
    }
)

instance.interceptors.response.use(
    async function (response) {
        return response.data;
    },
    async function (error) {
        if (error.response.status === 401 && error.response.data.message === 'Unauthorized access') {
            const refresh_token = localStorage.getItem('refreshToken')
            if (refresh_token) {
                try {
                    const formData = qs.stringify({
                        refresh_token: refresh_token
                    });
                    const response = await authApi.refreshToken(formData, {
                        headers: {
                            'Content-Type': 'application/x-www-form-urlencoded'
                        }
                    });
                    const newAccessToken = response.data.accessToken
                    localStorage.setItem('accessToken', newAccessToken)
                    error.config.headers['Authorization'] = 'Bearer ' + newAccessToken
                    return axios.request(error.config)
                } catch (refreshError) {
                    console.log("Error refreshing token:", refreshError)
                    handleRefreshTokenError()
                    return Promise.reject(error)
                }
            } else {
                handleNoRefreshTokenError()
                return Promise.reject(error)
            }
        } else {
            handleOtherErrors(error)
            return Promise.reject(error)
        }
    }
)

function handleRefreshTokenError() {
    localStorage.removeItem('user')
    localStorage.removeItem('accessToken')
    localStorage.removeItem('refreshToken')
    localStorage.removeItem('isLoggedIn')
    router.push('/login')
}

function handleNoRefreshTokenError() {
    console.log("NO REFRESH TOKEN ERROR")
}

function handleOtherErrors(error) {
    console.log("RESPONSE ERROR: ", error)
}

export default instance
