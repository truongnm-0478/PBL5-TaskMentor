import instance from "./axios"

const nameModel = '/TaskMentor/api'

const userApi = {
    getListUser: async () => {
        return await instance.get(nameModel + '/user-information/all')
    },
    getListUserAdmin: async () => {
        return await instance.get(nameModel + '/user')
    },
    disableUser: async (id) => {
        return await instance.delete(`${nameModel}/user?id=${id}`)
    },
    getUserInformation: async (id) => {
        return await instance.get(`${nameModel}/user?id=${id}`)
    },
    updateUser: async (data) => {
        return await instance.put(nameModel + '/user', data)
    }

}

export default userApi