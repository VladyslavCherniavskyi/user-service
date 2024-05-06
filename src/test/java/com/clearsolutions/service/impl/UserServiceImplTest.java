package com.clearsolutions.service.impl;

import com.clearsolutions.dto.SearchDto;
import com.clearsolutions.dto.UserPatchDto;
import com.clearsolutions.entity.User;
import com.clearsolutions.mapper.UserMapper;
import com.clearsolutions.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.Date;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private User user;
    @Mock
    private UserRepository userRepository;
    @Mock
    private UserMapper userMapper;
    @InjectMocks
    private UserServiceImpl userService;

    @Test
    public void create() {
        //given
        var newUser = new User();

        Mockito.doReturn(user)
                .when(userRepository)
                .save(newUser);

        //when
        var actual = userService.create(newUser);

        //then
        Assertions.assertEquals(user, actual);
        Assertions.assertNotNull(actual);
    }

    @Test
    void read() {
        //given
        var id = 1L;

        Mockito.doReturn(Optional.of(user))
                .when(userRepository)
                .findById(id);

        //when
        var actual = userService.read(id);

        //then
        Assertions.assertEquals(user, actual);
        Assertions.assertNotNull(actual);
    }

    @Test
    void read_throwEntityNotFoundException() {
        //when
        Assertions.assertThrows(
                EntityNotFoundException.class,
                () -> userService.read(null)
        );
    }

    @Test
    void update() {
        //given
        var newUser = new User();
        newUser.setAddress("Address");
        newUser.setPhone("00000");


        Mockito.doReturn(Optional.of(user))
                .when(userRepository)
                .findById(newUser.getId());

        Mockito.doReturn(user)
                .when(userRepository)
                .save(newUser);

        user.setAddress("Address");
        user.setPhone("00000");

        //when
        var actual = userService.update(newUser);

        //then
        Assertions.assertEquals(user, actual);
    }

    @Test
    void delete() {
        //giver
        var id = 1L;

        Mockito.doReturn(Optional.of(user))
                .when(userRepository)
                .findById(id);

        Mockito.doNothing()
                .when(userRepository)
                .delete(user);

        //when
        userService.delete(id);

        //then
        Mockito.verify(userRepository).delete(user);
    }

    @Test
    void patch() {
        //giver
        var id = 1L;
        var entityUser = Mockito.mock(User.class);
        var patchDto = new UserPatchDto(
                null,
                null,
                null,
                null,
                null,
                "address"
        );

        Mockito.doReturn(Optional.of(entityUser))
                .when(userRepository)
                .findById(id);

        Mockito.doNothing()
                .when(userMapper)
                .patch(patchDto, entityUser);

        Mockito.doReturn(user)
                .when(userRepository)
                .save(entityUser);

        //when
        var actual = userService.patch(id, patchDto);

        //then
        Assertions.assertEquals(user, actual);
        Mockito.verify(userRepository).save(entityUser);
        Mockito.verify(userRepository).findById(id);
        Mockito.verify(userMapper).patch(patchDto, entityUser);
    }

    @Test
    public void search() {
        //given
        var searchDto = new SearchDto(
                new Date(),
                new Date()
        );
        var pageable = PageRequest.of(0, 10);
        var expectedResult = Mockito.mock(Page.class);

        Mockito.doReturn(expectedResult)
                .when(userRepository)
                .search(searchDto, pageable);
        // when
        var result = userService.search(searchDto, pageable);

        // then
        Assertions.assertEquals(expectedResult, result);
        Mockito.verify(userRepository).search(searchDto, pageable);
    }

}