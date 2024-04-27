package com.clearsolutions.mapper;

import com.clearsolutions.dto.SearchDto;
import com.clearsolutions.dto.UserPatchDto;
import com.clearsolutions.dto.request.SearchRequest;
import com.clearsolutions.dto.request.UserCreateRequest;
import com.clearsolutions.dto.request.UserPatchRequest;
import com.clearsolutions.dto.request.UserUpdateRequest;
import com.clearsolutions.dto.response.UserResponse;
import com.clearsolutions.entity.User;
import org.mapstruct.*;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface UserMapper {

    User mapFrom(UserCreateRequest createRequest);

    User mapFrom(UserUpdateRequest updateRequest);

    UserResponse mapTo(User user);

    @BeanMapping(
            nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
            nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
    )
    void patch(UserPatchDto patchDto, @MappingTarget User user);

    UserPatchDto mapFrom(UserPatchRequest patchRequest);

    SearchDto mapFrom(SearchRequest searchRequest);

}
