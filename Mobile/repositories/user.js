import AsyncStorage from '@react-native-async-storage/async-storage';
import axios from 'axios'
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
           'http://192.168.1.16:8080/TaskMentor/api/login',
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
           'http://192.168.1.16:8080/TaskMentor/api/register',
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
export default{
    getUserDetail,
    login,
    register
}