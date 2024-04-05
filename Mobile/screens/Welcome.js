import React ,{useState,useEffect} from "react";
import { Text, View , Image, ImageBackground, TouchableOpacity} from "react-native";
import { image,icons,color, FontSize } from "../constants";
import Icon from 'react-native-vector-icons/FontAwesome';
import {UIButton} from '../components/index'
export default function Welcome (props){
  const [accountTypes, SetAccountTypes ] = useState([
    {
      name: 'Influencer',
      is: true,
    },
    {
      name:'Business',
      is: false,
    },
    {
      name: 'Individual',
      is: false,
    }
  ])
    return <View style ={{ 
        backgroundColor : 'while',
      flex:100,
      marginTop: 40,
      

    }}> 
  <ImageBackground
  source={image.background}
  resizeMode="cover"
  style={{
    flex:100,
    //justifyContent: "center"
  }}>
    <View
    style={{
        
        flex:20,
    }}>
    <View style={{
    flexDirection : 'row',
  alignItems: "center",
    justifyContent :'flex-start',
   height: 50,
 
     
   }}>
   <Image source={icons.icon}
    style ={{

        height: 40,
        width:40,
        marginStart :10
    }}
    />
        <Text style={ {
            color : 'black',

        }}> This welcom </Text>
         <View style={{ flex:1}}/>
    <Icon  size ={15} style={{marginHorizontal:10}} name={'question-circle'}></Icon>
   </View>
    </View>
   <View
   style ={{
  
    flex: 20,
    justifyContent : "center",
    alignItems:"center"
   }}>
    <Text style={{marginBottom:10, 
        color:'white'}}> Welcome to </Text>
    <Text style={{marginBottom:10,
    color:'white',
    fontWeight:'bold'}}> Company.com</Text>
   
    <Text style={{marginBottom:10,
    color:'white'}}> please select your account type </Text>
   </View>
   <View
   style ={{
   
    flex:40
   }}>
          {accountTypes.map(accountType => <UIButton onPress={() => {let newAccountType = accountTypes.map(eachAccountType =>{
            return {...eachAccountType, is: eachAccountType.name == accountType.name}
          })
          SetAccountTypes(newAccountType);}
        
    } title={accountType.name} is={accountType.is} />)}
    
    {/* <UIButton onPress={()=>alert('haha')} title ='Business' is={false}/> 
    <UIButton onPress={()=>alert('haha')} title ='Individual' is={false}/>  */}
   </View>
   <View
   style ={{
   
    flex:20,
  
   }}>
      <UIButton title ='LOGIN'/>
      <Text style={{
    color:'white',
    alignSelf:"center"}}>Want to register new account  </Text>
    <TouchableOpacity onPress={()=>{alert('press register')}} style={{
      padding:5,

    }}>
    <Text style={{
    color:color.pimary,
    alignSelf:"center",
    textDecorationLine: "underline"}}>Register </Text>
    </TouchableOpacity>
    
   </View>
  </ImageBackground>
    </View>
}