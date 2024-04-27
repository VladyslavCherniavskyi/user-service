package com.clearsolutions.facade;

import com.clearsolutions.dto.request.SearchRequest;
import com.clearsolutions.dto.request.UserCreateRequest;
import com.clearsolutions.dto.request.UserPatchRequest;
import com.clearsolutions.dto.request.UserUpdateRequest;
import com.clearsolutions.dto.response.UserResponse;
import com.clearsolutions.mapper.UserMapper;
import com.clearsolutions.service.impl.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserFacade {

    private final UserServiceImpl userService;
    private final UserMapper userMapper;

    public UserResponse create(UserCreateRequest createRequest) {
        var user = userMapper.mapFrom(createRequest);
        var createdUser = userService.create(user);
        return userMapper.mapTo(createdUser);
    }

    public UserResponse patch(Long id, UserPatchRequest patchRequest) {
        var patchDto = userMapper.mapFrom(patchRequest);
        var user = userService.patch(id, patchDto);
        return userMapper.mapTo(user);
    }

    public UserResponse update(Long id, UserUpdateRequest updateRequest) {
        var user = userMapper.mapFrom(updateRequest);
        user.setId(id);
        var updatedUser = userService.update(user);
        return userMapper.mapTo(updatedUser);
    }

    public Page<UserResponse> search(SearchRequest searchRequest, Pageable pageable) {
        var searchDto = userMapper.mapFrom(searchRequest);
        return userService.search(searchDto, pageable)
                .map(userMapper::mapTo);
    }

}
