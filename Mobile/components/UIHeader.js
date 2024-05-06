import React, { Component } from "react";
import { TouchableOpacity, Text, View, Image } from "react-native";
import Icon from 'react-native-vector-icons/FontAwesome5'
import { color, FontSize } from '../constants'
function UIHeader(props) {
    const { title, leftIconName = '', onPressLeftIcon, onPressRightIcon, rightIconName = '', image, JoinIcon = '', scanner,  onPressJoinicon } = props
    return (<View style={{
    }}>
        <View style={{
             marginBottom: 10,
            flexDirection: 'row',
            height: 55,
            justifyContent: 'space-between',
            alignItems: 'center',
           // backgroundColor:'red'
            
        }}>
            {/* {leftIconName && <Icon name={leftIconName} size={25} color='black' style={{
                padding: 10,
            }} onPress={onPressLeftIcon}
            ></Icon>} */}
              <TouchableOpacity onPress={onPressLeftIcon}>
            {leftIconName && <Image size={1} source={leftIconName}  style={
                {
                    marginStart: 10,
                    padding: 10,
                    width: 25,
                    height: 25,
                }
            } onPress={onPressRightIcon}>
            </Image>}
            </TouchableOpacity>
            {image && <Image style={{
                width: 50,
                height: 50,
                resizeMode: 'cover',
                borderRadius: 25,
                marginRight: 15,
                marginStart: 1,
            }} source={{
                uri: image
            }}>
            </Image>}
            <Text style={{
                marginLeft: 10,
                fontWeight: 500,
                fontSize: FontSize.h2,
                // alignSelf:'center',
                lineHeight: 45,
                color: 'black',
                flex: 1, // Sử dụng flex để căn chữ về phía trái
                paddingLeft: 10, // Thêm padding để tách chữ với biểu tượng
            }}>
                {title}
            </Text>
            {rightIconName && <Icon name={rightIconName} size={25} color='black' style={{
                padding: 10,
            }} onPress={onPressRightIcon}></Icon>}
            {/* {JoinIcon && <Icon name={JoinIcon} size={25} color='black' style={{
                padding: 10,
            }} onPress={onPressJoinicon}></Icon>} */}
            <TouchableOpacity onPress={onPressJoinicon}>
            {JoinIcon && <Image size={1} source={JoinIcon}  style={
                {
                    marginRight:10,
                    marginLeft:5,
                    padding: 10,
                    width: 25,
                    height: 25,
                }
            } onPress={onPressRightIcon}>
            </Image>}
            </TouchableOpacity>
             <TouchableOpacity onPress={onPressRightIcon}>
            {scanner && <Image size={1} source={scanner}  style={
                {
                    marginRight:10,
                    marginLeft:5,
                    padding: 10,
                    width: 23,
                    height: 23,
                }
            } onPress={onPressRightIcon}>
            </Image>}
            </TouchableOpacity>
        </View>
    </View>);
} export default UIHeader