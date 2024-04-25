package service;

import dto.request.ClassRequest;
import dto.request.JoinClassRequest;
import model.*;
import repository.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ClassService {
    private final ClassRepository classRepository = new ClassRepository();
    private final TeacherRepository teacherRepository = new TeacherRepository();
    private final StudentRepository studentRepository = new StudentRepository();
    private final JoinClassRepository joinClassRepository = new JoinClassRepository();

    private final UserRepository userRepository = new UserRepository();

    public ClassRoom saveClass(ClassRequest classRequest, int userId) {
        ClassRoom classRoom = ClassRoom.builder()
                .className(classRequest.getName())
                .teacher(Teacher.builder().id(teacherRepository.getByUserId(userId).getId()).build())
                .code(classRequest.getCode())
                .description(classRequest.getDescription())
                .year(classRequest.getYear())
                .insertTime(new java.sql.Timestamp(new Date().getTime()))
                .insertBy(userId)
                .build();
        return classRepository.save(classRoom);
    }

    public List<ClassRequest> listClassForTeacher(int userId) {
        List<ClassRoom> classRooms = classRepository.findByUserId(userId);
        List<ClassRequest> classRequests = new ArrayList<>();
        for (ClassRoom classRoom : classRooms) {
            ClassRequest classRequest = ClassRequest.builder()
                    .name(classRoom.getClassName())
                    .code(classRoom.getCode())
                    .year(classRoom.getYear())
                    .description(classRoom.getDescription())
                    .build();
            classRequests.add(classRequest);
        }
        return classRequests;
    }

    public List<ClassRequest> listClassForStudent(int userId) {
        Student student = studentRepository.getStudentByUserId(userId);
        List<StudentClass> studentClasses = joinClassRepository.getClassesByStudentId(student.getId());
        List<ClassRequest> classRequests = new ArrayList<>();
        for(StudentClass studentClass : studentClasses) {
            ClassRoom classRoom = studentClass.getClassRoom();
            ClassRequest classRequest = ClassRequest.builder()
                    .name(classRoom.getClassName())
                    .code(classRoom.getCode())
                    .year(classRoom.getYear())
                    .description(classRoom.getDescription())
                    .build();
            classRequests.add(classRequest);
        }
        return classRequests;
    }

    public ClassRoom updateClass(ClassRequest classRequest, int userId) {
        ClassRoom classRoom = classRepository.findByCode(classRequest.getCode());
        classRoom.setDescription(classRequest.getDescription());
        classRoom.setClassName(classRequest.getName());
        classRoom.setUpdateTime(new java.sql.Timestamp(new Date().getTime()));
        classRoom.setUpdateBy(userId);
        return classRepository.update(classRoom);
    }

    public void deleteClass(String code, int userId) {
        ClassRoom classRoom = classRepository.findByCode(code);
        classRoom.setDeleteBy(userId);
        classRoom.setDeleteTime(new java.sql.Timestamp(new Date().getTime()));
        classRepository.update(classRoom);
    }

    public boolean isJoinedClass(Student student) {
        return joinClassRepository.isStudentInClass(student);
    }

    public boolean joinClass(JoinClassRequest joinClassRequest, int userId) {
        ClassRoom classRoom = classRepository.findByCode(joinClassRequest.getClassCode());

        if(classRepository.findByCode(joinClassRequest.getClassCode()) == null) {
            return false;
        }

        if(studentRepository.isCodeExists(joinClassRequest.getStudentId())) {
            if(studentRepository.isCodeAndUserIdExists(userId, joinClassRequest.getStudentId())) {
                Student student = studentRepository.getStudentByCodeAndUserId(userId, joinClassRequest.getStudentId());

                if(isJoinedClass(student)) {
                    return false;
                }

                StudentClass studentClass = StudentClass.builder()
                        .student(student)
                        .classRoom(classRoom)
                        .build();
                joinClassRepository.addStudentToClass(studentClass);
                return true;
            } else {
                return false;
            }

        } else {
            if(studentRepository.isUserIdExists(userId)) {
                return false;
            } else {
                User user = userRepository.getUserById(userId);
                Student student = Student.builder()
                        .user(user)
                        .code(joinClassRequest.getStudentId())
                        .insertTime(new java.sql.Timestamp(new Date().getTime()))
                        .insertBy(userId)
                        .build();
                studentRepository.insertStudent(student);
                StudentClass studentClass = StudentClass.builder()
                        .student(student)
                        .classRoom(classRoom)
                        .build();
                joinClassRepository.addStudentToClass(studentClass);
                return true;
            }
        }
    }
}
