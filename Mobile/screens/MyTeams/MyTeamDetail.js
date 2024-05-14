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
import RequirementList from "./RequirmentList";
import { Alert } from "react-native";
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
    // ham lay thong tin team
    useEffect(() => {
        const fetchData = async () => {
            try {
                const id_ = { id }
                const accessToken = await AsyncStorage.getItem('accessToken');
                const data = await teams.getRequirement(id_, accessToken);
                setRequirement(data)// Cập nhật state students với dữ liệu từ API
             
                //console.log(requirement)

            } catch (error) {
                console.error("Error fetching student data:", error);
            }
        };
        fetchData();
    }, [requirement]);
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
    // xoa team
    const deleteTeam = async () => {
        try {
            const id_ = { id }
            const accessToken = await AsyncStorage.getItem('accessToken');
            const response = await teams.deleteTeams(accessToken, id_)

            navigate('My Teams')

        } catch (error) {
            console.log(error)
        }
    }

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
                    JoinIcon={image.delete}
                    onPressLeftIcon={() => {
                        goBack()
                    }}
                    //      onPressRightIcon= {()=>{
                    //         navigate('Scanner')
                    //      }}
                    onPressJoinicon={() => {
                        // deleteTeam()
                        try {
                            Alert.alert(
                                "Confirm Logout",
                                "Are you sure you want to delete team?",
                                [
                                    {
                                        text: "Cancel",
                                        onPress: () => console.log("Cancel Pressed"),
                                        style: "cancel"
                                    },
                                    {
                                        text: "OK",
                                        onPress: async () => {
                                            deleteTeam()
                                        }
                                    }
                                ]
                            );
                        } catch (error) {
                            // Xử lý lỗi nếu có
                            console.log(error);
                        }
                    }}
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
                    {isRequirementActive == true && <FlatList style={{

                    }} data={requirement}
                        renderItem={({ item }) => <RequirementList onPress={() => {
                        }}
                        requirement={item} key={item.id}
                        />
                        }
                    />}
                </View>
            </View>
        </View>
    );
} export default MyteamDetail