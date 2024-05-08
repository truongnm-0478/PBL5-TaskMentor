import React, { useState, useEffect } from "react";
import { Text, View, Image, ImageBackground, TouchableOpacity, TextInput, KeyboardAvoidingView, Keyboard, StyleSheet } from "react-native";
import { image, icons, color, FontSize } from "../../constants";
import Icon from 'react-native-vector-icons/FontAwesome';
import { isValidEmail, isValidPassword } from "../../utilies/Validations"
function MyTeamMembers(props) {
  let { studentID, phone, mail, isLeader ,name} = props.member
  const role = isLeader ? "Leader" : "Member";
  const { onPress } = props
  return (
   <TouchableOpacity
   style={styles.touchableOpacity}
   onPress={onPress}
   activeOpacity={0.8}
 >
   <View style={styles.container}>
     <View style={styles.row}>
       <Text style={styles.label}>ID:</Text>
       <Text style={styles.info}>{studentID}</Text>
     </View>
     <View style={styles.row}>
       <Text style={styles.label}>Name:</Text>
       <Text style={styles.info}>{name}</Text>
     </View>
     <View style={styles.row}>
       <Text style={styles.label}>Phone:</Text>
       <Text style={styles.info}>{phone}</Text>
     </View>
     <View style={styles.row}>
       <Text style={styles.label}>Email:</Text>
       <Text style={styles.info}>{mail}</Text>
     </View>
     <View style={styles.row}>
       <Text style={styles.label}>Role:</Text>
       <Text style={{
        paddingHorizontal:7,
        backgroundColor:role==='Leader'?'red':'aqua',
        borderRadius:5,
        color:'white'
       }}>{role}</Text>
     </View>
   </View>
 </TouchableOpacity>
);
}
const styles = StyleSheet.create({
  touchableOpacity: {
    marginVertical: 10,
    marginHorizontal: 10,
    paddingHorizontal: 20,
    paddingVertical: 10,
    borderRadius: 10,
    borderTopColor:'#e8e8e8',
    borderBottomColor:'#e8e8e8',
    borderRightColor:'#e8e8e8',
    backgroundColor: "white",
    borderRadius:10,
    borderLeftWidth:4,
    borderLeftColor:color.Text_tabbar,
    shadowColor: "#171717",
    shadowOffset: {
      width: 0,
      height: 4,
    },
    shadowOpacity: 0.3,
    shadowRadius: 3,
    elevation: 2,
  },
  container: {
  
    
    overflow: "hidden", // Để shadow hiển thị đúng
  },
  row: {
    flexDirection: "row",
    marginBottom: 5,
  },
  
  label: {
    width:50,
    fontWeight: "bold",
    marginRight: 10,
  },
  info: {
    flex: 1,
  },
  
});

export default MyTeamMembers