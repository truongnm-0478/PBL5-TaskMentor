import React, { useState, useEffect } from "react";
import { Text, View, Image, ImageBackground, TouchableOpacity, TextInput, KeyboardAvoidingView, Keyboard } from "react-native";
import {getLastLetter} from '../../utilies/index'
import { image, icons, color, FontSize,background } from "../../constants";
import Icon from 'react-native-vector-icons/FontAwesome';
import { isValidEmail, isValidPassword } from "../../utilies/Validations"
function MyTeamsItem(props) {
  let { id, name, className, classCode, insertTime  } = props.Teams
  const inserttime = new Date(insertTime);
  const monthNames = ["January", "February", "March", "April", "May", "June",
  "July", "August", "September", "October", "November", "December"];
  // Lấy các thành phần của ngày và giờ
  const year = inserttime.getFullYear();
  const month = monthNames[inserttime.getMonth()]; 
  const date = inserttime.getDate();
  const formattedInsertTime = `${month} ${date}, ${year}`;
  const { onPress } = props
  if (!name) {
    return null; // hoặc có thể hiển thị một placeholder khác thay vì null
  }
  const firstInitial = getLastLetter(name)
  return (<TouchableOpacity onPress={onPress} style={{
    backgroundColor: color.BackGround,
      marginBottom: 5,
      marginHorizontal: 15,
      paddingStart: 10,
      flexDirection: 'row',
      alignItems: "center",
      marginEnd: 10,
      paddingVertical: 10,
      marginBottom: 10,
      borderWidth: 0.7,
      borderRadius: 10,
      borderColor: '#e8e8e8',
      shadowColor: '#171717',
      shadowOffset: { width: 0, height: 4 },
      shadowOpacity: 0.3,
      shadowRadius: 3,
      elevation: 2,
  }}>
    <View>
      <View style={{
        width: 70,
        height: 70,
         borderRadius: 5, // Đặt borderRadius là 1 nửa của width/height để tạo hình tròn
        backgroundColor:background(name), // Màu nền mặc định hoặc có thể thay đổi theo yêu cầu
        justifyContent: 'center',
        alignItems: 'center',
        marginRight: 15,
        // marginStart: 10,
        // marginBottom:10,
      }}>
        <Text style={{ fontSize: 20, color: "white"}}>{firstInitial}</Text>
      </View>
       <Text style={{
                color: 'white',
                backgroundColor:'blue',
                position:'absolute',
                right:5,
                top:0,
                fontSize:FontSize.h6*0.6,
                borderRadius:5,
                
            }}></Text>
    </View>
    <View style={{
      flexDirection: 'column',
    }}>
      <Text style={{
        color: 'black',
        fontSize: FontSize.h4,
        fontWeight: '400',
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
        }}>{className}</Text>
      </View>

    </View>

  </TouchableOpacity>)
} export default MyTeamsItem