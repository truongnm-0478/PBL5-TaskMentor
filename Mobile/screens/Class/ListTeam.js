import React, { useState, useEffect } from "react";
import { Text, View, Image, ImageBackground, TouchableOpacity, TextInput, KeyboardAvoidingView, Keyboard , FlatList,StyleSheet} from "react-native";
import { image, icons, color, FontSize } from "../../constants";
import Icon from 'react-native-vector-icons/FontAwesome';
import { isValidEmail, isValidPassword } from "../../utilies/Validations"



const styles = StyleSheet.create({
  row: {
   
    flexDirection: 'row',
    paddingVertical: 10,
    borderBottomWidth: 1,
    borderColor: '#ddd',
  },
  cell: {
    flex: 1,
    alignItems: 'center',
    textAlign:"center"

  },
});

const ListTeams = ({ teams , onPress}) => {
  const insertTime = new Date(teams.insertTime);
  // Lấy các thành phần của ngày và giờ
  const year = insertTime.getFullYear();
  const month = insertTime.getMonth() + 1;
  const date = insertTime.getDate();
  const hours = insertTime.getHours();
  const minutes = insertTime.getMinutes();
  const seconds = insertTime.getSeconds();

  // Tạo chuỗi ngày giờ định dạng
  const formattedInsertTime = `${date}/${month}/${year} ${hours}:${minutes}:${seconds}`;
  
  return (
    
    <TouchableOpacity onPress={onPress} style={styles.row}>
      <Text style={styles.cell}>{teams.id}</Text>
      <Text style={styles.cell}>{teams.name}</Text>
      <Text style={styles.cell}>{formattedInsertTime}</Text>
    </TouchableOpacity>
  );
};

export default ListTeams;
