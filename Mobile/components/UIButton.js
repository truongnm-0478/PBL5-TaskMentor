import React from 'react'
import {TouchableOpacity, Text} from 'react-native'

import Icon from 'react-native-vector-icons/FontAwesome';
import { color } from '../constants';
function UIButton (props){
    return <TouchableOpacity onPress = {props.onPress} style={{
        borderColor: 'white',
        borderWidth: 2,
        height:40,
        borderRadius:5,
        marginHorizontal: 15,
        marginVertical:10,
        justifyContent:"center",
      alignItems:"center",
      backgroundColor:props.is== true? 'white': null
      }}>
    {props.is ==true && <Icon size={15} name={"check-circle"} style={{color:'aqua',
        position: "absolute",
        top:10,
        left:10
      }}>
  
                </Icon>}
        <Text style={{color: props.is == true? color.pimary:'aqua'
      }}> {props.title} </Text>
      </TouchableOpacity>
}
export default UIButton;