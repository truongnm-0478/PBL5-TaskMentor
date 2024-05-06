import React ,{useState,useEffect} from "react";
import AsyncStorage from '@react-native-async-storage/async-storage';
import { Text, View , Image, ImageBackground, TouchableOpacity, TextInput, KeyboardAvoidingView,Keyboard, FlatList, VirtualizedList, Button, StyleSheet, ScrollView} from "react-native";
import { image,icons,color, FontSize } from "../../constants";
import Icon from 'react-native-vector-icons/FontAwesome';
import{isValidEmail,isValidPassword} from "../../utilies/Validations"

function Apppointment(props){
    const { navigation } = props;
    const { navigate, goBack } = navigation;
 return ( <View style={{
    backgroundColor:color.BackGround,
    flex:1,
 }}>
    <View style={{
        marginLeft:20,
        marginTop:40,
    }}>
        <Text>
            haha
        </Text>
    </View>
 </View>);
} export default Apppointment
