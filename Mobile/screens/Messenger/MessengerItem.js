import React, { useState, useEffect } from "react";
import { Text, View, Image, ImageBackground, TouchableOpacity, TextInput, KeyboardAvoidingView, Keyboard } from "react-native";
import { image, icons, color, FontSize } from "../../constants";
import Icon from 'react-native-vector-icons/FontAwesome';
import { isValidEmail, isValidPassword } from "../../utilies/Validations"


function MessengerItem(props) {
  let { url, isSender, messengers, timestamp } = props.item
  const { onPress } = props

  return (isSender == false ? <TouchableOpacity onPress={onPress} style={{
    flexDirection: 'row',
    marginTop: 10
  }}>
    <Image style={{
      width: 50,
      height: 50,
      resizeMode: 'cover',
      borderRadius: 25,
      marginRight: 15,
      marginStart: 10
    }} source={{
      uri: url
    }}>
    </Image>
    <View style={{
      // backgroundColor:'red',
      flex: 1,
      marginEnd: 65,
      flexDirection:'row'
    }}>
      <Text style={{
        margin: 6,
        backgroundColor: color.messenger,
        color: 'black',
        fontSize: FontSize.h6,
        padding: 10,
        borderRadius: 10
      }}>
        {messengers}
      </Text>
    </View>
  </TouchableOpacity> : <TouchableOpacity onPress={onPress} style={{
    flexDirection: 'row',
    marginTop: 10,
    justifyContent:'flex-end'
  }}>
   
    <View style={{
      // backgroundColor:'red',
      flex: 1,
     marginStart:50,
     flexDirection:'row',
     justifyContent:'flex-end'
    }}>
      <Text style={{
        margin: 6,
        backgroundColor: color.messenger,
        color: 'black',
        fontSize: FontSize.h6,
        padding: 10,
        borderRadius: 10
      }}>
        {messengers}
      </Text>
    </View>
    <Image style={{
      width: 50,
      height: 50,
      resizeMode: 'cover',
      borderRadius: 25,
      marginRight: 15,
      marginStart: 10
    }} source={{
      uri: url
    }}>
    </Image>
  </TouchableOpacity>)

}
export default MessengerItem