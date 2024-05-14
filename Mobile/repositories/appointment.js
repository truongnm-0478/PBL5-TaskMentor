import axios from 'axios';
const baseURL = 'http://localhost:8080'
const getAppointment = async(accessToken)=>{
    try {
        const response = await axios.get(
            `${baseURL}/TaskMentor/api/appointment/guest`, // Đường dẫn API để lấy thông tin người dùng
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
    getAppointment
}