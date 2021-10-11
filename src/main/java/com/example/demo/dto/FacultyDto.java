package com.example.demo.dto;

import com.example.demo.model.Department;
import com.example.demo.model.Teacher;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
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
