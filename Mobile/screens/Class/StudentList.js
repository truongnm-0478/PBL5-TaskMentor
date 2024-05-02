import React, { useState, useEffect } from "react";
import { Text, View, Image, ImageBackground, TouchableOpacity, TextInput, KeyboardAvoidingView, Keyboard , StyleSheet} from "react-native";
import { image, icons, color, FontSize } from "../../constants";
import Icon from 'react-native-vector-icons/FontAwesome';
import { isValidEmail, isValidPassword } from "../../utilies/Validations"
function StudentList(props){
    let {name, phone, email } = props.students
    const {onPress} = props
    return <View style={{
        marginTop:40,
    }}>
        <View style={styles.container}>
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
        <Text style={styles.info}>{email}</Text>
      </View>
    </View>
    </View>
} 
const styles = StyleSheet.create({
    container: {
      marginTop: 20,
      paddingHorizontal: 20,
      paddingVertical: 10,
      borderWidth: 1,
      borderColor: "gray",
      borderRadius: 10,
    },
    row: {
      flexDirection: "row",
      marginBottom: 5,
    },
    label: {
      fontWeight: "bold",
      marginRight: 10,
    },
    info: {
      flex: 1,
    },
  });
export default StudentList