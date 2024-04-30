import React from "react";
import { NavigationContainer } from "@react-navigation/native";
import { createNativeStackNavigator } from "@react-navigation/native-stack";
import { Welcome, Login_1, Register, Home, S, Scanner } from "../screens";
import ChangePassword from "../screens/Menu/ChangePassword";
import UpdateProfile from "../screens/Menu/UpdateProfile";
import About from '../screens/Menu/About'
import Class from "../screens/Class/Class";
import { Messenger } from '../screens'
import UITab from "./UITab"
const Stack = createNativeStackNavigator();

function App_1(props) {
    return (
        <NavigationContainer>
            <Stack.Navigator initialRouteName="Login" screenOptions={{ headerShown: false }}>
                <Stack.Screen name="Login" component={Login_1} />
                <Stack.Screen name="Scanner" component={Scanner} />
                <Stack.Screen name="Home" component={Home} />
                <Stack.Screen name="Welcome" component={Welcome} />
                <Stack.Screen name="UITab" component={UITab} />
                <Stack.Screen name="Messenger" component={Messenger} />
                <Stack.Screen name="Register" component={Register} />
                <Stack.Screen name="Class" component={Class} />
                <Stack.Screen name="ChangePassword" component={ChangePassword} />
                <Stack.Screen name="UpdateProfile" component={UpdateProfile} />
                <Stack.Screen name="About" component={About} />
            </Stack.Navigator>
        </NavigationContainer>
    );
}

export default App_1;
