package com.clearsolutions.mapper;

import com.clearsolutions.dto.SearchDto;
import com.clearsolutions.dto.UserPatchDto;
import com.clearsolutions.dto.request.SearchRequest;
import com.clearsolutions.dto.request.UserCreateRequest;
import com.clearsolutions.dto.request.UserPatchRequest;
import com.clearsolutions.dto.request.UserUpdateRequest;
import com.clearsolutions.dto.response.UserResponse;
import com.clearsolutions.entity.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Date;

@ExtendWith(MockitoExtension.class)
class UserMapperTest {

    @InjectMocks
    private UserMapperImpl userMapper;

    @Test
    public void mapFrom_createRequest() {
        //given
        var createRequest = Mockito.mock(UserCreateRequest.class);
        var expected = Mockito.mock(User.class);

        //when
        var actual = userMapper.mapFrom(createRequest);

        //then
        asserts(expected, actual);
    }

    @Test
    public void mapFrom_createRequestIsNull() {
        //when
        var actual = userMapper.mapFrom((UserCreateRequest) null);

        //then
        Assertions.assertNull(actual);
    }

    @Test
    public void mapFrom_updateRequest() {
        //given
        var updateRequest = Mockito.mock(UserUpdateRequest.class);
        var expected = Mockito.mock(User.class);

        //when
        var actual = userMapper.mapFrom(updateRequest);

        //then
        asserts(expected, actual);
    }

    @Test
    public void mapFrom_updateRequestIsNull() {
        //when
        var actual = userMapper.mapFrom((UserUpdateRequest) null);

        //then
        Assertions.assertNull(actual);
    }

    @Test
    public void mapTo_user() {
        //given
        var user = Mockito.mock(User.class);
        var expected = Mockito.mock(UserResponse.class);

        //when
        var actual = userMapper.mapTo(user);

        //then
        Assertions.assertNotNull(actual);
        Assertions.assertEquals(expected.firstName(), actual.firstName());
        Assertions.assertEquals(expected.lastName(), actual.lastName());
        Assertions.assertEquals(expected.dateOfBirth(), actual.dateOfBirth());
        Assertions.assertEquals(expected.phone(), actual.phone());
        Assertions.assertEquals(expected.email(), actual.email());
        Assertions.assertEquals(expected.address(), actual.address());
    }

    @Test
    public void mapTo_userIsNull() {
        //when
        var actual = userMapper.mapTo(null);

        //then
        Assertions.assertNull(actual);
    }

    @Test
    public void patch() {
        //given
        var dateOfBirth = Mockito.mock(Date.class);
        var user = Mockito.mock(User.class);
        var patchDto = new UserPatchDto(
                "firstName",
                "lastName",
                dateOfBirth,
                "+00",
                "email@test.com",
                "address"
        );
        var expected = User.builder()
                .firstName("firstName")
                .lastName("lastName")
                .dateOfBirth(dateOfBirth)
                .phone("+00")
                .email("email@test.com")
                .address("address")
                .build();

        //when
        userMapper.patch(patchDto, user);

        //then
        Assertions.assertNotNull(patchDto.firstName());
        Assertions.assertNotNull(patchDto.lastName());
        Assertions.assertNotNull(patchDto.dateOfBirth());
        Assertions.assertNotNull(patchDto.phone());
        Assertions.assertNotNull(patchDto.email());
        Assertions.assertNotNull(patchDto.address());
        Assertions.assertEquals(expected.getFirstName(), patchDto.firstName());
        Assertions.assertEquals(expected.getLastName(), patchDto.lastName());
        Assertions.assertEquals(expected.getDateOfBirth(), patchDto.dateOfBirth());
        Assertions.assertEquals(expected.getPhone(), patchDto.phone());
        Assertions.assertEquals(expected.getEmail(), patchDto.email());
        Assertions.assertEquals(expected.getAddress(), patchDto.address());
    }

    @Test
    public void patch_patchDtoIsNull() {
        //given
        var user = Mockito.mock(User.class);

        //when
        userMapper.patch(null, user);

        //then
        Assertions.assertNull(user.getFirstName());
    }

    @Test
    public void mapFrom_patchRequest() {
        //given
        var userPatchRequest = Mockito.mock(UserPatchRequest.class);
        var expected = Mockito.mock(UserPatchDto.class);

        //when
        var actual = userMapper.mapFrom(userPatchRequest);

        //then
        Assertions.assertNotNull(actual);
        Assertions.assertEquals(expected.firstName(), actual.firstName());
        Assertions.assertEquals(expected.lastName(), actual.lastName());
        Assertions.assertEquals(expected.dateOfBirth(), actual.dateOfBirth());
        Assertions.assertEquals(expected.phone(), actual.phone());
        Assertions.assertEquals(expected.email(), actual.email());
        Assertions.assertEquals(expected.address(), actual.address());
    }

    @Test
    public void mapFrom_patchRequestIsNull() {
        //when
        var actual = userMapper.mapFrom((UserPatchRequest) null);

        //then
        Assertions.assertNull(actual);
    }

    @Test
    public void mapFrom_searchRequest() {
        //given
        var searchRequest = Mockito.mock(SearchRequest.class);
        var expected = Mockito.mock(SearchDto.class);

        //when
        var actual = userMapper.mapFrom(searchRequest);

        //then
        Assertions.assertNotNull(actual);
        Assertions.assertEquals(expected.from(), actual.from());
        Assertions.assertEquals(expected.to(), actual.to());
    }

    @Test
    public void mapFrom_searchRequestIsNull() {
        //when
        var actual = userMapper.mapFrom((SearchRequest) null);

        //then
        Assertions.assertNull(actual);
    }

    private void asserts(User expected, User actual) {
        Assertions.assertNotNull(actual);
        Assertions.assertEquals(expected.getFirstName(), actual.getFirstName());
        Assertions.assertEquals(expected.getLastName(), actual.getLastName());
        Assertions.assertEquals(expected.getDateOfBirth(), actual.getDateOfBirth());
        Assertions.assertEquals(expected.getPhone(), actual.getPhone());
        Assertions.assertEquals(expected.getEmail(), actual.getEmail());
        Assertions.assertEquals(expected.getAddress(), actual.getAddress());
    }

}