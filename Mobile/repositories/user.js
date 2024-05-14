import AsyncStorage from '@react-native-async-storage/async-storage';
import axios from 'axios'
const baseURL = 'http://192.168.206.247:8080'
const getUserDetail=async()=>{
    try {
    let respone= await axios.get('https://randomuser.me/api')
    if(respone.status != 200){
        throw "failed request"
    }
    if(respone.data.results.length>0){
        let responeUser= respone.data.results[0]
       let user={}
       // user.dateOfBirth = new Date(responeUser.dob.date)
        user.email= responeUser.email
        user.gender= responeUser.gender ?? 'male'
        user.userid = responeUser.id.name + responeUser.id.value
        user.address = responeUser.location.state + ',' + responeUser.location.street.name
        user.username= responeUser.login.username
        user.url = responeUser.picture.large
        user.phone = responeUser.phone??''
        user.registeredDate= (new Date(responeUser.registered.date)).toDateString()
        return user
    }
    throw "user not found"
    debugger
    } catch (error) {
      debugger
        throw error
    }
}
const login = async ({ username, password }) => {
    console.log(username)
    console.log(password)
    try {
       
        const response = await axios.post(
        //    'http://192.168.206.247:8080/TaskMentor/api/login',
         `${baseURL}/TaskMentor/api/login`,
            {
                username,
                password,
            },
            {
                headers: { 'Content-Type': 'application/json' },
            }
        )
        const { accessToken, refreshToken } = response.data.data;

        // Hiển thị access token và refresh token trên console
        console.log('Access Token:', accessToken)
        console.log('Refresh Token:', refreshToken)
        await AsyncStorage.setItem('accessToken', accessToken);
        await AsyncStorage.setItem('refreshToken', refreshToken);
        
        return response.data
    } catch (error) {
        throw error
    }
}

const register = async (newAccount) => {
    try {
        console.log(newAccount)
        let response = await axios.post(
        //    'http://192.168.206.247:8080/TaskMentor/api/register',
        `${baseURL}/TaskMentor/api/register`,
            newAccount,
            {
                headers: { 'Content-Type': 'application/json' },
            }
        )
        return response.data
    } catch (error) {
        throw error
    }
}
const getUserInfo = async (accessToken) => {
    try {
        const response = await axios.get(
            `${baseURL}/TaskMentor/api/user-information`, // Đường dẫn API để lấy thông tin người dùng
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
};

const logout = async (accessToken) => {
    try {
        // Lấy access token từ AsyncStorage

        // Gửi yêu cầu logout với access token
        const response = await axios.get(
            `${baseURL}/TaskMentor/api/logout`,
            {
                headers: {
                    'Authorization': `Bearer ${accessToken}`,
                    'Content-Type': 'application/json'
                }
            }
        );

        // Xóa access token từ AsyncStorage sau khi logout thành công
        await AsyncStorage.removeItem('accessToken');

        // Trả về kết quả từ phản hồi của API logout (nếu cần)
        return response.data;
    } catch (error) {
        // Xử lý lỗi (nếu cần)
        throw error;
    }
}


export default{
    getUserDetail,
    login,
    register,
    logout,
    getUserInfo,
}