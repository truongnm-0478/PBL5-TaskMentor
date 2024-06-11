import React, { useState ,useEffect} from 'react';
import { View, TextInput, Button, FlatList, Text, StyleSheet ,Switch,TouchableOpacity,Alert} from 'react-native';
import AsyncStorage from '@react-native-async-storage/async-storage';
import { Dropdown } from 'react-native-element-dropdown';
import { image,icons,color, FontSize } from "../../constants";
import { _class,teams } from "../../repositories";
import { UIHeader } from '../../components'


const styles = StyleSheet.create({
  container: {
    //marginTop:40,
    flex: 1,
    padding: 20,
    backgroundColor:'white'
  },
  input: {
    borderWidth: 1,
    borderColor: color.border, // Màu sắc của đường viền
    borderRadius: 5, // Độ cong của góc (nếu cần)
    color: color.placeholder,
    height: 60,
    paddingHorizontal: 15,
    marginBottom:20,
  },
  memberInputContainer: {
    flexDirection: 'row',
    marginBottom: 10,
    alignItems:'center',
  
  },
  memberInput: {
    borderWidth: 1,
    borderColor: color.border, // Màu sắc của đường viền
    borderRadius: 5, // Độ cong của góc (nếu cần)
    color: color.placeholder,
    height: 60,
    paddingHorizontal: 15,
   
    width:250,
  },
  listContainer: {
    flex: 1,
    marginTop: 20,
  },
  listItem: {
    padding: 10,
    marginTop: 10,
   // backgroundColor: '#f8f8f8',
    borderWidth: 1,
    borderColor: '#eee',
    flexDirection: 'row',
    justifyContent: 'space-between',
    alignItems: 'center',
  },
  dropdown: {
  
    height: 50,
    //backgroundColor: 'white',
    borderRadius: 5,
    padding: 12,
    borderColor: color.border,
    // shadowColor: '#000',
    width:250,
    borderWidth:1,
    height: 60,
    color: color.placeholder,
    // shadowOffset: {
    //   width: 0,
    //   height: 1,
    // },
    // shadowOpacity: 0.2,
    // shadowRadius: 1.41,
    // width:250,
    // elevation: 2,
  },
  icon: {
    marginRight: 5,
  },
  item: {
    padding: 17,
    flexDirection: 'row',
    justifyContent: 'space-between',
    alignItems: 'center',
  },
  textItem: {
    flex: 1,
    fontSize: 16,
  },
  placeholderStyle: {
    fontSize: 16,
  },
  selectedTextStyle: {
    fontSize: 16,
  },
  iconStyle: {
    width: 20,
    height: 20,
  },
  inputSearchStyle: {
    height: 40,
    fontSize: 16,
  },
});

const AddTeams = ({route,navigation}) => {
   const { code, students} = route.params;
   const { navigate, goBack } = navigation

  const [teamName, setTeamName] = useState('');
  const [members, setMembers] = useState([]);
  const [memberInput, setMemberInput] = useState('');
  const [memberRole, setMemberRole] = useState(false); // State for member role
  const [selectedStudent, setSelectedStudent] = useState(null);
  const [value, setValue] = useState(null);
  const [isFocus, setIsFocus] = useState(false);
  const studentOptions = students.map(student => ({
    label: student.name,
    value: student.studentId,
}));
const renderItem = item => {
  return (
    <View style={styles.item}>
      <Text style={styles.textItem}>{item.label}</Text>
     
    </View>
  );
};
  const addMemberHandler = () => {
    const newMember = { studentId: memberInput, leader: memberRole }; // Include role in member object
    setMembers(currentMembers => [...currentMembers, newMember]);
    setMemberInput('');
    setMemberRole(false); // Reset role state after adding member
  };

  const toggleRoleHandler = (index) => {
    const updatedMembers = [...members];
    updatedMembers[index].role = !updatedMembers[index].role;
    setMembers(updatedMembers);
  };

  const addTeamHandler =async  () => {
    // Logic to add the team
    // console.log({code})
    // console.log('Team Name:', teamName);
    // console.log('Members:', members);
    console.log(students)
    // Reset the states
    setTeamName('');
    setMembers([]);
    try {
      const response = await create();
      if (response) {
        Alert.alert(
          "Success",
          "Team has been added successfully",
          [
            { text: "OK", onPress: () => console.log("OK Pressed") }
          ],
          { cancelable: false }
        );
      }
    } catch (error) {
      console.log(error);
 
    }
  };
  
  const create = async () => {
    try {
      const accessToken = await AsyncStorage.getItem('accessToken');
    
      const response = await teams.createTeam(teamName , code, members,accessToken)
       return response
    } catch (error) {
      console.error("Lỗi trong phương thức create:", error);
    } 
}

  return (
    
    <View style={styles.container}>
   <UIHeader title={'Create Team'}
            
            leftIconName={image.back}
            onPressLeftIcon={() => {
              navigation.goBack();
            }}
            
           
        ></UIHeader>
      <TextInput
        style={styles.input}
        placeholder="Enter Team Name"
        value={teamName}
        onChangeText={setTeamName}
      />
      <View style={styles.memberInputContainer}>
        {/* <TextInput
          style={styles.memberInput}
          placeholder="Enter Member Name"
          value={memberInput}
          onChangeText={setMemberInput}
        /> */}
       
       <Dropdown
        style={styles.dropdown}
        placeholderStyle={styles.placeholderStyle}
        selectedTextStyle={styles.selectedTextStyle}
        inputSearchStyle={styles.inputSearchStyle}
        iconStyle={styles.iconStyle}
        data={studentOptions}
        search
        maxHeight={300}
        labelField="label"
        valueField="value"
        placeholder="Select student"
        searchPlaceholder="Search..."
        value={value}
        onChange={item => {
          setValue(item.value);
          setMemberInput(item.value);
        }}
        
        renderItem={renderItem}
      />
        <Text style={{
          paddingLeft:10,
          flex:1,
        }}>
          Leader
        </Text>
        <Switch
          value={memberRole}
          onValueChange={() => setMemberRole(previousState => !previousState)}
        />
        
      </View>
      <TouchableOpacity style={{ justifyContent:'center',
          alignItems:'center',
          backgroundColor:color.BGlogin,
          paddingHorizontal:20,
          borderRadius:5,
          height:60,
          
        }} onPress={addMemberHandler}>
          <Text style={{}}>Add Member</Text>
        </TouchableOpacity>
      <FlatList
        style={styles.listContainer}
        data={members}
        keyExtractor={(item, index) => index.toString()}
        renderItem={({ item, index }) => (
          <View style={styles.listItem}>
            <Text>{item.studentId}</Text>
            <Switch
              value={item.leader}
              onValueChange={() => toggleRoleHandler(index)}
            />
          </View>
        )}
      />
   
      <TouchableOpacity style={{justifyContent:'center',
          alignItems:'center',
          backgroundColor:color.BGlogin,
          paddingHorizontal:20,
          borderRadius:5,
          height:60,
          }} onPress={addTeamHandler} disabled={!teamName || members.length === 0}>
  <Text style={styles.addButtonText}>Add Team</Text>
</TouchableOpacity>
    </View>
  );
};
export default AddTeams;