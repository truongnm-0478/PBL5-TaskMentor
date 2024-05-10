import React, { useState, useEffect } from "react";
import { Text, View, Image, ImageBackground, TouchableOpacity, TextInput, KeyboardAvoidingView, Keyboard, FlatList, VirtualizedList, Button, StyleSheet } from "react-native";
import { image, icons, color, FontSize } from "../../constants";
import Icon from 'react-native-vector-icons/FontAwesome';
import { isValidEmail, isValidPassword } from "../../utilies/Validations"
import AsyncStorage from '@react-native-async-storage/async-storage';
import { UIHeader } from '../../components'
import { BarCodeScanner } from 'expo-barcode-scanner'
import { _class } from "../../repositories";
function Scanner(props) {
  const [hasPermission, setHasPermission] = useState(null);
  const [scanned, setScanned] = useState(false);
  const [text, setText] = useState('Not yet scanned')
  const [studentId, setStudentId] = useState('')
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
  const { navigation, route } = props
  const { navigate, goBack } = navigation
  const handleJoinclass = async () => {
    try {
       const accessToken = await AsyncStorage.getItem('accessToken');
       const response = await _class.JoinClass(accessToken, studentId , text)

        navigate('UITab')

    } catch (error) {
       console.log(error)
    } 
}
  return (
   <View style={{
    flex:1,
    backgroundColor:color.BackGround
   }}>
    <View style={{
      marginTop:40,
     
    }}>
       <UIHeader title={'Classes'}
         leftIconName={image.back}
        //JoinIcon={'plus-square'}
        // rightIconName={'qrcode'}
         onPressLeftIcon= {()=>{
           goBack()
         }}
      //   scanner = {image.scanner}
      //    onPressRightIcon= {()=>{
      //        navigate('Scanner')
      //    }}
        
      //    onPressJoinicon= {()=>{
      //     navigate('JoinTeam')
      //  }}
        ></UIHeader>
    <View style={styles.container}>
      

      <View style={styles.container}>
        <View style={styles.barcodebox}>
          <BarCodeScanner
            onBarCodeScanned={scanned ? undefined : handleBarCodeScanned}
            style={{

              height: 400, width: 400,
              borderRadius: 100
            }} />
        </View>
        <View style={{
          marginTop: 5, 
          justifyContent: 'center',
          alignItems: 'center',
          height:30,
        }}>
          <Text>{text}</Text>
        </View>

      
        <TextInput onChangeText={(Text) => {
          setStudentId(Text)
        }
        } style={{
          marginTop: 20,
          borderWidth: 1,
          borderColor: color.border, // Màu sắc của đường viền
          borderRadius: 5, // Độ cong của góc (nếu cần) 
          color: color.placeholder,
          paddingHorizontal: 15,
          height: 40,
          color: color.placeholder,
          width:200
        }} placeholder="Enter your student id" />
          {scanned && <TouchableOpacity onPress={() => setScanned(false)} style={{ backgroundColor: 'tomato', padding: 10, borderRadius: 5, marginTop: 20, height: 40, width:200, 
            alignItems:"center",
           }}>
          <Text style={{ color: 'white' }}>Scan again?</Text>
        </TouchableOpacity>
        }
      <TouchableOpacity 
            
            onPress={() => {handleJoinclass()}} style={{
              marginTop:20,
                backgroundColor: color.BGlogin,
                justifyContent: "center",
                alignItems: "center",
                width: 200,
                borderRadius: 10,
                alignSelf: "center",
                height:40
            }}>
                <Text style={{
                    padding: 10,
                    color: 'white'
                }}>Join class</Text>
            </TouchableOpacity>
      </View>

    </View>
    </View>
    </View>
  );
}
export default Scanner;
const styles = StyleSheet.create({
  container: {
    marginTop: 20,
    BackGround:'white',
    flex: 1,
    alignItems: 'center',
  },
  maintext: {

    fontSize: 16,
    marginTop: 20,
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