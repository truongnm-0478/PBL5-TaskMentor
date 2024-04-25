import instance from "./axios"

const nameModel = '/TaskMentor/api'

const classApi = {
    createClass: async (data) => {
        return await instance.post(nameModel + '/class', data)
    },
    getListClass: async () => {
        return await instance.get(nameModel + '/class');
    },
    updateClassInfo: async (data) => {
        return await instance.put(nameModel + '/class', data)
    },
    deleteClass: async (code) => {
        return await instance.delete(`${nameModel}/class?code=${code}`)
    },
    joinClass: async (data) => {
        return await instance.post( nameModel + '/joinClass', data)
    },
    getListJoinedClass: async () => {
        return await instance.get( nameModel + '/joinClass')
    },
    getListStudentInClass: async (code) => {
        return await instance.get(`${nameModel}/student?code=${code}`)
    },
    removeStudentToClass: async (id) => {
        return await instance.delete(`${nameModel}/student?id=${id}`)
    }
}

export default classApi