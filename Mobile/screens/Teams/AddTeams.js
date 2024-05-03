import React, { useState } from 'react';
import { View, TextInput, Button, FlatList, Text, StyleSheet ,Switch} from 'react-native';
import { teams } from '../../repositories';
import AsyncStorage from '@react-native-async-storage/async-storage';
const styles = StyleSheet.create({
  container: {
    marginTop:40,
    flex: 1,
    padding: 20,
  },
  input: {
    borderWidth: 1,
    borderColor: 'gray',
    padding: 10,
    marginBottom: 10,
  },
  memberInputContainer: {
    flexDirection: 'row',
    marginBottom: 10,
  },
  memberInput: {
    flex: 1,
    borderWidth: 1,
    borderColor: 'gray',
    padding: 10,
  },
  listContainer: {
    flex: 1,
    marginTop: 20,
  },
  listItem: {
    padding: 10,
    marginTop: 10,
    backgroundColor: '#f8f8f8',
    borderWidth: 1,
    borderColor: '#eee',
    flexDirection: 'row',
    justifyContent: 'space-between',
    alignItems: 'center',
  },
});

const AddTeams = ({route}) => {
   const { code } = route.params;
  const [teamName, setTeamName] = useState('');
  const [members, setMembers] = useState([]);
  const [memberInput, setMemberInput] = useState('');
  const [memberRole, setMemberRole] = useState(false); // State for member role

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

  const addTeamHandler = () => {
    // Logic to add the team
    // console.log({code})
    // console.log('Team Name:', teamName);
    // console.log('Members:', members);
    // Reset the states
    setTeamName('');
    setMembers([]);
    create()
  };
  const create = async () => {
    try {
      const accessToken = await AsyncStorage.getItem('accessToken');
       const response = await teams.createTeam(teamName , code, members,accessToken)

        

    } catch (error) {
       console.log(error)
    } 
}

  return (
    <View style={styles.container}>
      <TextInput
        style={styles.input}
        placeholder="Enter Team Name"
        value={teamName}
        onChangeText={setTeamName}
      />
      <View style={styles.memberInputContainer}>
        <TextInput
          style={styles.memberInput}
          placeholder="Enter Member Name"
          value={memberInput}
          onChangeText={setMemberInput}
        />
        <Switch
          value={memberRole}
          onValueChange={() => setMemberRole(previousState => !previousState)}
        />
        <Button title="Add Member" onPress={addMemberHandler} />
      </View>
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
      <Button title="Add Team" onPress={addTeamHandler} disabled={!teamName || members.length === 0} />
    </View>
  );
};

export default AddTeams;