import React, { useState, useEffect } from "react";
import { Text, View, TextInput, TouchableOpacity } from "react-native";
import { image, color, FontSize } from "../../constants";
import { UIHeader } from '../../components';
import { user } from "../../repositories";
import AsyncStorage from '@react-native-async-storage/async-storage';

function UpdateProfile(props) {
    const { name, email, id, phone, role, username } = props.route.params.userInfo;
    const [upname, setupname] = useState(name);
    const [upemail, setupemail] = useState(email);
    const [upphone, setupphone] = useState(phone);
    const [upusername, setupusername] = useState(username);
    const { navigation } = props;
    const { navigate, goBack } = navigation;

    const update = async () => {
        try {
            const accessToken = await AsyncStorage.getItem('accessToken');
            const response = await user.update_profile(upname, upemail, upusername, id, role, upphone, accessToken);
            navigate('UITab');
        } catch (error) {
            console.log(error);
        }
    }

    return (
        <View style={{ marginTop: 40 }}>
            <UIHeader
                title='Update Profile'
                leftIconName={image.back}
                onPressLeftIcon={() => goBack()}
                onPressRightIcon={() => alert('next')}
            />
            <View style={{ marginHorizontal: 15 }}>
                <TextInput
                    onChangeText={(text) => setupusername(text)}
                    style={{
                        borderWidth: 1,
                        borderColor: color.border,
                        borderRadius: 5,
                        color: color.placeholder,
                        height: 60,
                        paddingHorizontal: 15
                    }}
                    defaultValue={username}
                />
                <Text style={{ color: 'red', fontSize: FontSize.h6 }}></Text>
            </View>
            <View style={{ marginHorizontal: 15 }}>
                <TextInput
                    onChangeText={(text) => setupphone(text)}
                    style={{
                        borderWidth: 1,
                        borderColor: color.border,
                        borderRadius: 5,
                        color: color.placeholder,
                        height: 60,
                        paddingHorizontal: 15
                    }}
                    defaultValue={phone}
                />
                <Text style={{ color: 'red', fontSize: FontSize.h6 }}></Text>
            </View>
            <View style={{ marginHorizontal: 15 }}>
                <TextInput
                    onChangeText={(text) => setupemail(text)}
                    style={{
                        borderWidth: 1,
                        borderColor: color.border,
                        borderRadius: 5,
                        color: color.placeholder,
                        height: 60,
                        paddingHorizontal: 15
                    }}
                    defaultValue={email}
                />
                <Text style={{ color: 'red', fontSize: FontSize.h6 }}></Text>
            </View>
            <View style={{ marginHorizontal: 15 }}>
                <TextInput
                    onChangeText={(text) => setupname(text)}
                    style={{
                        borderWidth: 1,
                        borderColor: color.border,
                        borderRadius: 5,
                        color: color.placeholder,
                        height: 60,
                        paddingHorizontal: 15
                    }}
                    defaultValue={name}
                />
                <Text style={{ color: 'red', fontSize: FontSize.h6 }}></Text>
            </View>
            <View style={{ marginHorizontal: 15, marginTop: 15 }}>
                <TouchableOpacity
                    onPress={() => update()}
                    style={{
                        backgroundColor: color.BGlogin,
                        justifyContent: "center",
                        alignItems: "center",
                        width: '100%',
                        borderRadius: 10,
                        alignSelf: "center",
                        height: 60
                    }}
                >
                    <Text style={{ padding: 10, fontSize: FontSize.h5, color: 'white' }}>
                        Update
                    </Text>
                </TouchableOpacity>
            </View>
        </View>
    );
}

export default UpdateProfile;
