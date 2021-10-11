package com.example.demo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class AddressDto {

    @JsonProperty("id")
    private Long id;

    @NotBlank
    @JsonProperty("street")
    private String street;

    @NotBlank
    @JsonProperty("postalCode")
    private String postalCode;

    @JsonProperty("town")
    private String town;

}
