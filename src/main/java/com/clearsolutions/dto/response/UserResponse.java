package com.clearsolutions.dto.response;

import java.util.Date;

public record UserResponse(

        Long id,

        String firstName,

        String lastName,

        Date dateOfBirth,

        String phone,

        String email,

        String address

) {
}
