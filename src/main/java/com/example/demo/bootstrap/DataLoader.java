package com.example.demo.bootstrap;

import com.example.demo.model.Gender;
import com.example.demo.model.Student;
import com.example.demo.repositories.StudentRepo;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * @author : christiaan.griffioen
 * @since :  27-6-2021, zo
 **/
/*@Component
public class DataLoader implements ApplicationRunner {
    private StudentRepo studentRepo;

    @Autowired
    public DataLoader(StudentRepo studentRepo) {
        this.studentRepo = studentRepo;
    }

    public void run(ApplicationArguments args){
        studentRepo.save(new Student("Jan", "Jansen", Gender.MALE, 12, "abc@gmail.com"));
    }
}*/
