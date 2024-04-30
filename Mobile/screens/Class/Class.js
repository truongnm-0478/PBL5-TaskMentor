import React, { useState, useEffect } from "react";
import { Text, View, Image, ImageBackground, TouchableOpacity, TextInput, KeyboardAvoidingView, Keyboard, FlatList } from "react-native";
import { image, icons, color, FontSize } from "../../constants";
import Icon from 'react-native-vector-icons/FontAwesome';
import { isValidEmail, isValidPassword } from "../../utilies/Validations"
import { UIHeader } from '../../components'
import AsyncStorage from "@react-native-async-storage/async-storage";
function Class(props) {
    const { name } = props.route.params.teams;
    const { navigation } = props;
    const { goBack } = navigation;
    const [students, setStudents] = useState([
        {
            name: 'Trần Kim Hiếu',
            phone: '0976568992',
            
        }
    ]);
    const [activeButton, setActiveButton] = useState([
        {
            name: 'Button 1',
            status: true
        },
        {
            name: 'Button 2',
            status: false
        },
        {
            name: 'Button 3',
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
        </View>

    );
}

export default Class;