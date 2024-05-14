import React, { useState, useEffect } from "react";
import { Text, View, Image, ImageBackground, TouchableOpacity, TextInput, KeyboardAvoidingView, Keyboard, FlatList, ScrollView } from "react-native";
import { image, icons, color, FontSize } from "../../constants";
import Icon from 'react-native-vector-icons/FontAwesome';
import { isValidEmail, isValidPassword } from "../../utilies/Validations"
import { UIHeader } from '../../components'

function UpdateProfile(props) {
    const { navigation, route } = props
    const { navigate, goBack } = navigation
    const {email,name,phone,username}= props.route.params.userInfo
    return <View style={{
        marginTop: 40,
    }}>
        <UIHeader title='Update Profile'

            leftIconName={image.back}
            //rightIconName={'ellipsis-v'}
            onPressLeftIcon={() => {
                goBack()
            }}
            onPressRightIcon={() => {
                alert('next')
            }}

        ></UIHeader>
        <View style={{
            marginHorizontal: 15,
        }}>
            <TextInput onChangeText={(Text) => {

            }
            }  defaultValue={name} 
            style={{
                borderWidth: 1,
                borderColor: color.border, // Màu sắc của đường viền
                borderRadius: 5, // Độ cong của góc (nếu cần)
                color: color.placeholder,
                paddingHorizontal: 15,
                height: 60,
                color: color.placeholder,
            }} placeholder="Enter your password" />
            <Text style={{
                color: 'red',
                fontSize: FontSize.h6
            }}></Text>
        </View>
        <View style={{
            marginHorizontal: 15,
        }}>
            <TextInput onChangeText={(Text) => {

            }
            } style={{
                borderWidth: 1,
                borderColor: color.border, // Màu sắc của đường viền
                borderRadius: 5, // Độ cong của góc (nếu cần)
                color: color.placeholder,
                paddingHorizontal: 15,
                height: 60,
                color: color.placeholder,
            }}defaultValue={email} 
             placeholder="Enter your password" />
            <Text style={{
                color: 'red',
                fontSize: FontSize.h6
            }}></Text>
        </View>
        <View style={{
            marginHorizontal: 15,
        }}>
            <TextInput onChangeText={(Text) => {

            }
            }
            defaultValue={phone}  style={{
                borderWidth: 1,
                borderColor: color.border, // Màu sắc của đường viền
                borderRadius: 5, // Độ cong của góc (nếu cần)
                color: color.placeholder,
                paddingHorizontal: 15,
                height: 60,
                color: color.placeholder,
            }} placeholder="Enter your password" />
            <Text style={{
                color: 'red',
                fontSize: FontSize.h6
            }}></Text>
        </View>
        <View style={{
            marginHorizontal: 15,
        }}>
            <TextInput onChangeText={(Text) => {

            }
            } defaultValue={username} 
            style={{
                borderWidth: 1,
                borderColor: color.border, // Màu sắc của đường viền
                borderRadius: 5, // Độ cong của góc (nếu cần)
                color: color.placeholder,
                paddingHorizontal: 15,
                height: 60,
                color: color.placeholder,
            }} placeholder="Enter your password" />
            <Text style={{
                color: 'red',
                fontSize: FontSize.h6
            }}></Text>
        </View>
        <View style={{
            marginHorizontal: 15,
            marginTop: 15,
        }}>
            <TouchableOpacity onPress={() => { }}
                style={{
                    backgroundColor: color.BGlogin,
                    justifyContent: "center",
                    alignItems: "center",
                    width: '100%',
                    borderRadius: 10,
                    alignSelf: "center",
                    height: 60
                }}>
                <Text style={{
                    padding: 10,
                    fontSize: FontSize.h5,
                    color: 'white'
                }}>Submit</Text>
            </TouchableOpacity>
        </View>

    </View>
} export default UpdateProfile