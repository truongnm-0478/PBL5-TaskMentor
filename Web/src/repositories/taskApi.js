import instance from "./axios"

const nameModel = '/TaskMentor/api'

const taskApi = {
    addTask: async (data) => {
        return await instance.post(nameModel + '/task', data)
    },
    deleteTask: async (id) => {
        return await instance.delete(`${nameModel}/task?id=${id}`)
    },
    getListTaskByTeamId: async (id) => {
        return await instance.get(`${nameModel}/task?id=${id}`)
    },
    updateTaskStatus: async (data) => {
        return await instance.put(nameModel + '/task', data)
    },
    getAssignByTaskId: async (id) => {
        return await instance.get(`${nameModel}/assignment?id=${id}`)
    },
    updateAssign: async (data) => {
        return await instance.put(nameModel + '/assignment', data)
    }
}

export default taskApi