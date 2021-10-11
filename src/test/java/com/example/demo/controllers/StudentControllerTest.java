package com.example.demo.controllers;

import com.example.demo.services.StudentService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.*;
import org.springframework.ui.Model;

import static org.mockito.Mockito.*;

/**
 * @author : christiaan.griffioen
 * @since :  21-6-2021, ma
 **/


class StudentControllerTest {

    @Mock
    StudentService studentService;
    @Mock
    Model model;

    @InjectMocks
    StudentController studentController;

    @BeforeEach
    void setUp() {

    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void test1() {
    }

    @Test
    void saveStudent() {
    }

    @Test
    void deleteStudent() {
    }

    @Test
    void updateStudent() {
    }
}