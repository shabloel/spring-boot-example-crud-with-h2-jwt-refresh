package com.example.demo.model.dto;

import com.example.demo.model.entity.Department;
import com.example.demo.model.entity.Teacher;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.Set;

@Data
public class FacultyDto {

    @JsonProperty("id")
    private Long id;

    @NotNull
    @JsonProperty("name")
    private String name;

    @JsonProperty("teachers")
    private Set<Teacher> teachers;

    @JsonProperty("departments")
    private Set<Department> departments;
}
