import React, { useState, useEffect } from "react";
import { Text, View, Image, ImageBackground, TouchableOpacity, TextInput, KeyboardAvoidingView, Keyboard, FlatList } from "react-native";
import { image, icons, color, FontSize } from "../../constants";
import Icon from 'react-native-vector-icons/FontAwesome';
import { isValidEmail, isValidPassword } from "../../utilies/Validations"
import { UIHeader } from '../../components'
import AsyncStorage from "@react-native-async-storage/async-storage";
import { _class, teams } from "../../repositories";
import MyTeamMembers from "./MyTeamMembers";
import CommentList from "./CommentList";
function MyteamDetail(props) {
    const { id, name } = props.route.params.Teams;
    const { navigation } = props;
    const { navigate, goBack } = navigation;
    const [member, setMember] = useState([{}])
    const [comment, setComment] = useState([{}])
    const [requirement, setRequirement] = useState([{}])
    const [activeButton, setActiveButton] = useState([
        {
            name: 'Requirement',
            status: true
        },
        {
            name: 'Feedback',
            status: false
        },
        {
            name: 'Members',
            status: false
        },

    ]);

    const handleButtonPress = (buttonIndex) => {
        const updatedButtons = activeButton.map((button, index) => ({
            ...button,
            status: index === buttonIndex
        }));
        setActiveButton(updatedButtons);
    };
    useEffect(() => {
        const fetchData = async () => {
            try {
                const id_ = { id }
                const accessToken = await AsyncStorage.getItem('accessToken');
                const data = await teams.getRequirement(id_, accessToken);
                setRequirement(data)// Cập nhật state students với dữ liệu từ API
                console.log(data)
                console.log(requirement)

            } catch (error) {
                console.error("Error fetching student data:", error);
            }
        };
        fetchData();
    }, []);
    useEffect(() => {
        const fetchData = async () => {
            try {
                const id_ = { id }
                const accessToken = await AsyncStorage.getItem('accessToken');
                const data = await teams.getComment(accessToken, id_);
                setComment(data)// Cập nhật state students với dữ liệu từ API
                console.log(data)
                console.log(comment)
            } catch (error) {
                console.error("Error fetching student data:", error);
            }
        };
        fetchData();
    }, []);
    useEffect(() => {
        const fetchData = async () => {
            try {
                const id_ = { id }
                const accessToken = await AsyncStorage.getItem('accessToken');
                const data = await teams.getMembersByTeam(accessToken, id_);
                setMember(data)// Cập nhật state students với dữ liệu từ API

            } catch (error) {
                console.error("Error fetching student data:", error);
            }
        };
        fetchData();
    }, []);
    const RequirementIndex = activeButton.findIndex(button => button.name === 'Requirement');
    const isRequirementActive = activeButton[RequirementIndex].status;
    const FeedbackIndex = activeButton.findIndex(button => button.name === 'Feedback');
    const isFeedbackActive = activeButton[FeedbackIndex].status;
    const MembersIndex = activeButton.findIndex(button => button.name === 'Members');
    const isMembersActive = activeButton[MembersIndex].status;
    return (
        <View style={{
            flex: 1,
            backgroundColor: color.BackGround,

        }}>
            <View style={{
                marginTop: 40,
                flexDirection: 'column',
            }}>
                <UIHeader title={name}
                    leftIconName={image.back}
                    // JoinIcon={'plus-square'}
                    // rightIconName={'qrcode'}
                    onPressLeftIcon={() => {
                        goBack()
                    }}
                //      onPressRightIcon= {()=>{
                //         navigate('Scanner')
                //      }}
                //      onPressJoinicon= {()=>{
                //       navigate('JoinTeam')
                //    }}
                ></UIHeader>
                <View style={{ flexDirection: 'row' }}>
                    {activeButton.map((button, index) => (
                        <TouchableOpacity
                            key={index}
                            style={{
                                //backgroundColor: button.status ? color.primary : color.inactive,
                                padding: 10,
                                borderRadius: 5,
                                //marginTop: 10,
                                alignItems: 'center',
                                marginRight: 10
                            }}
                            onPress={() => handleButtonPress(index)}
                        >
                            <Text style={{ color: button.status ? color.BGlogin : 'black' }}>{button.name}</Text>
                        </TouchableOpacity>
                    ))}
                </View>
                <View>
                    {isMembersActive == true && <FlatList style={{

                    }} data={member}
                        renderItem={({ item }) => <MyTeamMembers onPress={() => {
                        }}
                            member={item} key={item.id}
                        />
                        }
                    />}
                    {isFeedbackActive == true && <FlatList style={{

                    }} data={comment}
                        renderItem={({ item }) => <CommentList onPress={() => {
                        }}
                            comment={item} key={item.id}
                        />
                        }
                    />}
                </View>
            </View>
        </View>
    );
} export default MyteamDetail