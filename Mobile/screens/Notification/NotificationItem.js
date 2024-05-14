import React, { useState, useEffect } from "react";
import { Text, View, Image, ImageBackground, TouchableOpacity, TextInput, KeyboardAvoidingView, Keyboard } from "react-native";
import { image, icons, color, FontSize , background} from "../../constants";
import { getLastLetter } from "../../utilies";
import Icon from 'react-native-vector-icons/FontAwesome';
import { isValidEmail, isValidPassword } from "../../utilies/Validations"
function NotificationItem(props){
    let {classCode,className,content,teacherName} = props.Notification
    const {onPress} = props
    if (!teacherName) {
      return null; // hoặc có thể hiển thị một placeholder khác thay vì null
    }
    const firstInitials = getLastLetter(teacherName)
    return (<TouchableOpacity onPress={onPress} style={{
        marginBottom:5,
        paddingTop: 20,
        paddingStart: 10,
        flexDirection:'row',
       // alignItems:"center"
      // alignItems:"center",
      
      // marginEnd:10,
    }}>
       <View style={{
         marginEnd:10,
       }}>
       <View style={{
        width: 50,
        height: 50,
         borderRadius: 5, // Đặt borderRadius là 1 nửa của width/height để tạo hình tròn
        backgroundColor: background(teacherName), // Màu nền mặc định hoặc có thể thay đổi theo yêu cầu
        justifyContent: 'center',
        alignItems: 'center',
        marginRight: 15,
        marginStart: 10,
      }}>
        <Text style={{ fontSize: FontSize.h3, color: 'white', fontWeight: '400' }}>{firstInitials}</Text>
      </View>
       </View>
      <View style={{
        flexDirection:'column',
         marginEnd:10,
      }}>
      <View style={{
        //flexDirection:'row',
        marginRight:100,
       // backgroundColor:'red',
      }}>
      <Text style={{
      }}><Text style={{
        color:'black',
        fontSize:FontSize.h5,
        fontWeight:'bold'
      }}>{teacherName} </Text>
      <Text style={{
        color:"#1a5db6",
        fontSize:FontSize.h5,
        //fontWeight:'bold'
        marginRight:10,
        //backgroundColor:'red',
        flex:1,
      }}> from {className}  </Text></Text>
      {/* {className.length > 15 ? `${className.substring(0, 15)}...` : className} */}
      </View>
      <View style={{
        marginRight:60
      }}>
      <Text style={{
        paddingEnd:10,
        color:color.inactive,
        fontSize:FontSize.h5,
        marginRight:10,
      }}>{content}</Text>
      </View>
    
      </View>

    </TouchableOpacity>)
} export default NotificationItem