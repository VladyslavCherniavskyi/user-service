package com.clearsolutions.dto.response;

import java.util.Date;

public record UserDtoResponse(

        Long id,

        String firstName,

        String lastName,

        Date dateOfBirth,

        String phone,

        String email,

        String address

) {
}
