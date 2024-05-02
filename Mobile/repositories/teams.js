import AsyncStorage from '@react-native-async-storage/async-storage';
import axios from 'axios'
const baseURL = 'http://192.168.206.247:8080'
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
export default {
    getMembersByTeam,
}