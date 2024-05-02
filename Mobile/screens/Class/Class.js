import React, { useState, useEffect } from "react";
import { Text, View, Image, ImageBackground, TouchableOpacity, TextInput, KeyboardAvoidingView, Keyboard, FlatList } from "react-native";
import { image, icons, color, FontSize } from "../../constants";
import Icon from 'react-native-vector-icons/FontAwesome';
import { isValidEmail, isValidPassword } from "../../utilies/Validations"
import { UIHeader } from '../../components'
import AsyncStorage from "@react-native-async-storage/async-storage";
import StudentList from "./StudentList";
import { _class } from "../../repositories";
import NotificationsList from "./NotificationsList";
import ListTeams from "./ListTeam";
function Class(props) {
    const { name, code } = props.route.params.teams;
    const { navigation } = props;
    const { goBack } = navigation;
    const [intro, setIntro]=useState([{}])
    const [students, setStudents] = useState([{}]);
    const [Notification, setNotification] = useState([{}])
    const [teams, setTeams] = useState([{}])
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
    }, []);
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
    }, []);
    useEffect(() => {
        const fetchData = async () => {
            try {
                const code_r = { code }
                const accessToken = await AsyncStorage.getItem('accessToken');
                const data = await _class.getIntroByClass(accessToken, code_r);
                console.log(data)
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
    return (
        <View style={{ marginTop: 40, flexDirection: 'column', flex: 1 }}>
            <UIHeader title={name} leftIconName={'arrow-left'} onPressLeftIcon={() => goBack()}></UIHeader>
            <View style={{ flexDirection: 'row' }}>
                {activeButton.map((button, index) => (
                    <TouchableOpacity
                        key={index}
                        style={{
                            backgroundColor: button.status ? color.primary : color.inactive,
                            padding: 10,
                            borderRadius: 5,
                            marginTop: 10,
                            alignItems: 'center',
                            marginRight: 10
                        }}
                        onPress={() => handleButtonPress(index)}
                    >
                        <Text style={{ color: 'black' }}>{button.name}</Text>
                    </TouchableOpacity>
                ))}
            </View>
            {isliststudentActive == true && <FlatList style={{

            }} data={students}
                renderItem={({ item }) => <StudentList onPress={() => {
                }}
                    students={item} key={item.name}
                />
                }
            />}
            {isNotificationActive == true && <FlatList style={{

            }} data={Notification}
                renderItem={({ item }) => <NotificationsList onPress={() => {
                }}
                    Notification={item} key={item.name}
                />
                }
            />}
            {isliststeamActive == true && <FlatList style={{

            }} data={teams}
                renderItem={({ item }) => <ListTeams onPress={() => {
                }}
                teams={item} key={item.name}
                />
                }
            />}
            {isintroActive==true && <View> 
                <Text>
                 {intro.name}
                </Text>
            </View> }
        </View>

    );
}

export default Class;
