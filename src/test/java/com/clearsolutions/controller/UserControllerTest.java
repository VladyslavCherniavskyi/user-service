package com.clearsolutions.controller;

import com.clearsolutions.dto.request.SearchRequest;
import com.clearsolutions.dto.request.UserCreateRequest;
import com.clearsolutions.dto.request.UserPatchRequest;
import com.clearsolutions.dto.request.UserUpdateRequest;
import com.clearsolutions.dto.response.UserResponse;
import com.clearsolutions.facade.UserFacade;
import com.clearsolutions.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;

import java.util.Collections;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    @Mock
    private UserService userService;
    @Mock
    private UserFacade userFacade;
    @InjectMocks
    private UserController userController;

    @Test
    public void create() {
        //given
        var createRequest = Mockito.mock(UserCreateRequest.class);
        var userResponse = Mockito.mock(UserResponse.class);

        Mockito.doReturn(userResponse)
                .when(userFacade)
                .create(createRequest);

        //when
        var actual = userController.create(createRequest);

        //Then
        Assertions.assertEquals(HttpStatus.CREATED, actual.getStatusCode());
        Assertions.assertEquals(userResponse, actual.getBody());
    }

    @Test
    public void patch() {
        //given
        var id = 1L;
        var patchRequest = Mockito.mock(UserPatchRequest.class);
        var userResponse = Mockito.mock(UserResponse.class);

        Mockito.doReturn(userResponse)
                .when(userFacade)
                .patch(id, patchRequest);

        //when
        var actual = userController.patch(id, patchRequest);

        //then
        Assertions.assertEquals(HttpStatus.OK, actual.getStatusCode());
        Assertions.assertEquals(userResponse, actual.getBody());
    }

    @Test
    public void update() {
        //given
        var id = 1L;
        var updateRequest = Mockito.mock(UserUpdateRequest.class);
        var userResponse = Mockito.mock(UserResponse.class);

        Mockito.doReturn(userResponse)
                .when(userFacade)
                .update(id, updateRequest);

        //when
        var actual = userController.update(id, updateRequest);

        //then
        Assertions.assertEquals(HttpStatus.OK, actual.getStatusCode());
        Assertions.assertEquals(userResponse, actual.getBody());
    }

    @Test
    public void delete() {
        //given
        var id = 1L;

        Mockito.doNothing()
                .when(userService)
                .delete(id);

        //when
        var actual = userController.delete(id);

        //then
        Assertions.assertEquals(HttpStatus.NO_CONTENT, actual.getStatusCode());
        Mockito.verify(userService).delete(id);
    }

    @Test
    public void search() {
        //given
        var searchRequest = Mockito.mock(SearchRequest.class);
        var pageable = Mockito.mock(Pageable.class);
        var userResponse = Mockito.mock(UserResponse.class);
        var userResponsePages = new PageImpl<>(Collections.singletonList(userResponse));

        Mockito.doReturn(userResponsePages)
                .when(userFacade)
                .search(searchRequest, pageable);

        //when
        var actual = userController.search(searchRequest, pageable);

        //then
        Assertions.assertEquals(HttpStatus.OK, actual.getStatusCode());
        Assertions.assertEquals(userResponsePages, actual.getBody());
    }

}