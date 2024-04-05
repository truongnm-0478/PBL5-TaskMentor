import { StatusBar } from 'expo-status-bar';
import { StyleSheet, Text, View } from 'react-native';
import { Welcome, Login , Login_1, Register} from './screens/index';
import React from 'react';
import CategoryListItem from './screens/CategoryListItem'; 
import App_1 from './Navigation/App_1';
export default function App() {
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
