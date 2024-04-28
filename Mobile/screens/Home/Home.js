import React ,{useState,useEffect} from "react";
import { Text, View , Image, ImageBackground, TouchableOpacity, TextInput, KeyboardAvoidingView,Keyboard, FlatList, VirtualizedList, Button, StyleSheet} from "react-native";
import { image,icons,color, FontSize } from "../../constants";
import Icon from 'react-native-vector-icons/FontAwesome';
import{isValidEmail,isValidPassword} from "../../utilies/Validations"
import {UIHeader} from '../../components'
import HomeItem from "./HomeItem"
function Home(props){
    const{navigation,route}=props
    const{navigate,goBack}= navigation
    const[teams, Teams]= useState([
        {   id: 1,
            title: "Pbl5",
            year: 2024,
            image:"https://i.pinimg.com/736x/5b/b7/ea/5bb7ea5849b86c43ef4691dea43ee049.jpg",
            detail: 'nhóm này lập ra để hổ trợ sinh viên làm đồ án năm 2025'

        },
        {   id: 2,
            title: "Pbl5",
            year: 2024,
            image:"https://i.pinimg.com/736x/5b/b7/ea/5bb7ea5849b86c43ef4691dea43ee049.jpg",
            detail: 'nhóm này lập ra để hổ trợ sinh viên làm đồ án năm 2025'

        },
        {   id: 3,
            title: "Pbl5",
            year: 2024,
            image:"https://i.pinimg.com/736x/5b/b7/ea/5bb7ea5849b86c43ef4691dea43ee049.jpg",
            detail: 'nhóm này lập ra để hổ trợ sinh viên làm đồ án năm 2025'

        },
        {   id: 4,
            title: "Pbl5",
            year: 2024,
            image:"https://i.pinimg.com/736x/5b/b7/ea/5bb7ea5849b86c43ef4691dea43ee049.jpg",
            detail: 'nhóm này lập ra để hổ trợ sinh viên làm đồ án năm 2025'

        },
        {   id: 5,
            title: "Pbl5",
            year: 2024,
            image:"https://i.pinimg.com/736x/5b/b7/ea/5bb7ea5849b86c43ef4691dea43ee049.jpg",
            detail: 'nhóm này lập ra để hổ trợ sinh viên làm đồ án năm 2025'

        },
        {   id: 6,
            title: "Pbl5",
            year: 2024,
            image:"https://i.pinimg.com/736x/5b/b7/ea/5bb7ea5849b86c43ef4691dea43ee049.jpg",
            detail: 'nhóm này lập ra để hổ trợ sinh viên làm đồ án năm 2025'

        },
        {   id: 7,
            title: "Pbl5",
            year: 2024,
            image:"https://i.pinimg.com/736x/5b/b7/ea/5bb7ea5849b86c43ef4691dea43ee049.jpg",
            detail: 'nhóm này lập ra để hổ trợ sinh viên làm đồ án năm 2025'

        },
        {   id: 8,
            title: "Pbl5",
            year: 2024,
            image:"https://i.pinimg.com/736x/5b/b7/ea/5bb7ea5849b86c43ef4691dea43ee049.jpg",
            detail: 'nhóm này lập ra để hổ trợ sinh viên làm đồ án năm 2025'

        },
        {   id: 9,
            title: "Pbl5",
            year: 2024,
            image:"https://i.pinimg.com/736x/5b/b7/ea/5bb7ea5849b86c43ef4691dea43ee049.jpg",
            detail: 'nhóm này lập ra để hổ trợ sinh viên làm đồ án năm 2025'

        },
        {   id: 10,
            title: "Pbl5",
            year: 2024,
            image:"https://i.pinimg.com/736x/5b/b7/ea/5bb7ea5849b86c43ef4691dea43ee049.jpg",
            detail: 'nhóm này lập ra để hổ trợ sinh viên làm đồ án năm 2025'

        },
        {   id: 11,
            title: "Pbl5",
            year: 2024,
            image:"https://i.pinimg.com/736x/5b/b7/ea/5bb7ea5849b86c43ef4691dea43ee049.jpg",
            detail: 'nhóm này lập ra để hổ trợ sinh viên làm đồ án năm 2025'

        },
    ])
    return <View style={{
        marginTop:40,
        flexDirection:'column',
        marginBottom:100
    }}>
         <UIHeader title={'Teams'}
        // leftIconName={'arrow-left'}
        rightIconName={'qrcode'}
        //  onPressLeftIcon= {()=>{
        //     alert('back')
        //  }}
         onPressRightIcon= {()=>{
            navigate('Scanner')
         }}
        ></UIHeader>
        <FlatList style={{
           
        }} data={teams} 
        renderItem={({item}) => <HomeItem onPress={()=>{
            navigate('Team', {teams :item})
        }} 
        teams={item} key ={item.id}
        />
    }
        />
    </View>
} export default Home