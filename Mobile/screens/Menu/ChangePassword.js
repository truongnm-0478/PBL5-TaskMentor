import React, { useState, useEffect } from "react";
import { Text, View, Image, ImageBackground, TouchableOpacity, TextInput, KeyboardAvoidingView, Keyboard, FlatList,ScrollView, Alert } from "react-native";
import { image, icons, color, FontSize } from "../../constants";
import Icon from 'react-native-vector-icons/FontAwesome';
import { isValidEmail, isValidPassword } from "../../utilies/Validations"
import { UIHeader } from '../../components'
import { MaterialIcons } from '@expo/vector-icons'; 
import { user } from "../../repositories";
import AsyncStorage from '@react-native-async-storage/async-storage'
function ChangePassword(props){
    const [hidePassword, setHidePassword] = useState(true)
    const { navigation, route } = props
    const { navigate, goBack } = navigation
    const [pass, setPass]= useState('')
    const [checkPass, setCheckPass]= useState('')
    const [current, serCurrent] = useState('')
    // const changpass = async () => {
    //     try {
    //         const accessToken = await AsyncStorage.getItem('accessToken');
    //        const response = await user.change_pass(pass, checkPass, current, accessToken )
    //        navigate('UITab')
    //     } catch (error) {
    //       console.log(error)
    //     } 
    // }
    const changePasswordConfirm = async () => {
        Alert.alert(
            "Confirm Change Password",
            "Are you sure you want to change your password?",
            [
                {
                    text: "Cancel",
                    style: "cancel"
                },
                {
                    text: "Confirm",
                    onPress: async () => {
                        try {
                            const accessToken = await AsyncStorage.getItem('accessToken');
                            const response = await user.change_pass(pass, checkPass, current, accessToken);
                            navigate('UITab');
                        } catch (error) {
                            console.log(error);
                        }
                    }
                }
            ]
        );
    };
    return(<View style={{
        flex:1,
        backgroundColor:color.BackGround,
    }}>
         <View style={{
        marginTop:40,
    }}>
       <UIHeader title='Change Password'
            
            leftIconName={image.back}
          //  rightIconName={'ellipsis-v'}
            onPressLeftIcon={() => {
                goBack()
            }}
            onPressRightIcon={() => {
                alert('next')
            }}
          
        ></UIHeader>
        <View style={{
           
        }}>
               <View style={{
                    marginHorizontal: 15,
                }}>
                    <TextInput 
                    onChangeText={(Text) => {
                       serCurrent(Text)
                    }}
                     style={{
                        borderWidth: 1,
                        borderColor: color.border, // Màu sắc của đường viền
                        borderRadius: 5, // Độ cong của góc (nếu cần)
                        color: color.placeholder,
                        paddingHorizontal: 15,
                        height: 60,
                        color: color.placeholder,
                    }} secureTextEntry={hidePassword} placeholder="Current password" />
                    <TouchableOpacity onPress={() => setHidePassword(!hidePassword)} style={{ position: 'absolute', top: 20, right: 20 }}>
                        <MaterialIcons
                            name={hidePassword ? 'visibility-off' : 'visibility'} // Hiện hoặc ẩn biểu tượng mắt
                            size={24}
                            color="black"
                        />
                    </TouchableOpacity>
                    <Text style={{
                        color: 'red',
                        fontSize: FontSize.h6
                    }}></Text>
                </View>
                <View style={{
                    marginHorizontal: 15,
                }}>
                    <TextInput 
                     onChangeText={(Text) => {
                        setPass(Text)
                     }}
                     style={{
                        borderWidth: 1,
                        borderColor: color.border, // Màu sắc của đường viền
                        borderRadius: 5, // Độ cong của góc (nếu cần)
                        color: color.placeholder,
                        paddingHorizontal: 15,
                        height: 60,
                        color: color.placeholder,
                    }} secureTextEntry={hidePassword} placeholder="New password" />
                    <TouchableOpacity onPress={() => {}} style={{ position: 'absolute', top: 20, right: 20 }}>
                        <MaterialIcons
                            name={hidePassword ? 'visibility-off' : 'visibility'} // Hiện hoặc ẩn biểu tượng mắt
                            size={24}
                            color="black"
                        />
                    </TouchableOpacity>
                    <Text style={{
                        color: 'red',
                        fontSize: FontSize.h6
                    }}></Text>
                </View>
                <View style={{
                    marginHorizontal: 15,
                }}>
                    <TextInput 
                     onChangeText={(Text) => {
                        setCheckPass(Text)
                     }}
                     style={{
                        borderWidth: 1,
                        borderColor: color.border, // Màu sắc của đường viền
                        borderRadius: 5, // Độ cong của góc (nếu cần)
                        color: color.placeholder,
                        paddingHorizontal: 15,
                        height: 60,
                        color: color.placeholder,
                    }} secureTextEntry={hidePassword} placeholder="Confirm new password" />
                    <TouchableOpacity onPress={() => {}} style={{ position: 'absolute', top: 20, right: 20 }}>
                        <MaterialIcons
                            name={hidePassword ? 'visibility-off' : 'visibility'} // Hiện hoặc ẩn biểu tượng mắt
                            size={24}
                            color="black"
                        />
                    </TouchableOpacity>
                    <Text style={{
                        color: 'red',
                        fontSize: FontSize.h6
                    }}></Text>
                </View>
                <View style={{
                marginHorizontal:15,
                marginTop:15,
            }}>
            <TouchableOpacity 
            onPress={()=>{changePasswordConfirm()}}
          style={{
                backgroundColor: color.BGlogin,
                justifyContent: "center",
                alignItems: "center",
                width: '100%',
                borderRadius: 10,
                alignSelf: "center",
                height:60
            }}>
                <Text style={{
                    padding: 10,
                    fontSize: FontSize.h5,
                    color: 'white'
                }}>Change Password</Text>
            </TouchableOpacity> 
            </View>
            </View>
           
    </View>
    </View>
    );
}export default ChangePassword