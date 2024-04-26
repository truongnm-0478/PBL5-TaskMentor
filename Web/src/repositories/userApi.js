import instance from "./axios"

const nameModel = '/TaskMentor/api'

const userApi = {
    getListUser: async () => {
        return await instance.get(nameModel + '/user-information/all')
    },
    getListUserAdmin: async () => {
        return await instance.get(nameModel + '/user')
    }
}

export default userApi