package com.example.demo.services;

import com.example.demo.controllers.StudentController;
import com.example.demo.dto.StudentDto;
import com.example.demo.exception.BadRequestException;
import com.example.demo.exception.StudentNotFoundException;
import com.example.demo.mapper.SourceTargetMapper;
import com.example.demo.model.Student;
import com.example.demo.repositories.StudentRepo;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 * @author : christiaan.griffioen
 * @since :  15-6-2021, di
 **/

@Service
public class  StudentServiceImpl implements StudentService {

    private final StudentRepo studentRepo;
    private final SourceTargetMapper mapper;

    @Autowired
    public StudentServiceImpl(final StudentRepo studentRepo, final SourceTargetMapper mapper) {
        this.studentRepo = studentRepo;
        this.mapper = mapper;
    }

    @Override
    public List<StudentDto> getStudents() {
        return studentRepo.findAll().stream()
                .map(mapper::studentToStudentDto)
                .collect(Collectors.toList());
    }

    @Override
    public Student getStudentById(Long id) {
        boolean exists = studentRepo.existsById(id);
        if(!exists){
            throw new StudentNotFoundException("student with id " + id + "is not present in DB");
        }
        return studentRepo.getById(id);
    }


    @Override
    public void addNewStudent(Student student) {
        Optional<Student> studentOptional = studentRepo.findStudentByEmail(student.getEmail());
        if (studentOptional.isPresent()) {
            throw new BadRequestException("email taken");
        }
        studentRepo.save(student);
    }

    @Override
    public void deleteStudent(Long id) {
        boolean exists = studentRepo.existsById(id);
        if (!exists) {
            throw new StudentNotFoundException("student with id " + id + "is not present in DB");
        }
        studentRepo.deleteById(id);
    }

    @Transactional//with this annotation you can use the setters and dont have to call the repo
    public void updateStudent(Long id, String name, String email) {
        Student student = studentRepo.findById(id)
                .orElseThrow(() -> new StudentNotFoundException("student with id " + id + "is not present in DB"));
        if (name != null && name.length() > 0 && !Objects.equals(student.getFirstName(), name)) {
            student.setFirstName(name);
        }

        if (email != null && email.length() > 0 && !Objects.equals(student.getEmail(), email)) {
            Optional<Student> studentOptional = studentRepo
                    .findStudentByEmail(email);
            if (studentOptional.isPresent()) {
                throw new BadRequestException("Email taken");
            }
            student.setEmail(email);
        }
    }
}
