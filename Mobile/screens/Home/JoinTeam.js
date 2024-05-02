import React, { useState, useEffect } from "react";
import { Text, View, Image, ImageBackground, TouchableOpacity, TextInput, KeyboardAvoidingView, Keyboard } from "react-native";
import { image, icons, color, FontSize } from "../../constants";
import Icon from 'react-native-vector-icons/FontAwesome';
import { isValidEmail, isValidPassword } from "../../utilies/Validations"
import {UIHeader} from '../../components'
function JoinTeam(props){
    const{navigation,route}=props
    const{navigate,goBack}= navigation
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
                        // setPassword(Text)
                    }
                    } style={{
                        borderWidth: 1,
                        borderColor: color.border, // Màu sắc của đường viền
                        borderRadius: 5, // Độ cong của góc (nếu cần)
                        color: color.placeholder,
                        paddingHorizontal: 15,
                        height: 60,
                        color: color.placeholder,
                    }}  placeholder="Enter your password" />
             
             <TouchableOpacity 
          
            onPress={() => {}} style={{
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