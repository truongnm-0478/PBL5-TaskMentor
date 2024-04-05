import React ,{useState,useEffect} from "react";
import { Text, View , Image, ImageBackground, TouchableOpacity, TextInput, KeyboardAvoidingView,Keyboard} from "react-native";
import { image,icons,color, FontSize } from "../constants";
import Icon from 'react-native-vector-icons/FontAwesome';
import{isValidEmail,isValidPassword} from "../utilies/Validations"

function Login(props){
  const [KeyboardDisshown, setKeyboardDisshown]  = useState(false)
  const [Email, setEmail] = useState('')
  const [Password, setPassword]= useState('')
  const[errorEmail, seterorrEmail]= useState('')
  const[errorPassword, seterorrPassword]=useState('')
  const isVlidateOK=()=>{
    if(Email.length>0 && Password.length>0 && isValidEmail(Email)==true && isValidPassword(Password)==true) return true
    else return false
  }
  useEffect(()=>{
    Keyboard.addListener('keyboardDidShow',()=>{setKeyboardDisshown(true)})
    Keyboard.addListener('keyboardDidHide',()=>{setKeyboardDisshown(false)})
  })
    return <KeyboardAvoidingView 
    style={{marginTop: 40,
    flex:100,
    backgroundColor:'white'}}>
      <View style={{
        height:200,
        flexDirection:'row',
        flex: 30,
        alignItems:'center',
        justifyContent:'space-around'
      }}>
      <Text style={{
        color: 'black',
        fontSize: FontSize.h2,
        fontWeight:'bold',
        width:'50%'
      }}> Already have an account</Text>
      <Image style={{
        width:100,
        height:100,
        alignSelf:"center"
      }} source={
        image.telegram
      } />
      </View>
        <View style={{
            flex:30
        }}>
        <View style={{
                 marginHorizontal: 15,
        }}>
            <Text style={{
                fontSize: FontSize.h6
            }}>Email</Text>
            <TextInput onChangeText={(Text)=>{
             seterorrEmail(isValidEmail(Text)==true?"":"Email not in correct format")
              setEmail(Text)}} style={{
                 marginBottom:10,
                color: color.placeholder,
            }} placeholder="example@email.com" />
             <View style={{
                height:1,
                backgroundColor:'black'
            }}></View>
            <Text style={{color: 'red',
          fontSize: FontSize.h6
          }}>{errorEmail}</Text>
        </View>
        <View style={{
                 marginHorizontal: 15,
        }}>
            <Text style={{
                marginTop:5,
                fontSize: FontSize.h6
            }}>Password</Text>
            <TextInput onChangeText={(Text)=>{
              seterorrPassword(isValidPassword(Text)==true?"":"Password not in correct format")
              setPassword(Text)
            }
            } style={{
                
               marginBottom:10,
                color: color.placeholder,
            }} secureTextEntry={true} placeholder="Enter your password" />
            <View style={{
               
                height:1,
                backgroundColor:'black'
            }}></View>
            <Text style={{
              color:'red',
              fontSize: FontSize.h6
            }}>{errorPassword}</Text>
        </View>
        </View>
        { KeyboardDisshown == false && <View style={{
            flex: 15
        }}>
            <TouchableOpacity 
            disabled = {isVlidateOK()==false}
            onPress={() => { alert('Email=' + Email + 'Password=' + Password) }} style={{
                backgroundColor: isVlidateOK()==true?'#0099CC':color.inactive,
                justifyContent: "center",
                alignItems: "center",
                width: '60%',
                borderRadius: 20,
                alignSelf: "center"
            }}>
                <Text style={{
                    padding: 10,
                    fontSize: FontSize.h5,
                    color: 'white'
                }}>Login</Text>
            </TouchableOpacity> 
            <TouchableOpacity onPress={() => { alert('new account?') }} style={{
                alignSelf: "center",
                padding: 5
            }}>
                <Text style={{

                    padding: 10,
                    fontSize: FontSize.h5,
                    color: 'aqua'
                }}>New user? Register now</Text>
            </TouchableOpacity>
        </View>}
        <View style={{
            flex:25,
           
        }}>
             
          <View style={{
            flexDirection:'row',
            height:40,
            alignItems:"center",
            marginHorizontal:20
          }}>
             <View style={{
                backgroundColor:'black',
                height:1,
                flex:1
             }}></View>
               
                    <Text style={{
                        marginHorizontal:10,
                        padding: 10,
                        fontSize: FontSize.h5,
                        color: 'aqua'
                    }}>User orther method?</Text>
              
                <View style={{
                backgroundColor:'black',
                height:1,
                flex:1
             }}></View>
            </View> 
          <View style={{
            flexDirection:'row',
           alignItems:"center",
           justifyContent:"center"
          }}>
          <Icon name="facebook" size={35} color={color.facebook}></Icon>
          <View style={{
            width:25
          }}></View>
        <Icon name='google' size={35} color={color.google}></Icon>
          </View>
        </View>
    </KeyboardAvoidingView>
}
export default Login