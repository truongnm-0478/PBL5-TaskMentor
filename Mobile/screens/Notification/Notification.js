import React, { useState, useEffect ,useRef,useContext } from "react";
import { Text, View, Image, ImageBackground, TouchableOpacity, TextInput, KeyboardAvoidingView, Keyboard, FlatList,Animated } from "react-native";
import { image, icons, color, FontSize } from "../../constants";
import Icon from 'react-native-vector-icons/FontAwesome';
import { isValidEmail, isValidPassword } from "../../utilies/Validations"
import { UIHeader } from '../../components'
import NotificationItem from "./NotificationItem";
import AsyncStorage from "@react-native-async-storage/async-storage";
import { _class } from "../../repositories";
import { NotificationContext } from "../../Contexts/NotificationContext";
 function Notification(props){
    const{navigation,route}=props
    const{navigate,goBack}= navigation
    const [firstRun, setFirstRun] = useState(true);
    // const [showNotification, setShowNotification] = useState(false);
    const { showNotification, setShowNotification, notificationOpacity } = useContext(NotificationContext);
    // const notificationOpacity = useRef(new Animated.Value(0)).current;
    const [Notification, setNotification]= useState([
      {}
    ])
    const [Notification_confirm, setNotification_confirm]= useState([
        {}
      ])
    useEffect(() => {
        const fetchData = async () => {
            try {
              
                const accessToken = await AsyncStorage.getItem('accessToken');
                const data = await _class.getNotificationForUser(accessToken);
               setNotification(data)
               //console.log(data)
               // So sánh data với notificationConfirm
            if(firstRun){
                setNotification_confirm(data)
                setFirstRun(false)
            }
            if (!firstRun && JSON.stringify(data) !== JSON.stringify(Notification_confirm)) {
                setShowNotification(true);
            }
            setNotification_confirm(data)
            } catch (error) {
                //console.error("Error fetching student data:", error);
            }
        };
        fetchData();
    }, [Notification,firstRun]);
    
    const hideNotification = () => {
        setShowNotification(false);
        navigate('Notification');
    };
    // useEffect(() => {
    //     if (showNotification) {
    //         Animated.timing(notificationOpacity, {
    //             toValue: 1,
    //             duration: 500,  // Thời gian animation tính bằng mili giây
    //             useNativeDriver: true,
    //         }).start();
    //     } else {
    //         Animated.timing(notificationOpacity, {
    //             toValue: 0,
    //             duration: 500,
    //             useNativeDriver: true,
    //         }).start();
    //     }
    // }, [showNotification]);
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
          {/* {showNotification && (
                <TouchableOpacity style={{ position: 'absolute', top: 680, right: 20, backgroundColor: '#66FF66', padding: 10, borderRadius: 10 }} onPress={hideNotification}>
                    <Text style={{ color: 'white' }}>Bạn có thông báo mới</Text>
                </TouchableOpacity>
            )} */}
            {/* {showNotification && (
            <Animated.View style={{ position: 'absolute', top: 680, right: 20, backgroundColor: '#66FF66', padding: 10, borderRadius: 10, opacity: notificationOpacity }}>
                <TouchableOpacity onPress={hideNotification}>
                    <Text style={{ color: 'white' }}>Bạn có thông báo mới</Text>
                </TouchableOpacity>
            </Animated.View>
        )} */}
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