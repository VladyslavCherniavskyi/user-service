package com.clearsolutions.facade;

import com.clearsolutions.dto.SearchDto;
import com.clearsolutions.dto.UserPatchDto;
import com.clearsolutions.dto.request.SearchRequest;
import com.clearsolutions.dto.request.UserCreateRequest;
import com.clearsolutions.dto.request.UserPatchRequest;
import com.clearsolutions.dto.request.UserUpdateRequest;
import com.clearsolutions.dto.response.UserResponse;
import com.clearsolutions.entity.User;
import com.clearsolutions.mapper.UserMapperImpl;
import com.clearsolutions.service.impl.UserServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.Collections;

@ExtendWith(MockitoExtension.class)
class UserFacadeTest {

    @Mock
    private UserServiceImpl userService;
    @Mock
    private UserMapperImpl userMapper;
    @InjectMocks
    private UserFacade userFacade;

    @Test
    void create() {
        //given
        var createRequest = Mockito.mock(UserCreateRequest.class);
        var user = Mockito.mock(User.class);
        var createdUser = Mockito.mock(User.class);
        var userResponse = Mockito.mock(UserResponse.class);

        Mockito.doReturn(user)
                .when(userMapper)
                .mapFrom(createRequest);

        Mockito.doReturn(createdUser)
                .when(userService)
                .create(user);

        Mockito.doReturn(userResponse)
                .when(userMapper)
                .mapTo(createdUser);

        //when
        var actual = userFacade.create(createRequest);

        //then
        Assertions.assertEquals(userResponse, actual);
    }

    @Test
    void patch() {
        //given
        var id = 1L;
        var patchRequest = Mockito.mock(UserPatchRequest.class);
        var patchDto = Mockito.mock(UserPatchDto.class);
        var user = Mockito.mock(User.class);
        var userResponse = Mockito.mock(UserResponse.class);

        Mockito.doReturn(patchDto)
                .when(userMapper)
                .mapFrom(patchRequest);

        Mockito.doReturn(user)
                .when(userService)
                .patch(id, patchDto);

        Mockito.doReturn(userResponse)
                .when(userMapper)
                .mapTo(user);

        //when
        var actual = userFacade.patch(id, patchRequest);

        //then
        Assertions.assertEquals(userResponse, actual);
    }

    @Test
    void update() {
        //given
        var id = 1L;
        var updateRequest = Mockito.mock(UserUpdateRequest.class);
        var user = Mockito.mock(User.class);
        var updatedUser = Mockito.mock(User.class);
        var userResponse = Mockito.mock(UserResponse.class);

        Mockito.doReturn(user)
                .when(userMapper)
                .mapFrom(updateRequest);

        Mockito.doReturn(updatedUser)
                .when(userService)
                .update(user);

        Mockito.doReturn(userResponse)
                .when(userMapper)
                .mapTo(updatedUser);

        //when
        var actual = userFacade.update(id, updateRequest);

        //then
        Assertions.assertEquals(userResponse, actual);
        Mockito.verify(user).setId(id);
    }

    @Test
    void search() {
        //given
        var searchRequest = Mockito.mock(SearchRequest.class);
        var pageable = Mockito.mock(Pageable.class);
        var searchDto = Mockito.mock(SearchDto.class);
        var user = Mockito.mock(User.class);
        var userPages = new PageImpl<>(Collections.singletonList(user));
        var userResponse = Mockito.mock(UserResponse.class);

        Mockito.doReturn(searchDto)
                .when(userMapper)
                .mapFrom(searchRequest);

        Mockito.doReturn(userPages)
                .when(userService)
                .search(searchDto, pageable);

        Mockito.doReturn(userResponse)
                .when(userMapper)
                .mapTo(user);

        //when
        var actual = userFacade.search(searchRequest, pageable);

        //then
        Assertions.assertEquals(userResponse, actual.getContent().get(0));
        Assertions.assertEquals(1, actual.getTotalElements());
    }

}