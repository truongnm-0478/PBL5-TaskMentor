import React ,{useState,useEffect} from "react";
import AsyncStorage from '@react-native-async-storage/async-storage';
import { Text, View , Image, ImageBackground, TouchableOpacity, TextInput, KeyboardAvoidingView,Keyboard, FlatList, VirtualizedList, Button, StyleSheet} from "react-native";
import { image,icons,color, FontSize } from "../../constants";
import Icon from 'react-native-vector-icons/FontAwesome';
import{isValidEmail,isValidPassword} from "../../utilies/Validations"
import {UIHeader} from '../../components'
import HomeItem from "./HomeItem"
import { _class } from "../../repositories";
function Home(props){
    const{navigation,route}=props
    const{navigate,goBack}= navigation
    const[teams, setTeams]= useState([
       {}
    ])
    useEffect(() => {
        // Gọi hàm getClass khi component được mount
        getClassData();
      }, []);
      const getClassData = async () => {
        try {
          // Gọi hàm getClass từ repository để lấy danh sách lớp học từ API
          const accessToken = await AsyncStorage.getItem('accessToken');
          const classData = await _class.getClass(accessToken);
          // Gán danh sách lớp học vào biến teams
          setTeams(classData);
        } catch (error) {
          console.log(error)
        }
      };
    return <View style={{
        marginTop:40,
        flexDirection:'column',
        marginBottom:100
    }}>
         <UIHeader title={'Teams'}
        // leftIconName={'arrow-left'}
        JoinIcon={'qrcode'}
        rightIconName={'qrcode'}
        //  onPressLeftIcon= {()=>{
        //     alert('back')
        //  }}
         onPressRightIcon= {()=>{
            navigate('Scanner')
         }}
         onPressJoinicon= {()=>{
          navigate('JoinTeam')
       }}
        ></UIHeader>
        <FlatList style={{
           
        }} data={teams} 
        renderItem={({item}) => <HomeItem onPress={()=>{
            navigate('Class', {teams :item})
        }} 
        teams={item} key ={item.id}
        />
    }
        />
    </View>
} export default Home
