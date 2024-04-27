package com.clearsolutions.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public record UserCreateDtoRequest(

        @NotBlank(message = "FirstName cannot be empty")
        String firstName,

        @NotBlank(message = "LastName cannot be empty")
        String lastName,

        @NotNull(message = "DateOfBirth cannot be null" )
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
        Date dateOfBirth,

        @Pattern(
                regexp = "^\\+[0-9]*$",
                message = "Phone must be a valid numeric value, optionally starting with a '+'"
        )
        String phone,

        @NotBlank(message = "Email cannot be empty")
        @Email(message = "Invalid email format. Please provide a valid email address.")
        String email,

        String address

) {
}
