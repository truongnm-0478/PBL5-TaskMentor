import React, { useState, useEffect } from "react";
import { Text, View, Image, ImageBackground, TouchableOpacity, TextInput, KeyboardAvoidingView, Keyboard } from "react-native";
import { image, icons, color, FontSize } from "../../constants";
import Icon from 'react-native-vector-icons/FontAwesome';
import { isValidEmail, isValidPassword } from "../../utilies/Validations"
function MyTeamsItem(props) {
  let { id, name, classname, classcode } = props.Teams
  const { onPress } = props
  if (!name) {
    return null; // hoặc có thể hiển thị một placeholder khác thay vì null
  }
  const firstInitial = name.charAt(0).toUpperCase();
  return (<TouchableOpacity onPress={onPress} style={{
    marginBottom: 5,
    paddingTop: 20,
    paddingStart: 10,
    flexDirection: 'row',
    alignItems: "center",

    marginEnd: 10,
  }}>
    <View>
      <View style={{
        width: 50,
        height: 50,
         borderRadius: 5, // Đặt borderRadius là 1 nửa của width/height để tạo hình tròn
        backgroundColor: 'lightgrey', // Màu nền mặc định hoặc có thể thay đổi theo yêu cầu
        justifyContent: 'center',
        alignItems: 'center',
        marginRight: 15,
        marginStart: 10,
      }}>
        <Text style={{ fontSize: FontSize.h3, color: 'black', fontWeight: 'bold' }}>{firstInitial}</Text>
      </View>
       <Text style={{
                color: 'white',
                backgroundColor:'blue',
                position:'absolute',
                right:5,
                top:0,
                fontSize:FontSize.h6*0.6,
                borderRadius:5,
                
            }}>2024</Text>
    </View>
    <View style={{
      flexDirection: 'column',
    }}>
      <Text style={{
        color: 'black',
        fontSize: FontSize.h5,
        fontWeight: 'bold',
        alignItems: "center",
        justifyContent: "center",
      }}>{name}</Text>
      <View style={{
        marginRight: 60
      }}>
        <Text style={{

          paddingEnd: 10,
          color: color.inactive,
          fontSize: FontSize.h5,
          marginRight: 10,
        }}>{classname}</Text>
      </View>

    </View>

  </TouchableOpacity>)
} export default MyTeamsItem