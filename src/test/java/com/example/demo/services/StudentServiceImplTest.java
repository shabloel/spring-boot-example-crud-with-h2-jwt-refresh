package com.example.demo.services;

import com.example.demo.exception.BadRequestException;
import com.example.demo.exception.StudentNotFoundException;
import com.example.demo.mapper.SourceTargetMapper;
import com.example.demo.model.Gender;
import com.example.demo.model.Student;
import com.example.demo.repositories.StudentRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

/**
 * @author : christiaan.griffioen
 * @since :  16-6-2021, wo
 **/

@ExtendWith(MockitoExtension.class)
class StudentServiceImplTest {

    @Mock
    private StudentRepo studentRepo; //we already know studentrepo works, therefore iso @Autowired, we can Mock it.

    @Mock
    private SourceTargetMapper mapper;

    @InjectMocks
    private StudentService studentService;


/*    @MockitoExtension.class takes care of this
    @AfterEach
    void tearDown() throws Exception {
        autoCloseable.close();
    }*/

    @Test
    @DisplayName("Test should pass when studentRepo is being called")
    void getStudents() {
        //when
        studentService.getStudents();
        //then
        //verify that studentRepo was invoked after studentService.getStudents was called.
        //we don't want to actually call the DB, so to make our unit test fast, we mock studentRepo.
        verify(studentRepo).findAll();
    }

    @Test
    @DisplayName("Test should pass when studentRepo is being called")
    void getStudentById() {
        //given
        Long id = 1L;
        Student givenStudent = new Student();
        givenStudent.setFirstName("Peppa");
        givenStudent.setLastName("Pig");
        Optional<Student> optionalStudent = Optional.of(givenStudent);
        given(studentRepo.existsById(id))
                .willReturn(true);
        //when
        studentService.getStudentById(id);
        //then
        verify(studentRepo).existsById(id);
        verify(studentRepo).getById(id);
    }

    @Test
    @DisplayName("Test should pass when error message is returned")
    void getStudentByIdStudentNotFound() {
        //given
        Long id = 1L;
        given(studentRepo.existsById(anyLong()))
                .willReturn(false);

        //then
        assertThatThrownBy(() -> studentService.getStudentById(id))
                .isInstanceOf(StudentNotFoundException.class)
                .hasMessageContaining("student with id " + id + "is not present in DB");

        verify(studentRepo, never()).findById(id);

    }

    @Test
    void addNewStudent() {
        //given
        Student student = new Student();
        student.setFirstName("Peppa");
        student.setLastName("Pig");
        student.setEmail("peppa@gmail.com");
        student.setGender(Gender.FEMALE);

        //when
        studentService.addNewStudent(student);

        //then
        ArgumentCaptor<Student> argumentCaptor = ArgumentCaptor.forClass(Student.class);
        verify(studentRepo).save(argumentCaptor.capture());
        Student capturedStudent = argumentCaptor.getValue();
        assertThat(capturedStudent).isEqualTo(student);
        verify(studentRepo).findStudentByEmail(student.getEmail());
    }

    @Test
    void addNewStudentEmailTaken() {
        Optional<Student> studentOptional = Optional.of(new Student());
        //given
        Student student = new Student();
        student.setFirstName("Peppa");
        student.setLastName("Pig");
        student.setEmail("peppa@gmail.com");
        student.setGender(Gender.FEMALE);

        given(studentRepo.findStudentByEmail(anyString()))
                .willReturn(studentOptional);

        //then
        assertThatThrownBy(() -> studentService.addNewStudent(student))
                .isInstanceOf(BadRequestException.class)
                .hasMessageContaining("email taken");

        verify(studentRepo, never()).save(any());
    }

    @Test
    void deleteStudent() {
        //given
        Long id = 1L;
        given(studentRepo.existsById(anyLong()))
                .willReturn(true);
        //when
        studentService.deleteStudent(id);
        //then
        ArgumentCaptor<Long> argumentCaptor = ArgumentCaptor.forClass(Long.class);
        verify(studentRepo).deleteById(argumentCaptor.capture());
        Long capturedId = argumentCaptor.getValue();
        assertThat(capturedId).isEqualTo(id);
        verify(studentRepo).existsById(id);
    }

    @Test
    void deleteStudentDoesNotExist() {
        //given
        Long id = 1L;
        given(studentRepo.existsById(anyLong()))
                .willReturn(false);

        //then
        assertThatThrownBy(() -> studentService.deleteStudent(id))
                .isInstanceOf(StudentNotFoundException.class)
                .hasMessageContaining("student with id " + id + "is not present in DB");

        verify(studentRepo, never()).deleteById(id);
    }

    @Test
    void updateStudent() {
        //given
        Long id = 1L;
        String firstName = "Jan";
        String email = "jansen@yahoo.com";
        Student testStudent = new Student();
        testStudent.setFirstName("Peppa");
        testStudent.setEmail("peppa@gmail.com");
        Optional<Student> optionalStudent = Optional.of(testStudent);
        given(studentRepo.findById(id))
                .willReturn(optionalStudent);
        //when
        studentService.updateStudent(id, firstName, email);

        //then
        verify(studentRepo).findById(id);
        verify(studentRepo).findStudentByEmail(email);
        assertThat(testStudent.getFirstName()).isEqualTo(firstName);
        assertThat(testStudent.getEmail()).isEqualTo(email);
    }

    @Test
    void updateStudentEmailTaken() {
        //given
        Long id = 1L;
        String firstName = "Jan";
        String email = "jansen@email.com";
        Student testStudentId = new Student();
        testStudentId.setFirstName("Peppa");
        testStudentId.setEmail("peppa@gmail.com");
        Student testStudentEmail = new Student();
        testStudentEmail.setFirstName("Jan");
        testStudentEmail.setEmail("jansen@email.com");

        Optional<Student> optionalStudentId = Optional.of(testStudentId);
        Optional<Student> optionalStudentEmail = Optional.of(testStudentEmail);
        given(studentRepo.findById(id))
                .willReturn(optionalStudentId);
        given(studentRepo.findStudentByEmail(email))
                .willReturn(optionalStudentEmail);

        //when //then
        assertThatThrownBy(() -> studentService.updateStudent(id, firstName, email))
                .isInstanceOf(BadRequestException.class)
                .hasMessageContaining("Email taken");

        assertThat(testStudentId.getEmail()).isNotEqualTo(email);
    }

    @Test
    void updateStudentCannotFindStudent() {
        //given
        Long id = 1L;
        String firstName = "Jan";
        String email = "jansen@email.com";

        given(studentRepo.findById(id))
                .willReturn(Optional.empty());

        //when //then
        assertThatThrownBy(() -> studentService.updateStudent(id, firstName, email))
                .isInstanceOf(StudentNotFoundException.class)
                .hasMessageContaining("student with id " + id + "is not present in DB");
    }
}