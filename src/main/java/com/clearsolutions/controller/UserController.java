package com.clearsolutions.controller;

import com.clearsolutions.dto.request.SearchRequest;
import com.clearsolutions.dto.request.UserCreateRequest;
import com.clearsolutions.dto.request.UserPatchRequest;
import com.clearsolutions.dto.request.UserUpdateRequest;
import com.clearsolutions.dto.response.UserResponse;
import com.clearsolutions.facade.UserFacade;
import com.clearsolutions.service.UserService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@Validated
public class UserController {

    private final UserFacade userFacade;
    private final UserService userService;

    @PostMapping
    public ResponseEntity<UserResponse> create(@RequestBody @Valid UserCreateRequest createRequest) {
        return new ResponseEntity<>(userFacade.create(createRequest), HttpStatus.CREATED);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<UserResponse> patch(
            @PathVariable @Positive(message = "Id must be positive") Long id,
            @RequestBody UserPatchRequest patchRequest) {
        return new ResponseEntity<>(userFacade.patch(id, patchRequest), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResponse> update(
            @PathVariable @Positive(message = "Id must be positive") Long id,
            @RequestBody @Valid UserUpdateRequest updateRequest) {
        return new ResponseEntity<>(userFacade.update(id, updateRequest), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable @Positive(message = "Id must be positive") Long id) {
        userService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/search")
    public ResponseEntity<Page<UserResponse>> search(@RequestBody @Valid SearchRequest searchRequest,
                                                     @PageableDefault Pageable pageable) {
        return new ResponseEntity<>(userFacade.search(searchRequest, pageable), HttpStatus.OK);
    }

}
