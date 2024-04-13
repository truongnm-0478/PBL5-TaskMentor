// auth.js

import AsyncStorage from '@react-native-async-storage/async-storage';

const storeToken = async (accessToken, refreshToken) => {
    try {
        await AsyncStorage.setItem('accessToken', accessToken);
        await AsyncStorage.setItem('refreshToken', refreshToken);
    } catch (error) {
        console.error('Error storing tokens:', error);
    }
};

const getToken = async () => {
    try {
        const accessToken = await AsyncStorage.getItem('accessToken');
        const refreshToken = await AsyncStorage.getItem('refreshToken');
        return { accessToken, refreshToken };
    } catch (error) {
        console.error('Error getting tokens:', error);
    }
};

const clearToken = async () => {
    try {
        await AsyncStorage.removeItem('accessToken');
        await AsyncStorage.removeItem('refreshToken');
    } catch (error) {
        console.error('Error clearing tokens:', error);
    }
};

export { storeToken, getToken, clearToken };
