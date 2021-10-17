package com.example.demo.mapper;

import com.example.demo.model.dto.AddressDto;
import com.example.demo.model.dto.StudentDto;
import com.example.demo.model.dto.TeacherDto;
import com.example.demo.model.entity.Address;
import com.example.demo.model.entity.Student;
import com.example.demo.model.entity.Teacher;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SourceTargetMapper {
    StudentDto studentToStudentDto(Student student);

    Student studentDtoToStudent(StudentDto studentDto);

    AddressDto addressToAddressDto(Address address);

    Address addressDtoToAddress(AddressDto addressDto);

    TeacherDto teacherToTeacherDto(Teacher teacher);

    Teacher teacherDtoToTeacher(TeacherDto teacherDto);
}
