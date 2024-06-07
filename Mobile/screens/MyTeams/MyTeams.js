import React ,{useState,useEffect} from "react";
import AsyncStorage from '@react-native-async-storage/async-storage';
import { Text, View , Image, ImageBackground, TouchableOpacity, TextInput, KeyboardAvoidingView,Keyboard, FlatList, VirtualizedList, Button, StyleSheet} from "react-native";
import { image,icons,color, FontSize } from "../../constants";
import Icon from 'react-native-vector-icons/FontAwesome';
import{isValidEmail,isValidPassword} from "../../utilies/Validations"
import {UIHeader} from '../../components'
import { teams } from "../../repositories";
import MyTeamsItem from "./MyTeamsItem";
function MyTeams(props){
    const { navigation } = props;
    const { navigate, goBack } = navigation;
    const [Teams, setMyteams]= useState([{}])
    useEffect(() => {
        const fetchData = async () => {
            try {
                
                const accessToken = await AsyncStorage.getItem('accessToken');
                const data = await teams.getTeamsByUser(accessToken)
                setMyteams(data)// Cập nhật state students với dữ liệu từ API
             
            } catch (error) {
               // console.error("Error fetching student data:", error);
            }
        };
        fetchData();
    }, [Teams]);
    return( <View style={{
        flex:1,
        backgroundColor:color.BackGround
    }}>
    <View style={{
        marginTop:40,
    }}>
         <UIHeader title={'My Teams'}
        // leftIconName={'arrow-left'}
        // JoinIcon={'plus-square'}
        // rightIconName={'qrcode'}
        //  onPressLeftIcon= {()=>{
        //     alert('back')
        //  }}
    //      onPressRightIcon= {()=>{
    //         navigate('Scanner')
    //      }}
    //      onPressJoinicon= {()=>{
    //       navigate('JoinTeam')
    //    }}
        ></UIHeader>
          <FlatList style={{
           marginBottom:70,
        }} data={Teams} 
        renderItem={({item}) => <MyTeamsItem onPress={()=>{
            navigate('MyteamDetail', {Teams :item})
        }} 
        Teams={item} key ={item.id}
        />
    }
        />
    </View>
    </View>
    );
} export default MyTeams