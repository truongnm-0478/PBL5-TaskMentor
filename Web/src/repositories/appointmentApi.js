import instance from "./axios"

const nameModel = '/TaskMentor/api'

const appointmentApi = {
    createAppointment: async (data) => {
        return await instance.post(nameModel + '/appointment', data)
    },
    getListAppointment: async ()=> {
        return await instance.get(nameModel + '/appointment')
    },
    updateDateStart: async (data) => {
        return await instance.put(nameModel + '/appointment', data)
    },
    deleteAppointment: async (id) => {
        return await instance.delete(`${nameModel}/appointment?id=${id}`)
    },
    getListAppointmentOfGuest: async ()=> {
        return await instance.get(nameModel + '/appointment/guest')
    },
}

export default appointmentApi