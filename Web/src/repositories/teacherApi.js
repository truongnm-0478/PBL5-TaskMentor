import instance from "./axios"

const nameModel = '/TaskMentor/api'

const teacherApi = {
    getListTeacher: async () => {
        return await instance.get(`${nameModel}/teacher`)
    },
}

export default teacherApi