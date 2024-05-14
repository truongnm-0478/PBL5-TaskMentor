import React, { useState, useEffect } from "react";
import { Text, View, Image, ImageBackground, TouchableOpacity, TextInput, KeyboardAvoidingView, Keyboard , FlatList,StyleSheet} from "react-native";
import { image, icons, color, FontSize,background } from "../../constants";
import Icon from 'react-native-vector-icons/FontAwesome';
import { isValidEmail, isValidPassword } from "../../utilies/Validations"
import { getLastLetter } from "../../utilies";


const styles = StyleSheet.create({
  row: {
   //backgroundColor:'red',
    flexDirection: 'row',
    paddingVertical: 10,
    borderWidth:0.8,
    borderBottomWidth: 1,
    borderColor: '#ddd',
    marginBottom:10,
    marginHorizontal:10,
    borderRadius:10,
    height:55,
    justifyContent:'center',
    alignItems:'center'
  },
  cell: {
    flex: 1,
    alignItems: 'center',
    textAlign:"center"

  },
  Id:{
    width:30,
    alignItems: 'center',
    textAlign:"center"
  }
});

const ListTeams = ({ Teams , onPress}) => {
  const insertTime = new Date(Teams.insertTime);
  const monthNames = ["January", "February", "March", "April", "May", "June",
  "July", "August", "September", "October", "November", "December"];
  // Lấy các thành phần của ngày và giờ
  const year = insertTime.getFullYear();
  const month = monthNames[insertTime.getMonth()]; 
  const date = insertTime.getDate();
  const firstInitial = getLastLetter(Teams.name)
;

  // Tạo chuỗi ngày giờ định dạng
  const formattedInsertTime = `${month} ${date}, ${year}`;
  
  return (
    
    // <TouchableOpacity onPress={onPress} style={styles.row}>
    //   <Text style={styles.Id}>{Teams.id}</Text>
    //   <Text style={styles.cell}>{Teams.name}</Text>
    //   <Text style={styles.cell}>{formattedInsertTime}</Text>
    // </TouchableOpacity>
    <TouchableOpacity onPress={onPress} style={{
      backgroundColor: color.BackGround,
        marginBottom: 5,
        marginHorizontal: 15,
        paddingStart: 10,
        flexDirection: 'row',
        alignItems: "center",
        marginEnd: 10,
        paddingVertical: 10,
        marginBottom: 10,
        borderWidth: 0.7,
        borderRadius: 10,
        borderColor: '#e8e8e8',
        shadowColor: '#171717',
        shadowOffset: { width: 0, height: 4 },
        shadowOpacity: 0.3,
        shadowRadius: 3,
        elevation: 2,
    }}>
      <View>
        <View style={{
          width: 60,
          height: 60,
           borderRadius: 5, // Đặt borderRadius là 1 nửa của width/height để tạo hình tròn
          backgroundColor:background(Teams.name), // Màu nền mặc định hoặc có thể thay đổi theo yêu cầu
          justifyContent: 'center',
          alignItems: 'center',
          marginRight: 15,
          // marginStart: 10,
          // marginBottom:10,
        }}>
          <Text style={{ fontSize: 20, color: "white"}}>{firstInitial}</Text>
        </View>
         <Text style={{
                  color: 'white',
                  backgroundColor:'blue',
                  position:'absolute',
                  right:5,
                  top:0,
                  fontSize:FontSize.h6*0.6,
                  borderRadius:5,
                  
              }}></Text>
      </View>
      <View style={{
        flexDirection: 'column',
      }}>
        <Text style={{
          color: 'black',
          fontSize: FontSize.h4,
          fontWeight: '400',
          alignItems: "center",
          justifyContent: "center",
        }}>{Teams.name}</Text>
        <View style={{
          marginRight: 60
        }}>
          <Text style={{
  
            paddingEnd: 10,
            color: color.inactive,
            fontSize: FontSize.h5,
            marginRight: 10,
          }}>{formattedInsertTime}</Text>
        </View>
  
      </View>
  
    </TouchableOpacity>
  );
};

export default ListTeams;
