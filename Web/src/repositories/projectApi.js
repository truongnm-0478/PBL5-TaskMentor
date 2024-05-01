import instance from "./axios"

const nameModel = '/TaskMentor/api'

const projectApi = {
    addRequirement: async (data) => {
        return await instance.post(nameModel + '/requirement', data)
    },
    getListRequirement: async (id) => {
        return await instance.get(`${nameModel}/requirement?id=${id}`)
    },
    deleteRequirement: async (id) => {
        return await instance.delete(`${nameModel}/requirement?id=${id}`)
    },
    addComment: async (data) => {
        return await instance.post(nameModel + '/comment', data)
    },
    getListComment: async (id) => {
        return await instance.get(`${nameModel}/comment?id=${id}`)
    },
    deleteComment: async (id) => {
        return await instance.delete(`${nameModel}/comment?id=${id}`)
    },
    destroyApproval: async (id) => {
        return await instance.put(`${nameModel}/comment?id=${id}`)
    },
    createPlan: async (data) => {
        return await instance.post(nameModel + '/plan', data)
    },
    getPlan: async (id) => {
        return await instance.get(`${nameModel}/plan?id=${id}`)
    },
    updatePlan: async (data) => {
        return await instance.put(nameModel + '/plan', data)
    },
    deletePlan: async (id) => {
        return await instance.delete(`${nameModel}/plan?id=${id}`)
    }
}

export default projectApi