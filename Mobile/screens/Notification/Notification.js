import React, { useState, useEffect } from "react";
import { Text, View, Image, ImageBackground, TouchableOpacity, TextInput, KeyboardAvoidingView, Keyboard, FlatList } from "react-native";
import { image, icons, color, FontSize } from "../../constants";
import Icon from 'react-native-vector-icons/FontAwesome';
import { isValidEmail, isValidPassword } from "../../utilies/Validations"
import { UIHeader } from '../../components'
import NotificationItem from "./NotificationItem";
 function Notification(props){
    const [Notification, setNotification]= useState([
        {
            url:'https://cdn-icons-png.freepik.com/256/1827/1827392.png?semt=ais_hybrid',
            title: 'thông báo lịch hẹn',
            detail: 'các em ngày 5/10 sẽ lên lớp và báo cáo tiến độ hoàn thành công việc của dự án',
            datetime: 10-12-2003
        },
        {
            url:'https://cdn-icons-png.freepik.com/256/1827/1827392.png?semt=ais_hybrid',
            title: 'thông báo lịch hẹn',
            detail: 'các em ngày 5/10 sẽ lên lớp và báo cáo tiến độ hoàn thành công việc của dự án',
            datetime: 10-12-2003
        },
        {
            url:'https://cdn-icons-png.freepik.com/256/1827/1827392.png?semt=ais_hybrid',
            title: 'thông báo lịch hẹn',
            detail: 'các em ngày 5/10 sẽ lên lớp và báo cáo tiến độ hoàn thành công việc của dự án',
            datetime: 10-12-2003
        },
        {
            url:'https://cdn-icons-png.freepik.com/256/1827/1827392.png?semt=ais_hybrid',
            title: 'thông báo lịch hẹn',
            detail: 'các em ngày 5/10 sẽ lên lớp và báo cáo tiến độ hoàn thành công việc của dự án',
            datetime: 10-12-2003
        },
        {
            url:'https://cdn-icons-png.freepik.com/256/1827/1827392.png?semt=ais_hybrid',
            title: 'thông báo lịch hẹn',
            detail: 'các em ngày 5/10 sẽ lên lớp và báo cáo tiến độ hoàn thành công việc của dự án',
            datetime: 10-12-2003
        },
        {
            url:'https://cdn-icons-png.freepik.com/256/1827/1827392.png?semt=ais_hybrid',
            title: 'thông báo lịch hẹn',
            detail: 'các em ngày 5/10 sẽ lên lớp và báo cáo tiến độ hoàn thành công việc của dự án',
            datetime: 10-12-2003
        },
    ])

    return <View style={{
        marginTop:40,
        flexDirection:'column',
        marginBottom:100
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
 } export default Notification