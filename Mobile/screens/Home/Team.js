import React, { useState, useEffect } from "react";
import { Text, View, Image, ImageBackground, TouchableOpacity, TextInput, KeyboardAvoidingView, Keyboard, FlatList } from "react-native";
import { image, icons, color, FontSize } from "../../constants";
import Icon from 'react-native-vector-icons/FontAwesome';
import { isValidEmail, isValidPassword } from "../../utilies/Validations"
import { UIHeader } from '../../components'
import AsyncStorage from "@react-native-async-storage/async-storage";
function Team(props){
    const { image, title, detail, datetime} = props.route.params.teams
    const { navigation, route } = props
    const { navigate, goBack } = navigation
  return <View style={{
    marginTop: 40,
    flexDirection: 'column',
    flex: 1
  }}>
       <UIHeader title={'Teams'}
         leftIconName={'arrow-left'}
        //rightIconName={'qrcode'}
         onPressLeftIcon= {()=>{
           goBack()
         }}
        //  onPressRightIcon= {()=>{
        //     navigate('Scanner')
        //  }}
        ></UIHeader>
    <Text>
        this is teams
    </Text>
  </View>
} export default Team