package com.example.demo.mapper;

import com.example.demo.dto.StudentDto;
import com.example.demo.model.Student;

import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.junit.jupiter.api.Assertions.*;


class SourceTargetMapperTest {

    private SourceTargetMapper mapper = Mappers.getMapper(SourceTargetMapper.class);

    @Test
    public void givenSourceToDestination_whenMaps_thenCorrect(){
        Student student = Student.builder()
                .firstName("Jan")
                .lastName("Jansen")
                .age(12)
                .email("jansen@outlook.com")
                .interests("guitar")
                .build();

        StudentDto studentDto = mapper.studentToStudentDto(student);

        assertEquals(student.getFirstName(), studentDto.getFirstName());
    }

    public void givenDestinationToSource_whenMaps_thenCorrect(){
        StudentDto studentDto = StudentDto.builder()
                .firstName("Jan")
                .lastName("Jansen")
                .age(12)
                .email("jansen@outlook.com")
                .interests("guitar")
                .build();

        Student student = mapper.studentDtoToStudent(studentDto);

        assertEquals(student.getFirstName(), studentDto.getFirstName());
    }


}