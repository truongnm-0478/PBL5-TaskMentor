import React from "react";
import { NavigationContainer } from "@react-navigation/native";
import { createNativeStackNavigator } from "@react-navigation/native-stack";
import { Welcome, Login_1, Register } from "../screens";
import {Messenger} from '../screens'
import UITab  from "./UITab"
const Stack = createNativeStackNavigator();

function App_1(props) {
    return (
        <NavigationContainer>
            <Stack.Navigator initialRouteName="Login" screenOptions={{ headerShown: false }}>
            <Stack.Screen name="Login" component={Login_1} />
          
                
                <Stack.Screen name="Welcome" component={Welcome} />
                <Stack.Screen name="UITab" component={UITab} />
                <Stack.Screen name="Messenger" component={Messenger} />
                <Stack.Screen name="Register" component={Register} />
            </Stack.Navigator>
        </NavigationContainer>
    );
}

export default App_1;
