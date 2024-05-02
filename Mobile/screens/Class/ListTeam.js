import React, { useState, useEffect } from "react";
import { Text, View, Image, ImageBackground, TouchableOpacity, TextInput, KeyboardAvoidingView, Keyboard } from "react-native";
import { image, icons, color, FontSize } from "../../constants";
import Icon from 'react-native-vector-icons/FontAwesome';
import { isValidEmail, isValidPassword } from "../../utilies/Validations"
function ListTeams(props){
    let { id, name,insertTime } = props.teams
    const {onPress} = props
    return(<TouchableOpacity onPress={onPress} style={{
        marginBottom:5,
        paddingTop: 20,
        paddingStart: 10,
        flexDirection:'row',
       // alignItems:"center"
      // alignItems:"center",
       marginEnd:10,
    }}>
      
      <View style={{
        flexDirection:'column',
      }}>
      <Text style={{
        color:'black',
        fontSize:FontSize.h5,
        fontWeight:'bold'
      }}>{id}</Text>
      <View style={{
        marginRight:60
      }}>
      <Text style={{
       
        paddingEnd:10,
        color:color.inactive,
        fontSize:FontSize.h5,
        marginRight:10,
      }}>{name}</Text>
      </View>
    
      </View>

    </TouchableOpacity>)
} export default ListTeams