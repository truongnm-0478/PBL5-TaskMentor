import React, { useState, useEffect } from "react";
import { Text, View, Image, ImageBackground, TouchableOpacity, TextInput, KeyboardAvoidingView, Keyboard, FlatList, VirtualizedList, Button, StyleSheet } from "react-native";
import { image, icons, color, FontSize } from "../../constants";
import Icon from 'react-native-vector-icons/FontAwesome';
import { isValidEmail, isValidPassword } from "../../utilies/Validations"
import { UIHeader } from '../../components'
import { BarCodeScanner } from 'expo-barcode-scanner'

function Scanner(props) {
  const [hasPermission, setHasPermission] = useState(null);
  const [scanned, setScanned] = useState(false);
  const [text, setText] = useState('Not yet scanned')

  const askForCameraPermission = () => {
    (async () => {
      const { status } = await BarCodeScanner.requestPermissionsAsync();
      setHasPermission(status === 'granted');
    })()
  }

  // Request Camera Permission
  useEffect(() => {
    askForCameraPermission();
  }, []);

  // What happens when we scan the bar code
  const handleBarCodeScanned = ({ type, data }) => {
    setScanned(true);
    setText(data)
    console.log('Type: ' + type + '\nData: ' + data)
  };

  // // Check permissions and return the screens
  // if (hasPermission === null) {
  //   return (
  //     <View style={styles.container}>
  //       <Text>Requesting for camera permission</Text>
  //     </View>)
  // }
  // if (hasPermission === false) {
  //   return (
  //     <View style={styles.container}>
  //       <Text style={{ margin: 10 }}>No access to camera</Text>
  //       <Button title={'Allow Camera'} onPress={() => askForCameraPermission()} />
  //     </View>)
  // }

  // Return the View
  const{navigation,route}=props
    const{navigate,goBack}= navigation
  return (
    <View style={styles.container}>
            <UIHeader title={'Teams'}
         leftIconName={'arrow-left'}
       // rightIconName={'qrcode'}
         onPressLeftIcon= {()=>{
            goBack()
         }}
        //  onPressRightIcon= {()=>{
        //     navigate('Scanner')
        //  }}
        ></UIHeader>

    <View style={styles.container}>
      <View style={styles.barcodebox}>
        <BarCodeScanner
          onBarCodeScanned={scanned ? undefined : handleBarCodeScanned}
          style={{
            height: 400, width: 400,
            borderRadius: 100
          }} />
      </View>
      <Text style={styles.maintext}>{text}</Text>

      {scanned && <Button title={'Scan again?'} onPress={() => setScanned(false)} color='tomato' />}
    </View>
    </View>

  );
}
export default Scanner;
const styles = StyleSheet.create({
  container: {
    marginTop:40,
    flex: 1,
    backgroundColor: '#fff',
    alignItems: 'center',
    justifyContent: 'center',
  },
  maintext: {

    fontSize: 16,
    margin: 20,
  },
  barcodebox: {

    alignItems: 'center',
    justifyContent: 'center',
    height: 400,
    width: 400,
    overflow: 'hidden',
    borderRadius: 30,
    // backgroundColor: 'tomato'
  }
});