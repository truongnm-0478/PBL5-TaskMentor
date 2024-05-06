import * as React from 'react'
import {createBottomTabNavigator} from '@react-navigation/bottom-tabs'
import { FontSize,color,image } from '../constants'
import { StyleSheet, Image, View } from "react-native";
const Tab = createBottomTabNavigator()
import {Profile,Chat, Notification, Home} from "../screens"
import MyTeams from '../screens/MyTeams/MyTeams';
import Apppointment from '../screens/Appointment/Appointment';
import { Login_1,Register } from '../screens'
import Icon from 'react-native-vector-icons/FontAwesome';
const screenOptions = ({route})=>({
    headerShown :false,
    tabBarActiveTintColor: color.Text_tabbar,
    tabBarInavtiveTintColor: color.inactive,
    tabBarIcon: ({focused , color, size}) =>{
        let screensName = route.name
        let iconName = ""
        if(screensName=="Home"){
            iconName= image.tabbar_home
            iconName = focused ? image.tabbar_home_1:image.tabbar_home ;
        }
        else if(screensName=="Notification"){
            iconName=focused ?image.tabbar_notification_1 : image.tabbar_notification
        }
        else if(screensName=="Chat"){
            iconName=image.tabbar_chat
        }
        else if(screensName=="Profile"){
            iconName=focused ?image.tabbar_profile_1:image.tabbar_profile
        }
        else if(screensName=="MyTeams"){
            iconName=focused ?image.tabbar_myteams_1:image.tabbar_myteams
        }
        else if(screensName=="Apppointment"){
            iconName=focused ?image.tabbar_appointment_1:image.tabbar_appointment
        }
        return <Image size={20} source={iconName}  style={
            { 
                width:size,
                height:size,
                //tintColor: focused? color.pimary: color.inactive 
                tintColor: focused ?'#1677ff': '#4F4F4F'
            }
        }> 
            </Image>
    }
})
function UITab(props){

    return <Tab.Navigator screenOptions={screenOptions}>
        <Tab.Screen name={'Home'} component={Home} />
        <Tab.Screen name={'MyTeams'} component={MyTeams} />
        <Tab.Screen name={'Apppointment'} component={Apppointment} />
        <Tab.Screen name={'Notification'} component={Notification} />
        {/* <Tab.Screen name={'Chat'} component={Chat} /> */}
        <Tab.Screen name={'Profile'} component={Profile} />
       
         </Tab.Navigator>
} export default UITab