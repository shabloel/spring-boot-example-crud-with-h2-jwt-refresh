package com.example.demo.model.dto;

import com.example.demo.model.entity.Course;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
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
