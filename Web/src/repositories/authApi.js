import instance from "./axios"

const nameModel = '/TaskMentor/api'

const authApi = {
    login: async (data) => {
        return await instance.post(nameModel + '/login', data)
    },
    register: async (data) => {
        return await  instance.post(nameModel + '/register', data)
    },
    getCurrentUser: async () => {
        return await instance.get(nameModel + '/user-information')
    },
    refreshToken: async (token) => {
        return await instance.post('/TaskMentor/refresh', token)
    }
}

export default authApi