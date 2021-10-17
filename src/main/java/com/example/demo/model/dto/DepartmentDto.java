package com.example.demo.model.dto;


import com.example.demo.model.entity.Faculty;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class DepartmentDto {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("name")
    private String name;

    @JsonProperty("faculty")
    private Faculty faculty;

}
