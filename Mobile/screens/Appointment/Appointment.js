import React ,{useState,useEffect} from "react";
import AsyncStorage from '@react-native-async-storage/async-storage';
import { Text, View , Image, ImageBackground, TouchableOpacity, TextInput, KeyboardAvoidingView,Keyboard, FlatList, VirtualizedList, Button, StyleSheet, ScrollView , SafeAreaView} from "react-native";
import { image,icons,color, FontSize } from "../../constants";
import Icon from 'react-native-vector-icons/FontAwesome';
import{isValidEmail,isValidPassword} from "../../utilies/Validations"
import { Calendar } from 'react-native-big-calendar'
import { appointment } from "../../repositories";
import {Agenda} from 'react-native-calendars';
function Apppointment(props){
  const [Appointment, setAppointment]= useState([{}])
  const [isDataReady, setIsDataReady] = useState(false);
  useEffect(() => {
    const fetchData = async () => {
        try {
        
            const accessToken = await AsyncStorage.getItem('accessToken');
            const data = await appointment.getAppointment(accessToken);
            setAppointment(data)
            setIsDataReady(true);
        } catch (error) {
          //  console.error("Error fetching student data:", error);
        }
    };
    fetchData();
}, [Appointment]);
const convertAppointmentsToEvents = (appointments) => {
  const event = {};
  appointments.forEach((appointment) => {
      // Chuyển đổi định dạng ngày
      // console.log(appointment.start)
      const startDate = new Date(appointment.start);
      // console.log(startDate)
      const formattedDate = startDate.toISOString().split('T')[0];
      
      // Tạo key cho mỗi ngày nếu nó chưa tồn tại trong events
      if (!event[formattedDate]) {
          event[formattedDate] = [];
      }
      
      // Thêm mục vào danh sách các sự kiện của ngày đó
      event[formattedDate].push({ name: appointment.title, data: appointment.location });
  });
  return event;
};

  const events = {
    '2024-05-01': [{ name: 'Meeting 1', data: 'Thông đồ án tại phòng 206' }],
    '2024-05-09': [{ name: 'Meeting 1', data: 'Thông đồ án tại phòng 206' }],
    '2024-05-08': [{ name: 'Meeting 1', data: 'Thông đồ án tại phòng 206' }],
    '2024-05-07': [{ name: 'Meeting 1', data: 'Thông đồ án tại phòng 206' }],
    '2024-05-06': [{ name: 'Meeting 1', data: 'Thông đồ án tại phòng 206' }],
    '2024-05-02': [{ name: 'Meeting 1', data: 'Thông đồ án tại phòng 206' }],
    '2024-05-03': [{ name: 'Meeting 1', data: 'Thông đồ án tại phòng 206' }],
    '2024-05-04': [{ name: 'Meeting 1', data: 'Thông đồ án tại phòng 206' }],
    '2024-05-05': [{ name: 'Meeting 1', data: 'Thông đồ án tại phòng 206' }],
    '2024-05-10': [{ name: 'Meeting 1', data: 'Thông đồ án tại phòng 206' }],
  };
  const renderItem = (item) => (
    <TouchableOpacity style={{
      marginTop:40,
      marginBottom:5,
    }}>
      <Text>{item.name}</Text>
      <Text>{item.data}</Text>
    </TouchableOpacity>
  );
    const { navigation } = props;
    const { navigate, goBack } = navigation;
    if (!isDataReady) {
      // Hiển thị màn hình loading hoặc các yếu tố tương tự trong quá trình lấy dữ liệu
      return (
        <View style={{ flex: 1, justifyContent: 'center', alignItems: 'center' }}>
          <Text>Loading...</Text>
        </View>
      );
    }
  
 return (  <View style={{ flex: 1 ,
  backgroundColor:color.BackGround
 }}>
  <Agenda  style={{ marginTop:40,}}
    items={convertAppointmentsToEvents(Appointment)}
    renderItem={renderItem}
    // Các thuộc tính khác của Agenda (minDate, maxDate, selected, onDayPress, ...)
  />
</View>
 );
} export default Apppointment
