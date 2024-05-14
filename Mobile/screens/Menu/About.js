import React, { useState, useEffect } from "react";
import { Text, View, Image, ImageBackground, TouchableOpacity, TextInput, KeyboardAvoidingView, Keyboard, FlatList,ScrollView } from "react-native";
import { image, icons, color, FontSize } from "../../constants";
import Icon from 'react-native-vector-icons/FontAwesome';
import { isValidEmail, isValidPassword } from "../../utilies/Validations"
import { UIHeader } from '../../components'

function About(props){

    const { navigation, route } = props
    const { navigate, goBack } = navigation
    return <View style={{
        marginTop:40,
    }}>
       <UIHeader title='About'
            
            leftIconName={image.back}
           // rightIconName={'ellipsis-v'}
            onPressLeftIcon={() => {
                goBack()
            }}
            onPressRightIcon={() => {
                alert('next')
            }}
          
        ></UIHeader>

    </View>
}export default About