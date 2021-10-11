package com.example.demo.dto;

import com.example.demo.model.Course;
import com.example.demo.model.Faculty;
import com.example.demo.model.Student;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Set;

/**
 * @author : christiaan.griffioen
 * @since :  5-7-2021, ma
 **/

@Data
public class TeacherDto {

    @JsonProperty("id")
    private Long id;

    @NotBlank
    @JsonProperty("firstName")
    private String firstName;

    @NotBlank
    @JsonProperty("lastName")
    private String lastName;

    @JsonProperty("courses")
    private Set<Course> courses;

}
