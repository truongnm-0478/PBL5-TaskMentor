import React, { useState, useEffect } from "react";
import { Text, View, Image, ImageBackground, TouchableOpacity, TextInput, KeyboardAvoidingView, Keyboard, FlatList, ScrollView } from "react-native";
import { image, icons, color, FontSize } from "../../constants";
import Icon from 'react-native-vector-icons/FontAwesome';
import { isValidEmail, isValidPassword } from "../../utilies/Validations"
import { UIHeader } from '../../components'
import AsyncStorage from "@react-native-async-storage/async-storage";
import StudentList from "./StudentList";
import { _class } from "../../repositories";
import NotificationsList from "./NotificationsList";
import ListTeams from "./ListTeam";
import QRCode from 'react-native-qrcode-svg';
function Class(props) {
    const { name, code } = props.route.params.teams;
    const { navigation } = props;
    const { navigate, goBack } = navigation;
    const [intro, setIntro] = useState([{}])
    const [students, setStudents] = useState([{}]);
    const [Notification, setNotification] = useState([{}])
    const [Teams, setTeams] = useState([{}])
    const [activeButton, setActiveButton] = useState([
        {
            name: 'Notifications',
            status: true
        },
        {
            name: 'List student',
            status: false
        },
        {
            name: 'Team',
            status: false
        },
        {
            name: 'Introduction',
            status: false
        },
    ]);
    useEffect(() => {
        const fetchData = async () => {
            try {
                const code_r = { code }
                const accessToken = await AsyncStorage.getItem('accessToken');
                const stdata = await _class.getStudentByClass(accessToken, code_r);

                setStudents(stdata); // Cập nhật state students với dữ liệu từ API
            } catch (error) {
                console.error("Error fetching student data:", error);
            }
        };
        fetchData();
    }, [students]);
    useEffect(() => {
        const fetchData = async () => {
            try {
                const code_r = { code }
                const accessToken = await AsyncStorage.getItem('accessToken');
                const data = await _class.getNotificationByClass(accessToken, code_r);
                setNotification(data)// Cập nhật state students với dữ liệu từ API
            } catch (error) {
                console.error("Error fetching student data:", error);
            }
        };
        fetchData();
    }, []);
    useEffect(() => {
        const fetchData = async () => {
            try {
                const code_r = { code }
                const accessToken = await AsyncStorage.getItem('accessToken');
                const data = await _class.getTeamByClass(accessToken, code_r);
                setTeams(data)// Cập nhật state students với dữ liệu từ API
            } catch (error) {
                console.error("Error fetching student data:", error);
            }
        };
        fetchData();
    }, [Teams]);
    useEffect(() => {
        const fetchData = async () => {
            try {
                const code_r = { code }
                const accessToken = await AsyncStorage.getItem('accessToken');
                const data = await _class.getIntroByClass(accessToken, code_r);

                setIntro(data)// Cập nhật state students với dữ liệu từ API
            } catch (error) {
                console.error("Error fetching student data:", error);
            }
        };
        fetchData();
    }, []);
    const handleButtonPress = (buttonIndex) => {
        const updatedButtons = activeButton.map((button, index) => ({
            ...button,
            status: index === buttonIndex
        }));
        setActiveButton(updatedButtons);
    };
    const notificationIndex = activeButton.findIndex(button => button.name === 'Notifications');
    const isNotificationActive = activeButton[notificationIndex].status;
    const liststudentIndex = activeButton.findIndex(button => button.name === 'List student');
    const isliststudentActive = activeButton[liststudentIndex].status;
    const listteamIndex = activeButton.findIndex(button => button.name === 'Team');
    const isliststeamActive = activeButton[listteamIndex].status;
    const introIndex = activeButton.findIndex(button => button.name === 'Introduction');
    const isintroActive = activeButton[introIndex].status;
    return (<View style={{
        backgroundColor: color.BackGround,
        flex: 1,
    }}>
        <View style={{ marginTop: 40, flexDirection: 'column', }}>
            <UIHeader title={name.length > 23 ? `${name.substring(0, 23)}...` : name} leftIconName={image.back} onPressLeftIcon={() => goBack()}></UIHeader>
            <View style={{ flexDirection: 'row' , marginHorizontal:5}}>
                {activeButton.map((button, index) => (
                    <TouchableOpacity
                        key={index}
                        style={{
                            //backgroundColor: button.status ? 'red' : color.inactive,
                            padding: 10,
                            borderRadius: 5,
                            // marginTop: 10,
                            // backgroundColor:'red',
                            alignItems: 'center',
                            marginLeft: 5
                        }}
                        onPress={() => handleButtonPress(index)}
                    >
                        <Text style={{ color: button.status ? color.BGlogin : 'black' , 
                            // borderBottomWidth:1,
                            // borderColor:color.BGlogin,
                        }}>{button.name}</Text>
                    </TouchableOpacity>
                ))}
            </View>
            {isliststudentActive == true && <FlatList style={{
                marginBottom: 70,
            }} data={students}
                renderItem={({ item }) => <StudentList onPress={() => {
                }}
                    students={item} key={item.name}
                />
                }
            />}
            {isNotificationActive == true && <FlatList style={{
                marginBottom: 70,
            }} data={Notification}
                renderItem={({ item }) => <NotificationsList onPress={() => {
                }}
                    Notification={item} key={item.name}
                />
                }
            />}
            {isliststeamActive == true && <View style={{

                //backgroundColor:'red',
                marginBottom: 360,
            }}>
                <FlatList style={{

                }} data={Teams}
                    renderItem={({ item }) => <ListTeams onPress={() => {
                        navigate('MyteamDetail', { Teams: item })
                    }}
                        Teams={item} key={item.name}
                    />
                    }
                    keyExtractor={(item) => item.id}
                // ListHeaderComponent={() => (
                //     <View style={{ flexDirection: 'row', padding: 0, backgroundColor: '#f2f2f2' }}>
                //         <Text style={{ flex: 1, textAlign: 'center' }}>ID</Text>
                //         <Text style={{ flex: 1, textAlign: 'center' }}>Name</Text>
                //         <Text style={{ flex: 1, textAlign: 'center' }}>Creat Time</Text>
                //     </View>
                // )}
                />
                <TouchableOpacity

                    onPress={() => { navigate('AddTeams', { code: code, students: students }) }} style={{
                        backgroundColor: color.BGlogin,
                        //justifyContent: "center",
                        //alignItems: "center",
                        marginTop: 20,
                        borderRadius: 10,
                        alignSelf: "center",
                        //   height:60
                    }}>
                    <Text style={{
                        padding: 10,
                        //   fontSize: FontSize.h5,
                        color: 'white',
                    }}>Create teams</Text>
                </TouchableOpacity>
            </View>

            }
            {isintroActive == true && <ScrollView style={{
                //backgroundColor:'red',
                flexDirection: 'column',
                marginHorizontal: 20,
            }}>
                <View style={{
                    borderWidth:1,
                    padding:20,
                    borderRadius:5,
                    borderColor:'#e8e8e8',
                }}>
                <View style={{
                    alignItems: 'center',
                    height: 60,
                    flexDirection: 'row',
                }}>
                    <Text style={{ fontWeight:'bold',
                        width:100,
                    }}>Name: </Text>
                    <Text>{intro.name}</Text>
                </View>
                <View style={{
                    alignItems: 'center',
                    height: 60,
                    flexDirection: 'row',
                }}>
                    <Text style={{ fontWeight:'bold',
                         width:100,
                    }}>Teacher: </Text>
                    <Text>{intro.teacher.user.name}</Text>
                </View>
                <View style={{
                    alignItems: 'center',
                    height: 60,
                    flexDirection: 'row',
                }}>
                    <Text style={{ fontWeight:'bold',
                         width:100,
                    }}>Phone: </Text>
                    <Text>{intro.teacher.user.phone}</Text>
                </View>
                <View style={{
                    alignItems: 'center',
                    height: 60,
                    flexDirection: 'row',
                }}>
                    <Text style={{ fontWeight:'bold',
                         width:100,
                    }}>Email: </Text>
                    <Text>{intro.teacher.user.email}</Text>
                </View>
                <View style={{
                    alignItems: 'center',
                    height: 60,
                    flexDirection: 'row',
                }}>
                    <Text style={{ fontWeight:'bold',
                         width:100,
                    }}>Create Time: </Text>
                    <Text>{intro.createDate}</Text>
                </View>
                <View style={{
                    alignItems: 'center',
                    height: 60,
                    flexDirection: 'row',
                }}>
                    <Text style={{ fontWeight:'bold',
                         width:100,
                    }}>Description: </Text>
                    <Text>{intro.description}</Text>
                </View>
                <View style={{
                    alignItems: 'center',
                    height: 60,
                    flexDirection: 'row',
                }}>
                    <Text style={{ fontWeight:'bold',
                         width:100,
                    }}>Code: </Text>
                    <Text>{intro.code}</Text>
                </View>
                <View style={{
                    alignItems: 'center',
                    //height: 60,
                    flexDirection: 'row',
                }}>
                    <Text style={{ fontWeight:'bold',
                         width:100,
                         
                    }}>QR: </Text>
                    <QRCode
                        value={intro.code}
                        //backgroundColor="red"
                        logoSize={40}

                    />
                </View>
                </View>
            </ScrollView>}
        </View>
    </View>
    );
}

export default Class;
