package com.example.demo.services;

import com.example.demo.dto.StudentDto;
import com.example.demo.model.Student;

import java.util.List;

/**
 * @author : christiaan.griffioen
 * @since :  15-6-2021, di
 **/
public interface StudentService {
    List<StudentDto> getStudents();
    Student getStudentById(Long id);
    void addNewStudent(Student student);
    void deleteStudent(Long id);
    void updateStudent(Long id, String name, String email);
}
