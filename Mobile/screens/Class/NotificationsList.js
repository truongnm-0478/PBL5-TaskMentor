import React, { useState, useEffect } from "react";
import { Text, View, Image, ImageBackground, TouchableOpacity, TextInput, KeyboardAvoidingView, Keyboard } from "react-native";
import { image, icons, color, FontSize,background } from "../../constants";
import {getLastLetter} from '../../utilies/index'
import Icon from 'react-native-vector-icons/FontAwesome';
import { isValidEmail, isValidPassword } from "../../utilies/Validations"
function NotificationsList(props) {
  let { name, content } = props.Notification
  const { onPress } = props
  if (!name) {
    return null; // hoặc có thể hiển thị một placeholder khác thay vì null
  }
  function getInitials(name) {
    if (!name) return ''; // Trả về chuỗi rỗng nếu không có tên
  
    const words = name.split(' '); // Tách tên thành các từ
  
    // Lấy chữ cái đầu tiên của mỗi từ và ghép lại
    const initials = words.map(word => word.charAt(0).toUpperCase()).join('');
  
    const lastTwoInitials = initials.slice(-2);
  
    return lastTwoInitials;
  }
  
  // Sử dụng hàm getInitials để lấy hai chữ cái đầu của tên
  const firstInitials = getLastLetter(name)
  return (<TouchableOpacity onPress={onPress} style={{
    marginBottom: 5,
    paddingTop: 20,
    paddingStart: 10,
    flexDirection: 'row',
    // alignItems:"center"
    // alignItems:"center",
    marginEnd: 10,
  }}>
    <View>
      {/* <Image style={{
        width: 50,
        height: 50,
        resizeMode: 'cover',
        borderRadius: 10,
        marginRight: 15,
        marginStart: 10
      }} source={{
        uri: 'https://cdn-icons-png.freepik.com/256/1827/1827392.png?semt=ais_hybrid'
      }}>
      </Image> */}
      <View style={{
        width: 50,
        height: 50,
         borderRadius: 5, // Đặt borderRadius là 1 nửa của width/height để tạo hình tròn
        backgroundColor: background(name), // Màu nền mặc định hoặc có thể thay đổi theo yêu cầu
        justifyContent: 'center',
        alignItems: 'center',
        marginRight: 15,
        marginStart: 10,
      }}>
        <Text style={{ fontSize: FontSize.h3, color: 'white', fontWeight: '400' }}>{firstInitials}</Text>
      </View>
    </View>
    <View style={{
      flexDirection: 'column',
    }}>
      <Text style={{
        color: 'black',
        fontSize: FontSize.h5,
        fontWeight: 500,
      }}>{name}</Text>
      <View style={{
        marginRight: 60
      }}>
        <Text style={{

          paddingEnd: 10,
          color: color.inactive,
          fontSize: FontSize.h5,
          marginRight: 10,
        }}>{content}</Text>
      </View>

    </View>

  </TouchableOpacity>)
} export default NotificationsList