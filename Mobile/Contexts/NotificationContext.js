import React, { createContext, useState, useRef, useEffect } from 'react';
import { Animated } from 'react-native';

export const NotificationContext = createContext();

export const NotificationProvider = ({ children }) => {
    const [showNotification, setShowNotification] = useState(false);
    const notificationOpacity = useRef(new Animated.Value(0)).current;

    useEffect(() => {
        if (showNotification) {
            Animated.timing(notificationOpacity, {
                toValue: 1,
                duration: 500,
                useNativeDriver: true,
            }).start();
        } else {
            Animated.timing(notificationOpacity, {
                toValue: 0,
                duration: 500,
                useNativeDriver: true,
            }).start();
        }
    }, [showNotification]);

    return (
        <NotificationContext.Provider value={{ showNotification, setShowNotification, notificationOpacity }}>
            {children}
        </NotificationContext.Provider>
    );
};
