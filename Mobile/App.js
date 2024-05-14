import { StatusBar } from 'expo-status-bar';
import { StyleSheet, Text, View } from 'react-native';
import { Welcome, Login , Login_1, Register} from './screens/index';
import React,{ useEffect } from 'react';
import CategoryListItem from './screens/CategoryListItem'; 
import App_1 from './Navigation/App_1';
import SocketManager from './Socket/SocketManager';
export default function App() {
//   useEffect(() => {
//     const userId = 2; // Thay thế bằng user ID thực của bạn
//     const socketManager = new SocketManager(userId);

//     // Cleanup khi component unmount
//     return () => {
//         socketManager.disconnect();
//     };
// }, []);
  return (
    <View style={styles.container}>
      {/* <Text>Open up App.js to start working on your app!</Text> */}
      {/* <Welcome/> */}
      {/* <Login_1/> */}
       {/* <Register></Register>  */}
      <App_1></App_1>
      <StatusBar style="auto" />
    </View>
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
