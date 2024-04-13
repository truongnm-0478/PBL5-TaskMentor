import React, { useState, useEffect } from "react";
import { Text, View, Image, ImageBackground, TouchableOpacity, TextInput, KeyboardAvoidingView, Keyboard, FlatList } from "react-native";
import { image, icons, color, FontSize } from "../../constants";
import Icon from 'react-native-vector-icons/FontAwesome';
import { isValidEmail, isValidPassword } from "../../utilies/Validations"
import { UIHeader } from '../../components'
import MessengerItem from "./MessengerItem";
function Messenger(props) {
    const [messages, setmessages] = useState([
    ])
    const [typedText, setTypedText] = useState('')
    const [chatHistory, setChatHistory] = useState([
        {
            url: 'https://www.vietnamworks.com/hrinsider/wp-content/uploads/2023/12/anh-den-ngau.jpeg',
            isSender: true,
            messengers: 'hello',
            timestamp: 1712845497000,

        },
        {
            url: 'https://www.vietnamworks.com/hrinsider/wp-content/uploads/2023/12/anh-den-ngau.jpeg',
            isSender: true,
            messengers: 'how are you?',
            timestamp: 1712845557000,

        },
        {
            url: 'https://tmdl.edu.vn/wp-content/uploads/2022/07/hinh-nen-facebook-dep-22-1.jpg',
            isSender: false,
            messengers: 'how con me mayjjkbjbjkbjbjjjjjjjjjjjjjjjjjeeeeeeeeeeeeeeeeeeeeeeeeeeeeej',
            timestamp: 1712845617000,

        },
        {
            url: 'https://www.vietnamworks.com/hrinsider/wp-content/uploads/2023/12/anh-den-ngau.jpeg',
            isSender: true,
            messengers: 'thang cho nay, m biet bo m la ai khong',
            timestamp: 1712845497000,

        },
        {
            url: 'https://tmdl.edu.vn/wp-content/uploads/2022/07/hinh-nen-facebook-dep-22-1.jpg',
            isSender: false,
            messengers: 'tu lop 1 den lop 5 tao con chua so, so gi thang lop 7 nhu m',
            timestamp: 1712845497000,

        },
        {
            url: 'https://www.vietnamworks.com/hrinsider/wp-content/uploads/2023/12/anh-den-ngau.jpeg',
            isSender: true,
            messengers: 'a thế à',
            timestamp: 1712845497000,

        },
        {
            url: 'https://tmdl.edu.vn/wp-content/uploads/2022/07/hinh-nen-facebook-dep-22-1.jpg',
            isSender: false,
            messengers: 'a thế làm sao mà à',
            timestamp: 1712845497000,

        },



    ])  
    // const {numberOfstart}= props
    const { url, name } = props.route.params.user
    const { navigation, route } = props
    const { navigate, goBack } = navigation
    return <View style={{
        marginTop: 40,
        flexDirection: 'column',
        flex: 1

    }}>
        <UIHeader title={name}
            
            leftIconName={'arrow-left'}
            rightIconName={'ellipsis-v'}
            onPressLeftIcon={() => {
                goBack()
            }}
            onPressRightIcon={() => {
                alert('next')
            }}
            image={url}
        ></UIHeader>

        <FlatList style={{
            flex: 1,
        }} data={chatHistory}
            renderItem={({ item }) => <MessengerItem onPress={() => {
                alert(`you press item name: ${item.timestamp}`);
            }}
                item={item} key={`${item.timestamp}`} />}
        />
        <View style={{
            height: 50,
            backgroundColor: '#FFFFFF',
            flexDirection: 'row',
            justifyContent: 'space-between',
            alignItems: 'center'
        }}>
            <TextInput onChangeText={(typedText) => {
                setTypedText(typedText)
            }} placeholder="Enter your messege" placeholderTextColor={color.inactive} value={typedText} style={{
                // borderWidth: 1,
                // borderColor: color.border, // Màu sắc của đường viền
                borderRadius: 5, // Độ cong của góc (nếu cần)
                color: color.placeholder,
                paddingHorizontal: 15,
                height: 60,
                color: 'black',
                paddingLeft: 10,
                color: color.placeholder,
            }} />
            <TouchableOpacity onPress={()=>{
                // if(typedText.trim().length==0){
                //     return
                // }
                // let newMessengerObject={

                // }
            }}>
                <Icon style={{
                    padding: 10,
                }} name='paper-plane' size={30} />

            </TouchableOpacity>

        </View>

    </View>
} export default Messenger