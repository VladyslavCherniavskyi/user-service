package com.clearsolutions.mapper;

import com.clearsolutions.dto.request.UserCreateDtoRequest;
import com.clearsolutions.dto.request.UserUpdateDtoRequest;
import com.clearsolutions.dto.response.UserDtoResponse;
import com.clearsolutions.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface UserMapper {

    User mapFrom(UserCreateDtoRequest userCreateDtoRequest);

    User mapFrom(UserUpdateDtoRequest userUpdateDtoRequest);

    UserDtoResponse mapTo(User user);

}
