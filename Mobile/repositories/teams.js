import AsyncStorage from '@react-native-async-storage/async-storage';
import axios from 'axios'
import { baseURL } from './url';
// const baseURL = 'http://192.168.206.247:8080'
const getMembersByTeam = async(accessToken,id)=>{
    try {
       
        const response = await axios.get(
            `${baseURL}/TaskMentor/api/teamMember?id=${id.id}`, // Đường dẫn API để lấy thông tin người dùng
            {
                headers: {
                    'Content-Type': 'application/json',
                    'Authorization': `Bearer ${accessToken}` // Thêm token vào header
                }
            }
        );
        
        return response.data.data; // Trả về dữ liệu của người dùng từ API
    } catch (error) {
        throw error; // Ném lỗi nếu có lỗi xảy ra
    }
}
const getTeamsByUser = async(accessToken)=>{
    try {
       
        const response = await axios.get(
            `${baseURL}/TaskMentor/api/student/team`, // Đường dẫn API để lấy thông tin người dùng
            {
                headers: {
                    'Content-Type': 'application/json',
                    'Authorization': `Bearer ${accessToken}` // Thêm token vào header
                }
            }
        );
        
        return response.data.data; // Trả về dữ liệu của người dùng từ API
    } catch (error) {
        throw error; // Ném lỗi nếu có lỗi xảy ra
    }
}
const getRequirement = async(id,accessToken)=>{
    try {
       //console.log(id)
        const response = await axios.get(
            `${baseURL}/TaskMentor/api/requirement?id=${id.id}`, // Đường dẫn API để lấy thông tin người dùng
            {
                headers: {
                    'Content-Type': 'application/json',
                    'Authorization': `Bearer ${accessToken}` // Thêm token vào header
                }
            }
        );
        
        return response.data.data; // Trả về dữ liệu của người dùng từ API
    } catch (error) {
        throw error; // Ném lỗi nếu có lỗi xảy ra
    }
}
const getComment = async(accessToken,id)=>{
    try {
       console.log(id)
        const response = await axios.get(
            `${baseURL}/TaskMentor/api/comment?id=${id.id}`, // Đường dẫn API để lấy thông tin người dùng
            {
                headers: {
                    'Content-Type': 'application/json',
                    'Authorization': `Bearer ${accessToken}` // Thêm token vào header
                }
            }
        );
        
        return response.data.data; // Trả về dữ liệu của người dùng từ API
    } catch (error) {
        throw error; // Ném lỗi nếu có lỗi xảy ra
    }
}
const deleteTeams = async(accessToken,id)=>{
    try {
       console.log(id)
        const response = await axios.delete(
            `${baseURL}/TaskMentor/api/team?id=${id.id}`, // Đường dẫn API để lấy thông tin người dùng
            {
                headers: {
                    'Content-Type': 'application/json',
                    'Authorization': `Bearer ${accessToken}` // Thêm token vào header
                }
            }
        );
        
        return response.data.data; // Trả về dữ liệu của người dùng từ API
    } catch (error) {
        throw error; // Ném lỗi nếu có lỗi xảy ra
    }
}
const createTeam = async (group, code, members, accessToken) => {
    try {
        const response = await axios.post(
            `${baseURL}/TaskMentor/api/team`, // Đường dẫn API để lấy thông tin người dùng
            {
                 group,
                 code,
                 members
            },
            {
                headers: {
                    'Content-Type': 'application/json',
                    'Authorization': `Bearer ${accessToken}` // Thêm token vào header
                }
            }
        );
        
        return response.data.data; // Trả về dữ liệu của người dùng từ API
    } catch (error) {
        throw error; // Ném lỗi nếu có lỗi xảy ra
    }
}

export default {
    getMembersByTeam,
    getTeamsByUser,
    createTeam,
    getRequirement,
    getComment,
    deleteTeams
}