import instance from "./axios"

const nameModel = '/TaskMentor/api'

const userApi = {
    getListUser: async () => {
        return await instance.get(nameModel + '/user-information/all')
    }
}

export default userApi