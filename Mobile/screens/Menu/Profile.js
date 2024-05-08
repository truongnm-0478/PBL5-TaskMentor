import React, { useState, useEffect } from "react";
import { Text, View, Image, ImageBackground, TouchableOpacity, TextInput, KeyboardAvoidingView, Keyboard, SafeAreaView,Switch } from "react-native";
import { image, icons, color, FontSize } from "../../constants";
import Icon from 'react-native-vector-icons/FontAwesome';
import { isValidEmail, isValidPassword } from "../../utilies/Validations"
import {user} from '../../repositories'
import {
    user as UserReponsitory,
    population as PopulationReponsitory
} from '../../repositories'
import { UIHeader } from "../../components";
import FontAwesomeIcon from 'react-native-vector-icons/FontAwesome'
import AsyncStorage from '@react-native-async-storage/async-storage'

const SettingItem = ({ imageSource, title, onPress }) => (
    
    <TouchableOpacity onPress={onPress}>
        <View style={{
        flexDirection: 'row',
        alignItems: 'center',
        height: 60,
    }}>
        <Image source={imageSource} style={{
            width: 30,
            height: 30,
            resizeMode: 'cover',
            //borderRadius: 50,
            
            backgroundColor: 'transparent',
            marginRight: 15,
            marginStart: 20,
            alignSelf: 'center',
            tintColor:'#4880ff',
        }} />
        <Text style={{
            fontSize: FontSize.h6,
            paddingStart: 10
        }}>{title}</Text>
        <View style={{ flex: 1 }}></View>
        <Icon name="chevron-right" color='black' size={17} style={{ paddingEnd: 10 }}  />
    </View>
    </TouchableOpacity>
);

function Profile(props) {
    const [userInfo, setUserInfo] = useState({});
    const [loading, setLoading] = useState(true);
    useEffect(() => {
        // Gọi hàm getUserInfo khi component được mount
        const fetchUserInfo = async () => {
            try {
                // Lấy accessToken từ AsyncStorage hoặc từ đâu đó
                const accessToken = await AsyncStorage.getItem('accessToken');
                // Gọi hàm getUserInfo để lấy thông tin người dùng với accessToken
                console.log("UserInfo:",accessToken );
                const userData = await user.getUserInfo(accessToken);
                // Cập nhật state với thông tin người dùng
                console.log("UserInfo:", userData);
                setUserInfo(userData);
                setLoading(false)
            } catch (error) {
                console.log(error);
                // Xử lý lỗi nếu có
            }
        };

        fetchUserInfo(); // Gọi hàm fetchUserInfo
    }, []); 
    const{navigation,route}=props
    const{navigate,goBack}= navigation
    const[isEnabled, setisEnabled]= useState(true)
    const [user1, setUser1] = useState({})
    const{ email ,userid,address,gender,username,url,phone,registeredDate } = user1
    useEffect(() => {
                 UserReponsitory.getUserDetail()
                 .then(responseUser => setUser1(responseUser))
             }, []) 
    const settings = [
        //{ imageSource: image.moon, title: 'Dark Mode' },
        { imageSource: image.image_updateprofile, title: 'Update Profile' },
        { imageSource: image.image_changePass, title: 'Change Password' },
        { imageSource: image.image_About, title: 'About' },
        { imageSource: image.image_signOut, title: 'Sign out' }
    ];
    const handleLogout = async () => {
        try {
            // Gọi hàm logout từ tệp logout.js
            // await logout();
            const accessToken = await AsyncStorage.getItem('accessToken');
            console.log(accessToken)
            if (!accessToken) {
                throw "No access token found";
            }
            const response = await user.logout(accessToken)
            // Nếu logout thành công, hiển thị thông báo thành công
            navigate('Login') 
        } catch (error) {
            // Nếu logout không thành công, hiển thị thông báo lỗi
            // Alert.alert('Logout', 'Logout failed');
            console.log(error)
        }
    };
    return( <View style={{
        backgroundColor: color.BackGround,
        flex:1,
    }}>
         <View style={{
        flex: 1,
        marginTop: 40,
    }}>
        <UIHeader title={'Settings'}
            // leftIconName={'arrow-left'}
            rightIconName={'search'}
            // onPressLeftIcon={() => {
            //     alert('back')
            // }}
            onPressRightIcon={() => {
                alert('next')
            }}
        ></UIHeader>
        <View style={{
            flexDirection: 'row',
            marginEnd: 20,
            paddingRight: 20,
        }}>
            <Image style={{
                marginTop: 20,
                width: 100,
                height: 100,
                resizeMode: 'cover',
                borderRadius: 50,
                marginRight: 15,
                marginStart: 20,
                
            }} source={{
                uri: url
            }}></Image>
            <View style={{
    flexDirection: 'column',
    // backgroundColor:'red',
    justifyContent: "center",
    //alignItems:"center",
    marginEnd: 60,
    paddingRight: 20
}}>
    <View>
        <Text style={{
            fontWeight: 'bold',
            fontSize: FontSize.h3
        }}>{userInfo.name}</Text>
    </View>
    <View>
        <Text style={{
            paddingRight: 20
        }}>
            {userInfo.email}
        </Text>
    </View>
</View>
        </View>
        <View style={{
            marginTop: 20,
            height: 2,
            backgroundColor: "#EBEBEB",

        }}>
        </View>
        <View style={{
        flexDirection: 'row',
        alignItems: 'center',
        height: 60,
    }}>
        <Image source= {image.moon} style={{
            width: 30,
            height: 30,
            resizeMode: 'cover',
            //borderRadius: 50,
            backgroundColor: 'transparent',
            marginRight: 15,
            marginStart: 20,
            alignSelf: 'center',
            tintColor:'#81b0ff',
        }} />
        <Text style={{
            fontSize: FontSize.h6,
            paddingStart: 10
        }}>Dark Mode</Text>
        <View style={{ flex: 1 }}></View>
        <Switch
        trackColor={{false: color.inactive, true: '#81b0ff'}}
        thumbColor={isEnabled ? 'white': color.inactive}
        ios_backgroundColor="#3e3e3e"
        onValueChange={()=>{
            setisEnabled(!isEnabled)
        }}
        value={isEnabled}
      />
        {/* <Icon name="chevron-right" color='black' size={17} style={{ paddingEnd: 10 }}  /> */}
    </View>
        <View style={{ flex: 1,}}>
            {settings.map((setting, index) => (
                <SettingItem
                    key={index}
                    imageSource={setting.imageSource}
                    title={setting.title}
                    onPress={() => {
                        if(setting.title === 'Update Profile'){
                               navigate('UpdateProfile')
                        } else if(setting.title === 'Change Password'){
                            navigate('ChangePassword')
                           
                        } else if (setting.title === 'About'){
                                navigate('About')
                        } else if(setting.title === 'Sign out'){
                           {handleLogout()}
                        }
                    }} // Đổi hành động tương ứng
                />
            ))}
        </View>
    </View>
    </View> )
} export default Profile
