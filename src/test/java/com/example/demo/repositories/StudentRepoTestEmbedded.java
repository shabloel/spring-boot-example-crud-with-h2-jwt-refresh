package com.example.demo.repositories;

import com.example.demo.model.Gender;
import com.example.demo.model.Student;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

/**
 * @author : christiaan.griffioen
 * @since :  28-6-2021, ma
 **/
@DataJpaTest
public class StudentRepoTestEmbedded {

    Logger logger = LoggerFactory.getLogger(StudentRepoTestEmbedded.class);

    Student testStudent = Student.builder()
                            .firstName("Peppa")
                            .lastName("Pig")
                            .gender(Gender.FEMALE)
                            .age(12)
                            .email("peppa@gmail.com")
                            .build();

    @Autowired
    private StudentRepo studentRepo;

    @Test
    public void shouldSaveStudent(){
        Student savedStudent = studentRepo.save(testStudent);
        assertThat(savedStudent).usingRecursiveComparison().ignoringFields("id").isEqualTo(testStudent);
    }

    @Test
    @Sql("classpath:test-data.sql")
    public void shouldSaveUsersThroughSqlFile() {
        Optional<Student> test = studentRepo.findStudentByEmail("pig@yahoo.com");
        logger.info("Retrieved student: " + test.get().getFirstName());
        Assertions.assertThat(test).isNotEmpty();
    }
}
