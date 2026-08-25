package com.Lucifer.newRelationship.dto;

import jakarta.validation.constraints.*;

import java.util.List;


public record StdReqDto(
        @NotBlank(message = "first name is required")
        @Size(min=2,max=30, message = " name must be between 2 and 30 characters ")
        String name,

        @NotBlank(message = "email should not be empty")
        @Email(message = "please enter a valid email")
        String email,

        @NotBlank(message = "password is required")
        @Size(min = 8,max = 40,message = "password must contain at least 8 characters")
        @Pattern(regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@#$%^&*_+!]).{8,}$",
                message = "password must contain one uppercase letter one lowercase letter,one number and one special chara cter")
        String password,

        AddReqDto address,

        @NotNull(message = "department id is required")
        Integer deptId,

        List<Integer> courseIds,

        Long version

) {
}
