package com.clearsolutions.dto.request;

import com.clearsolutions.validation.anotation.Adult;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;

import java.util.Date;

public record UserPatchRequest(

        String firstName,

        String lastName,

        @Adult
        Date dateOfBirth,

        @Pattern(
                regexp = "^\\+[0-9]*$",
                message = "Phone must be a valid numeric value and starting with a '+'"
        )
        String phone,

        @Email(message = "Invalid email format. Please provide a valid email address.")
        String email,

        String address

) {
}
