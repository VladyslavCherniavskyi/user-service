package com.clearsolutions.facade;

import com.clearsolutions.dto.request.SearchDtoRequest;
import com.clearsolutions.dto.request.UserCreateDtoRequest;
import com.clearsolutions.dto.request.UserUpdateDtoRequest;
import com.clearsolutions.dto.response.UserDtoResponse;
import com.clearsolutions.mapper.UserMapper;
import com.clearsolutions.service.impl.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Component
@Transactional
@RequiredArgsConstructor
public class UserFacade {

    private final UserServiceImpl userService;
    private final UserMapper userMapper;

    public UserDtoResponse create(UserCreateDtoRequest userCreateDtoRequest) {
        var user = userMapper.mapFrom(userCreateDtoRequest);
        var createdUser = userService.create(user);
        return userMapper.mapTo(createdUser);
    }

    public UserDtoResponse updateFields(Long id, Map<String, Object> attributes) {
        var user = userService.patch(id, attributes);
        return userMapper.mapTo(user);
    }

    public UserDtoResponse update(Long id, UserUpdateDtoRequest userUpdateDtoRequest) {
        var user = userMapper.mapFrom(userUpdateDtoRequest);
        user.setId(id);
        var updatedUser = userService.update(user);
        return userMapper.mapTo(updatedUser);
    }

    public Page<UserDtoResponse> searchBetweenDateOfBirth(SearchDtoRequest searchDtoRequest, Pageable pageable) {
        return userService.searchBetweenDateOfBirth(
                        searchDtoRequest.from(),
                        searchDtoRequest.to(),
                        pageable)
                .map(userMapper::mapTo);
    }

    public Page<UserDtoResponse> searchBetweenAges(Integer from, Integer to, Pageable pageable) {
        return userService.searchBetweenAges(from, to, pageable)
                .map(userMapper::mapTo);
    }

}
