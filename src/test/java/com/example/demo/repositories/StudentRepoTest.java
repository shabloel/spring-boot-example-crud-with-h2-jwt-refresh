package com.example.demo.repositories;

import com.example.demo.model.Gender;
import com.example.demo.model.Student;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

/**
 * @author : christiaan.griffioen
 * @since :  15-6-2021, di
 **/
@DataJpaTest
class StudentRepoTest {

    Logger logger = LoggerFactory.getLogger(StudentRepoTest.class);

    @Autowired
    private StudentRepo studentRepo;

    Student buildStudent = Student.builder()
            .firstName("Peppa")
            .lastName("Pig")
            .gender(Gender.FEMALE)
            .age(12)
            .email("peppa@gmail.com")
            .build();

    @AfterEach
    void tearDown(){
        studentRepo.deleteAll();
    }

    @Test
    void findStudentByEmail() {
        //given
        studentRepo.save(buildStudent);

        //when
        Student testStudent = studentRepo.findStudentByEmail("peppa@gmail.com").get();

        //then
        logger.info("The retrieved student has id: " + testStudent.getId());
        assertThat(testStudent).isEqualTo(buildStudent);
    }

    @Test
    void notFoundStudentByEmailStudent() {
        //given
        studentRepo.save(buildStudent);
         logger.info("**********" + studentRepo.findAll());

        //when
        Optional<Student> studentOptional = studentRepo.findStudentByEmail("jansen@gmail.com");

        //then
        assertThat(studentOptional).isEmpty();
    }
}