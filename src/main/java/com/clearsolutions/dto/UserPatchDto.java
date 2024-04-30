package com.clearsolutions.dto;

import java.util.Date;

public record UserPatchDto(

        String firstName,

        String lastName,

        Date dateOfBirth,

        String phone,

        String email,

        String address

) {
}
