import { StatusBar } from 'expo-status-bar';
import React, { useState, useEffect ,useRef,useContext} from "react";
import { Text, View, Image, ImageBackground, TouchableOpacity, TextInput, KeyboardAvoidingView, Keyboard, FlatList,Animated,StyleSheet } from "react-native";

import { Welcome, Login , Login_1, Register} from './screens/index';
import AsyncStorage from "@react-native-async-storage/async-storage";
import CategoryListItem from './screens/CategoryListItem'; 
import App_1 from './Navigation/App_1';
import { NotificationProvider, NotificationContext } from './Contexts/NotificationContext';
import { _class } from './repositories';
const NotificationBanner = () => {
  const { showNotification, setShowNotification, notificationOpacity } = useContext(NotificationContext);

  const hideNotification = () => {
      setShowNotification(false);
  };

  return (
      showNotification && (
        <Animated.View style={{ position: 'absolute', top: 700, right: 20, backgroundColor: '#66FF66', padding: 10, borderRadius: 10, opacity: notificationOpacity }}>
        <TouchableOpacity onPress={hideNotification}>
            <Text style={{ color: 'white' }}>Bạn có thông báo mới</Text>
        </TouchableOpacity>
    </Animated.View>
      )
  );
};

export default function App() {
  return (
    // <View style={styles.container}>
      
    //   <App_1></App_1>
    //   <StatusBar style="auto" />
    // </View>
    <NotificationProvider>
    <View style={styles.container}>
        <App_1 />
        <NotificationBanner />
        <StatusBar style="auto" />
    </View>
</NotificationProvider>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: '#fff',
   // alignItems: 'center',
    //justifyContent: 'center',
  },
});
