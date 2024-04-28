import instance from "./axios"

const nameModel = '/TaskMentor/api'

const teamApi = {
    createTeam: async (data) => {
        return await instance.post(nameModel + '/team', data)
    },
    getListTeam: async (code) => {
        return await instance.get(`${nameModel}/team?code=${code}`)
    },
    getListMember: async (id) => {
        return await instance.get(`${nameModel}/teamMember?id=${id}`)
    },
    removeTeamToClass: async (id) => {
        return await instance.delete(`${nameModel}/team?id=${id}`)
    },
    getTeamByUser: async () => {
        return await instance.get( nameModel + '/student/team')
    }
}

export default teamApi