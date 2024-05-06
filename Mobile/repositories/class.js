import AsyncStorage from '@react-native-async-storage/async-storage';
import axios from 'axios'
const baseURL = 'http://192.168.206.247:8080'
const getClass = async(accessToken)=>{
    try {
        const response = await axios.get(
            `${baseURL}/TaskMentor/api/joinClass`, // Đường dẫn API để lấy thông tin người dùng
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
const getStudentByClass = async(accessToken,code)=>{
    try {
        const response = await axios.get(
            `${baseURL}/TaskMentor/api/student?code=${code.code}`, // Đường dẫn API để lấy thông tin người dùng
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
const getNotificationByClass = async(accessToken,code)=>{
    try {
        const response = await axios.get(
            `${baseURL}/TaskMentor/api/notification-class?code=${code.code}`, // Đường dẫn API để lấy thông tin người dùng
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
const getNotificationForUser = async(accessToken)=>{
    try {
        const response = await axios.get(
            `${baseURL}/TaskMentor/api/notification-for-user`, // Đường dẫn API để lấy thông tin người dùng
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
const getTeamByClass = async(accessToken,code)=>{
    try {
        const response = await axios.get(
            `${baseURL}/TaskMentor/api/team?code=${code.code}`, // Đường dẫn API để lấy thông tin người dùng
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
const getIntroByClass = async(accessToken,code)=>{
    try {
        const response = await axios.get(
            `${baseURL}/TaskMentor/api/class-introduction?code=${code.code}`, // Đường dẫn API để lấy thông tin người dùng
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
const JoinClass = async(accessToken,studentId, classCode)=>{
    console.log(studentId)
    console.log(classCode)
    try {
        const response = await axios.post(
            `${baseURL}/TaskMentor/api/joinClass`, // Đường dẫn API để lấy thông tin người dùng
            {
                studentId,
                classCode,
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
    getClass,
    getStudentByClass,
    getNotificationByClass,
    getTeamByClass,
    getIntroByClass,
    JoinClass,
    getNotificationForUser,
}