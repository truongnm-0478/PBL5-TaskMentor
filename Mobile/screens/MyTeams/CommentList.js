import React, { useState, useEffect } from "react";
import { Text, View, Image, ImageBackground, TouchableOpacity, TextInput, KeyboardAvoidingView, Keyboard } from "react-native";
import { image, icons, color, FontSize, background } from "../../constants";
import { getLastLetter } from "../../utilies";
import Icon from 'react-native-vector-icons/FontAwesome';
import { isValidEmail, isValidPassword } from "../../utilies/Validations"
function CommentList (props){
    let { comment, id, name, projectTitle ,projectId,username,insertTime} = props.comment
    const firstInitials = getLastLetter(name)
    return(<TouchableOpacity  style={{
        marginBottom: 5,
        paddingTop: 20,
        paddingStart: 10,
        flexDirection: 'row',
        // alignItems:"center"
        // alignItems:"center",
        marginEnd: 10,
      }}>
        <View>
         
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
            }}>{comment}</Text>
          </View>
    
        </View>
    
      </TouchableOpacity>);
} export default CommentList