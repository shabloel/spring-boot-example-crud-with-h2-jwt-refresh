package com.example.demo.dto;

import com.example.demo.model.CourseRegistration;
import com.example.demo.model.Teacher;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Set;

/**
 * @author : christiaan.griffioen
 * @since :  29-6-2021, di
 **/

@Data
@NoArgsConstructor
public class CourseDto {

    @JsonProperty("id")
    private Long id;

    @NotBlank
    @JsonProperty("name")
    private String name;

    @NotBlank
    @JsonProperty("description")
    private String description;

}
