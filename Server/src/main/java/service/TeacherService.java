package service;

import com.fasterxml.jackson.core.JsonProcessingException;
import dto.request.UserUpdateRequest;
import dto.response.UserInfoResponse;
import dto.response.UserResponse;
import model.Teacher;
import model.User;
import repository.TeacherRepository;
import repository.UserRepository;

import java.util.Date;
import java.util.List;

import util.PasswordHashingUtil;
import util.UserValidationUtil;

public class TeacherService {
    private final UserRepository userRepository = new UserRepository();
    private final TeacherRepository teacherRepository = new TeacherRepository();

    public List<Teacher> getListTeacher() {
        return teacherRepository.getAllTeachers();
    }
}
