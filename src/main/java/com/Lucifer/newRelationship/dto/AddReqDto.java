package com.Lucifer.newRelationship.dto;

import jakarta.validation.constraints.NotBlank;

public record AddReqDto(

        @NotBlank(message = "city is required")
        String city,

        @NotBlank(message = "state is required")
        String state,

        @NotBlank(message = "country is required")
        String country
) {
}
