import React, { useState, useEffect } from "react";
import { Text, View, Image, ImageBackground, TouchableOpacity, TextInput, KeyboardAvoidingView, Keyboard, ScrollView, StyleSheet } from "react-native";
import { image, icons, color, FontSize } from "../constants";
import Icon from 'react-native-vector-icons/FontAwesome';
import { isValidEmail, isValidPassword } from "../utilies/Validations"
import { MaterialIcons } from '@expo/vector-icons'; // Import MaterialIcons từ thư viện expo
function Login_1() {
    const [KeyboardDisshown, setKeyboardDisshown] = useState(false)
    const [Email, setEmail] = useState('')
    const [Password, setPassword] = useState('')
    const [errorEmail, seterorrEmail] = useState('')
    const [errorPassword, seterorrPassword] = useState('')
    const [hidePassword, setHidePassword] = useState(true)

    const isVlidateOK = () => {
        if (Email.length > 0 && Password.length > 0 && isValidEmail(Email) == true && isValidPassword(Password) == true) return true
        else return false
    }
    useEffect(() => {
        Keyboard.addListener('keyboardDidShow', () => { setKeyboardDisshown(true) })
        Keyboard.addListener('keyboardDidHide', () => { setKeyboardDisshown(false) })
    })
    return <ScrollView style={{
        backgroundColor: color.BackGround,
    }}>
        <View style={{
            marginTop: 40,
            backgroundColor: color.BackGround,
            marginBottom:40,
        }}>
            <View style={{
                //backgroundColor:'red',
                justifyContent: 'center',
                alignItems: "center"
            }}>
                <Image style={{
                    marginBottom: 40,
                }} source={image.image_login}>

                </Image>
            </View>
            <View>
                <View style={{
                    marginHorizontal: 15,
                }}>
                    <TextInput onChangeText={(Text) => {
                        seterorrEmail(isValidEmail(Text) == true ? "" : "Email not in correct format")
                        setEmail(Text)
                    }} style={{
                        borderWidth: 1,
                        borderColor: color.border, // Màu sắc của đường viền
                        borderRadius: 5, // Độ cong của góc (nếu cần)
                        color: color.placeholder,
                        height: 60,
                        paddingHorizontal: 15
                    }} placeholder="example@email.com" />
                    {/* Chèn biểu tượng mắt */}

                    <Text style={{
                        color: 'red',
                        fontSize: FontSize.h6
                    }}>{errorEmail}</Text>
                </View>
                <View style={{
                    marginHorizontal: 15,
                }}>
                    <TextInput onChangeText={(Text) => {
                        seterorrPassword(isValidPassword(Text) == true ? "" : "Password not in correct format")
                        setPassword(Text)
                    }
                    } style={{
                        borderWidth: 1,
                        borderColor: color.border, // Màu sắc của đường viền
                        borderRadius: 5, // Độ cong của góc (nếu cần)
                        color: color.placeholder,
                        paddingHorizontal: 15,
                        height: 60,
                        color: color.placeholder,
                    }} secureTextEntry={hidePassword} placeholder="Enter your password" />
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
                    }}>{errorPassword}</Text>
                </View>
                <View style={{
                    height:30,
                    marginHorizontal:10
                }}>
                    <Text style={{
                        fontSize:FontSize.h6,
                        color:'black',
                        position: 'absolute',
                         top: 0, right: 10
                    }}> Forgot password? </Text>
                </View>
            </View>
            <View style={{
                marginHorizontal:15,
                marginTop:15,
            }}>
            <TouchableOpacity 
            disabled = {isVlidateOK()==false}
            onPress={() => { alert('Email=' + Email + 'Password=' + Password) }} style={{
                backgroundColor: isVlidateOK()==true?color.BGlogin:color.inactive,
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
                }}>Login</Text>
            </TouchableOpacity> 
            </View>
            <View style={{
                marginVertical:20,
            flexDirection:'row',
            height:40,
            alignItems:"center",
            marginHorizontal:20
          }}>
             <View style={{
                backgroundColor:'black',
                height:1,
                flex:1
             }}></View>
               
                    <Text style={{
                        marginHorizontal:10,
                        padding: 5,
                        fontSize: FontSize.h5,
                        color: 'black'
                    }}>User orther method?</Text>
              
                <View style={{
                backgroundColor:'black',
                height:1,
                flex:1
             }}></View>
            </View> 
            <View style={{}}>
                <View style={{
                    flexDirection: 'row',
                    marginHorizontal: 15,
                    marginTop: 15,
                    justifyContent: "center",
                    alignItems: 'center',
                    backgroundColor: color.BackGround,
                    borderWidth: 1,
                    borderColor: color.google,
                    borderRadius: 10,
                    textAlign: 'center'
                }}>
                    <Image style={{
                        position:'absolute',
                        zIndex:1,
                        left:"5%",
                }} source={image.icon_google}></Image>
                    <TouchableOpacity
                        style={{
                            flex: 1,    
                            justifyContent: "center",
                            alignItems: 'center',
                            alignSelf: "center",
                            textAlign: 'center',
                            height: 60,
                        }}>
                        <Text style={{
                            textAlign: 'center',
                            padding: 10,
                            fontSize: FontSize.h5,
                            fontWeight: 'bold',
                            color: color.google,

                        }}>Login</Text>
                    </TouchableOpacity>
                </View>
            <View style={{
                marginHorizontal:15,
                marginTop:15,
                justifyContent: "center",
                alignItems: 'center',
                
            }}>
                  <Image style={{
                        position:'absolute',
                        zIndex:1,
                        left:"5%",
                }} source={image.icon_facebook}></Image>
            <TouchableOpacity 
            style={{
                backgroundColor:color.BackGround ,
                borderWidth:1,
                borderColor:color.facebook,
                justifyContent: "center",
                alignItems: "center",
                width: '100%',
                borderRadius: 10,
                alignSelf: "center",
                height:60
            }}>
                <Text style={{
                    padding: 10,
                    fontWeight:'bold',
                    fontSize: FontSize.h5,
                    color: color.facebook
                }}>Login</Text>
            </TouchableOpacity> 
            </View>
            </View>
            <View>
            <TouchableOpacity onPress={() => { alert('new account?') }} style={{
                alignSelf: "center",
                padding: 5
            }}>
                <Text style={{

                    padding: 10,
                    fontSize: FontSize.h5,
                    color: 'black'
                }}>Dont have a account yet? Register now</Text>
            </TouchableOpacity>
            </View>
        </View>
    </ScrollView>
}
export default Login_1