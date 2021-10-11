package com.example.demo.repositories;

import com.example.demo.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author : christiaan.griffioen
 * @since :  15-6-2021, di
 **/
@Repository
public interface StudentRepo extends JpaRepository<Student, Long> {

    Optional<Student> findStudentByEmail(String email); //select * from student where email = email

    //alternative
/*    @Query("Select s FROM Student s WHERE s.email = ?")
    Optional<Student> findStudentByEmail(String email);*/
}
