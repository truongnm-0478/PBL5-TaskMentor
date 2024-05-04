package service;

import dto.response.StudentResponse;
import model.ClassRoom;
import model.Student;
import model.StudentClass;
import repository.JoinClassRepository;
import repository.StudentRepository;

import java.util.ArrayList;
import java.util.List;

public class StudentService {
    private final StudentRepository studentRepository = new StudentRepository();
    private final JoinClassRepository joinClassRepository = new JoinClassRepository();

    public List<StudentResponse> getListStudent(String classCode) {
        List<StudentResponse> listStudent = new ArrayList<>();
        List<StudentClass> studentClass = joinClassRepository.getClassesByClassCode(classCode);
        for(StudentClass st : studentClass) {
            Student student = st.getStudent();
            StudentResponse studentResponse = StudentResponse.builder()
                    .studentId(student.getCode())
                    .phone(student.getUser().getPhone())
                    .email(student.getUser().getEmail())
                    .name(student.getUser().getName())
                    .id(st.getId())
                    .username(student.getUser().getUsername())
                    .userId(student.getUser().getId())
                    .build();
            listStudent.add(studentResponse);
        }
        return listStudent;
    }

    public boolean removeStudentToClass(int id) {
        try {
            joinClassRepository.deleteStudentById(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

}
