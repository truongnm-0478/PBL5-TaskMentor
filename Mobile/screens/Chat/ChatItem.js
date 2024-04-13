import React ,{useState,useEffect} from "react";
import { Text, View , Image, ImageBackground, TouchableOpacity, TextInput, KeyboardAvoidingView,Keyboard} from "react-native";
import { image,icons,color, FontSize } from "../../constants";
import Icon from 'react-native-vector-icons/FontAwesome';
import{isValidEmail,isValidPassword} from "../../utilies/Validations"


function ChatItem(props){
    let {url, name, massege,numberOffUnreadMessages } = props.users
    const {onPress} = props

    return   (<TouchableOpacity onPress={onPress} style={{
        marginBottom:5,
        paddingTop: 20,
        paddingStart: 10,
        flexDirection:'row',
       // alignItems:"center"
    }}>
       <View>
       <Image style={{
            width:50,
            height:50,
            resizeMode:'cover',
            borderRadius:25,
            marginRight:15,
            marginStart:10
        }} source={{
            uri: url
        }}>
        </Image>
       {numberOffUnreadMessages>0 && <Text style={{
                color: 'white',
                backgroundColor:'red',
                position:'absolute',
                right:10,
                top:0,
                fontSize:FontSize.h6*0.8,
                borderRadius:10,
                paddingHorizontal: numberOffUnreadMessages>9 ?1:2,
            }}>{numberOffUnreadMessages}</Text>}
       </View>
      <View style={{
        flexDirection:'column'
      }}>
      <Text style={{
        color:'black',
        fontSize:FontSize.h5,
        fontWeight:'bold'
      }}>{name}</Text>
       <Text style={{
        color:color.inactive,
        fontSize:FontSize.h5,
      }}>{massege}</Text>
      </View>

    </TouchableOpacity>)
} 
export default ChatItem