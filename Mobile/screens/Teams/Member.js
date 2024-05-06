import React, { useState, useEffect } from "react";
import AsyncStorage from '@react-native-async-storage/async-storage';
import { Text, View, Image, ImageBackground, TouchableOpacity, TextInput, KeyboardAvoidingView, Keyboard, FlatList, VirtualizedList, Button, StyleSheet } from "react-native";
import { image, icons, color, FontSize } from "../../constants";
import Icon from 'react-native-vector-icons/FontAwesome';
import { isValidEmail, isValidPassword } from "../../utilies/Validations"
import { UIHeader } from '../../components'
import { teams } from "../../repositories";
import MembersList from "./MembersList";
function Members(props) {

    const { id, name } = props.route.params.Teams;
    const { navigation } = props;
    const { navigate, goBack } = navigation;
    const [member, setMember] = useState([{}])
    useEffect(() => {
        const fetchData = async () => {
            try {
                const id_ = { id }
                const accessToken = await AsyncStorage.getItem('accessToken');
                const data = await teams.getMembersByTeam(accessToken, id_);
                setMember(data)// Cập nhật state students với dữ liệu từ API
                console.log(member)
            } catch (error) {
                console.error("Error fetching student data:", error);
            }
        };
        fetchData();
    }, []);
    return <View style={{
        marginTop: 40
    }}>
        <UIHeader title={'Members'}
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

        }} data={member}
            renderItem={({ item }) => <MembersList onPress={() => {
            }}
                member={item} key={item.id}
            />
            }
        />
    </View>
} export default Members