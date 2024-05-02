import React, { useState } from 'react';
import { View, TextInput, Button, FlatList, Text, StyleSheet } from 'react-native';

const styles = StyleSheet.create({
  container: {
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
  },
});

const AddTeams = () => {
  const [teamName, setTeamName] = useState('');
  const [members, setMembers] = useState([]);
  const [memberInput, setMemberInput] = useState('');

  const addMemberHandler = () => {
    setMembers(currentMembers => [...currentMembers, memberInput]);
    setMemberInput('');
  };

  const addTeamHandler = () => {
    // Logic to add the team
    console.log('Team Name:', teamName);
    console.log('Members:', members);
    // Reset the states
    setTeamName('');
    setMembers([]);
  };

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
        <Button title="Add Member" onPress={addMemberHandler} />
      </View>
      <FlatList
        style={styles.listContainer}
        data={members}
        keyExtractor={(item, index) => index.toString()}
        renderItem={({ item }) => (
          <View style={styles.listItem}>
            <Text>{item}</Text>
          </View>
        )}
      />
      <Button title="Add Team" onPress={addTeamHandler} disabled={!teamName || members.length === 0} />
    </View>
  );
};

export default AddTeams;
