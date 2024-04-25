import instance from "./axios"

const nameModel = '/TaskMentor/api'

const userApi = {
    createNotificationToClass: async (data) => {
        return await instance.post(nameModel + '/notification-class', data)
    },
    getNotificationsClass: async(code) => {
        return await instance.get(`${nameModel}/notification-class?code=${code}`)
    }
}

export default userApi