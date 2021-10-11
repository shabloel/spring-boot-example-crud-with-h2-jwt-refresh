package com.example.demo.dto;

import com.example.demo.model.Gender;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Builder
@Data
public class StudentDto {

    @JsonProperty("id")
    private Long id;

    @NotBlank
    @JsonProperty("firstName")
    private String firstName;

    @NotBlank
    @JsonProperty("lastName")
    private String lastName;

    @JsonProperty("gender")
    private Gender gender;

    @JsonProperty("age")
    private int age;

    @NotBlank
    @JsonProperty("email")
    private String email;

    @JsonProperty("interests")
    private String interests;

}
