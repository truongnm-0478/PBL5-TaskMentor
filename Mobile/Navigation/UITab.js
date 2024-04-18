import * as React from 'react'
import {createBottomTabNavigator} from '@react-navigation/bottom-tabs'
import { FontSize,color,image } from '../constants'
const Tab = createBottomTabNavigator()
import {Profile,Chat} from "../screens"
import { Login_1,Register } from '../screens'
import Icon from 'react-native-vector-icons/FontAwesome';
const screenOptions = ({route})=>({
    headerShown :false,
    tabBarActiveTintColor: color.pimary,
    tabBarInavtiveTintColor: color.inactive,
    tabBarIcon: ({focused , color, size}) =>{
        let screensName = route.name
        let iconName = ""
        if(screensName=="Login"){
            iconName='facebook'
        }
        else if(screensName=="Register"){
            iconName='facebook'
        }
        else if(screensName=="Chat"){
            iconName='facebook'
        }
        else if(screensName=="Profile"){
            iconName='facebook'
        }
        return <Icon name={iconName} size={20} color={focused? color.pimary: color.inactive}>
            
        </Icon>
    }
})
function UITab(props){

    return <Tab.Navigator screenOptions={screenOptions}>
        <Tab.Screen name={'Login'} component={Login_1} />
        <Tab.Screen name={'Register'} component={Register} />
        <Tab.Screen name={'Chat'} component={Chat} />
        <Tab.Screen name={'Profile'} component={Profile} />
         </Tab.Navigator>
} export default UITab