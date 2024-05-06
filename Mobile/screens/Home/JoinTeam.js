import React, { useState, useEffect } from "react";
import { Text, View, Image, ImageBackground, TouchableOpacity, TextInput, KeyboardAvoidingView, Keyboard } from "react-native";
import { image, icons, color, FontSize } from "../../constants";
import Icon from 'react-native-vector-icons/FontAwesome';
import { isValidEmail, isValidPassword } from "../../utilies/Validations"
import {UIHeader} from '../../components'
import { _class } from "../../repositories";
import AsyncStorage from "@react-native-async-storage/async-storage";
function JoinTeam(props){
    const{navigation,route}=props
    const{navigate,goBack}= navigation
    const [studentId, setStudentId] = useState('')
    const[classCode, setClassCode] = useState('')
    const handleJoinclass = async () => {
        try {
           const accessToken = await AsyncStorage.getItem('accessToken');
           const response = await _class.JoinClass(accessToken, studentId , classCode)

            navigate('UITab')

        } catch (error) {
           console.log(error)
        } 
    }


    return <View style={{
        marginTop:40
    }}>
         <UIHeader title={'Join team with code'}
         leftIconName={'arrow-left'}
        
      
         onPressLeftIcon= {()=>{
            goBack()
         }}
        
        ></UIHeader>
        <View>
        <View style={{
                    marginHorizontal: 15,
                }}>
                    <TextInput onChangeText={(Text) => {
                        // seterorrPassword(isValidPassword(Text) == true ? "" : "Password not in correct format")
                        setStudentId(Text)
                    }
                    } style={{
                        marginTop:40,
                        borderWidth: 1,
                        borderColor: color.border, // Màu sắc của đường viền
                        borderRadius: 5, // Độ cong của góc (nếu cần)
                        color: color.placeholder,
                        paddingHorizontal: 15,
                        height: 60,
                        color: color.placeholder,
                    }}  placeholder="Student Code" />
                     <TextInput onChangeText={(Text) => {
                        // seterorrPassword(isValidPassword(Text) == true ? "" : "Password not in correct format")
                        // setPassword(Text)
                        setClassCode(Text)
                    }
                    } style={{
                        marginTop:20,
                        borderWidth: 1,
                        borderColor: color.border, // Màu sắc của đường viền
                        borderRadius: 5, // Độ cong của góc (nếu cần)
                        color: color.placeholder,
                        paddingHorizontal: 15,
                        height: 60,
                        color: color.placeholder,
                    }}  placeholder="Class Code" />
             
             <TouchableOpacity 
          
            onPress={() => {handleJoinclass()}} style={{
                marginTop:20,
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
                }}>Join</Text>
            </TouchableOpacity> 
                </View>
        </View>
    </View>
} export default JoinTeam