import React ,{useState,useEffect} from "react";
import { Text, View , Image, ImageBackground, TouchableOpacity, TextInput, KeyboardAvoidingView,Keyboard, FlatList} from "react-native";
import { image,icons,color, FontSize } from "../../constants";
import Icon from 'react-native-vector-icons/FontAwesome';
import{isValidEmail,isValidPassword} from "../../utilies/Validations"
import {UIHeader} from '../../components'
import ChatItem from "./ChatItem";
import Messenger from '../Messenger/Messenger'
function Chat(props){
    const[users, setUsers] = useState([
        {
            url:'https://www.vietnamworks.com/hrinsider/wp-content/uploads/2023/12/anh-den-ngau.jpeg',
            name: "Hieu1",
            massege:'hello, how are you',
            numberOffUnreadMessages:0,
        },
        {
            url:'https://www.vietnamworks.com/hrinsider/wp-content/uploads/2023/12/anh-den-ngau.jpeg',
            name: "Hieu2",
            massege:'hello, how are you',
            numberOffUnreadMessages:10,
        },
        {
            url:'https://www.vietnamworks.com/hrinsider/wp-content/uploads/2023/12/anh-den-ngau.jpeg',
            name: "Hieu3",
            massege:'hello, how are you',
            numberOffUnreadMessages:6,
        },
        {
            url:'https://www.vietnamworks.com/hrinsider/wp-content/uploads/2023/12/anh-den-ngau.jpeg',
            name: "Hieu4",
            massege:'hello, how are you',
            numberOffUnreadMessages:10,
        },
        {
            url:'https://www.vietnamworks.com/hrinsider/wp-content/uploads/2023/12/anh-den-ngau.jpeg',
            name: "Hieu5",
            massege:'hello, how are you',
            numberOffUnreadMessages:6,
        },
        {
            url:'https://www.vietnamworks.com/hrinsider/wp-content/uploads/2023/12/anh-den-ngau.jpeg',
            name: "Hieu6",
            massege:'hello, how are you',
            numberOffUnreadMessages:3,
        },
        {
            url:'https://www.vietnamworks.com/hrinsider/wp-content/uploads/2023/12/anh-den-ngau.jpeg',
            name: "Hieu7",
            massege:'hello, how are you',
            numberOffUnreadMessages:4,
        },
        {
            url:'https://www.vietnamworks.com/hrinsider/wp-content/uploads/2023/12/anh-den-ngau.jpeg',
            name: "haha",
            massege:'hello, how are you',
            numberOffUnreadMessages:43,
        },
        {
            url:'https://www.vietnamworks.com/hrinsider/wp-content/uploads/2023/12/anh-den-ngau.jpeg',
            name: "haha",
            massege:'hello, how are you',
            numberOffUnreadMessages:2,
        }
        ,
        {
            url:'https://www.vietnamworks.com/hrinsider/wp-content/uploads/2023/12/anh-den-ngau.jpeg',
            name: "haha",
            massege:'hello, how are you',
            numberOffUnreadMessages:2,
        },
        {
            url:'https://www.vietnamworks.com/hrinsider/wp-content/uploads/2023/12/anh-den-ngau.jpeg',
            name: "haha",
            massege:'hello, how are you',
            numberOffUnreadMessages:2,
        },
        {
            url:'https://www.vietnamworks.com/hrinsider/wp-content/uploads/2023/12/anh-den-ngau.jpeg',
            name: "haha",
            massege:'hello, how are you',
            numberOffUnreadMessages:2,
        },
        {
            url:'https://www.vietnamworks.com/hrinsider/wp-content/uploads/2023/12/anh-den-ngau.jpeg',
            name: "haha",
            massege:'hello, how are you',
            numberOffUnreadMessages:2,
        }
    ])
   // const {numberOfstart}= props
   const{navigation,route}=props
   const{navigate,goBack}= navigation
    return <View style={{
        marginTop:40,
        flexDirection:'column',
        marginBottom:100
    }}>
        <UIHeader title={'Recent Chat'}
        // leftIconName={'arrow-left'}
        rightIconName={'search'}
        //  onPressLeftIcon= {()=>{
        //     alert('back')
        //  }}
         onPressRightIcon= {()=>{
            alert('next')
         }}
        ></UIHeader>
        <View style={{
            flexDirection:'row',
            justifyContent: "space-between",
            alignItems:"center",
            paddingStart: 10

        }}>
                <Text style={{
                    fontSize: FontSize.h6,
                    marginStart:10,
                }}>
                    6 unread masseses
                </Text>
                <Icon name='search' size={20} color='black' style={{
            padding:10,
        }} onPress={()=>{alert('haha')}}></Icon>
        </View>
        <FlatList style={{
           
        }} data={users} 
        renderItem={({item}) => <ChatItem onPress={()=>{
           navigate('Messenger', {user :item})
        }} 
        users={item} key ={item.url}
        />
    }
        />
    </View>
}export default Chat