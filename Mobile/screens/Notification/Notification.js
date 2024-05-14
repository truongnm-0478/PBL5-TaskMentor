import React, { useState, useEffect } from "react";
import { Text, View, Image, ImageBackground, TouchableOpacity, TextInput, KeyboardAvoidingView, Keyboard, FlatList } from "react-native";
import { image, icons, color, FontSize } from "../../constants";
import Icon from 'react-native-vector-icons/FontAwesome';
import { isValidEmail, isValidPassword } from "../../utilies/Validations"
import { UIHeader } from '../../components'
import NotificationItem from "./NotificationItem";
import AsyncStorage from "@react-native-async-storage/async-storage";
import { _class } from "../../repositories";
// danh sách thông báo
 function Notification(props){
    const [Notification, setNotification]= useState([
      {}
    ])
    useEffect(() => {
        const fetchData = async () => {
            try {
              
                const accessToken = await AsyncStorage.getItem('accessToken');
                const data = await _class.getNotificationForUser(accessToken);
               setNotification(data)
               //console.log(data)
            } catch (error) {
                console.error("Error fetching student data:", error);
            }
        };
        fetchData();
    }, [Notification]);
    
    return( <View style={{
        backgroundColor:color.BackGround,
        flex:1,
    }}>
    <View style={{
        marginTop:40,
        flexDirection:'column',
        marginBottom:100,
        backgroundColor: color.BackGround
    }}>
          <UIHeader title={'Notifications'}
        // leftIconName={'arrow-left'}
        rightIconName={'search'}
        //  onPressLeftIcon= {()=>{
        //     alert('back')
        //  }}
         onPressRightIcon= {()=>{
            alert('next')
         }}
        ></UIHeader>
         <FlatList style={{
           
        }} data={Notification} 
        renderItem={({item}) => <NotificationItem onPress={()=>{
          
        }} 
        Notification={item} key ={item.url}
        />
    }
        />
    </View>
    </View>
    );
 } export default Notification