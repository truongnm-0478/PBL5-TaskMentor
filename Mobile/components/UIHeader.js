import React,{Component} from "react";
import { TouchableOpacity, Text, View ,Image} from "react-native";
import Icon from 'react-native-vector-icons/FontAwesome5'
import {color, FontSize} from '../constants'
function UIHeader( props){
    const {title,leftIconName='',onPressLeftIcon,onPressRightIcon,rightIconName='',image} = props
   
    return <View style={{
       // backgroundColor:'white',
        flexDirection:'row',
        height:55,
       justifyContent:'space-between',
         alignItems:'center',
       
    }}>
        {leftIconName && <Icon name={leftIconName} size={25} color='black' style={{
            padding:10,
        }} onPress={onPressLeftIcon}
        ></Icon>}
        {image && <Image style={{
            width:50,
            height:50,
            resizeMode:'cover',
            borderRadius:25,
            marginRight:15,
            marginStart:10
        }} source={{
            uri: image
        }}>
        </Image>}
            <Text style={{
            
                fontSize:FontSize.h4,
               // alignSelf:'center',
                lineHeight:45,
               color:'black',
                flex: 1, // Sử dụng flex để căn chữ về phía trái
                paddingLeft: 10, // Thêm padding để tách chữ với biểu tượng
            }}>
                {title}
            </Text>
           {rightIconName && <Icon name={rightIconName} size={25} color='black' style={{
            padding:10,
            

        }} onPress={onPressRightIcon}></Icon>}
    </View>
} export default UIHeader