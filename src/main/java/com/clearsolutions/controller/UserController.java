package com.clearsolutions.controller;

import com.clearsolutions.dto.request.SearchDtoRequest;
import com.clearsolutions.dto.request.UserCreateDtoRequest;
import com.clearsolutions.dto.request.UserUpdateDtoRequest;
import com.clearsolutions.dto.response.UserDtoResponse;
import com.clearsolutions.facade.UserFacade;
import com.clearsolutions.service.UserService;
import com.clearsolutions.validation.CredentialValidation;
import com.clearsolutions.validation.FieldsValidation;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@Validated
public class UserController {

    private final UserFacade userFacade;
    private final UserService userService;
    private final CredentialValidation credentialValidation;
    private final FieldsValidation fieldsValidation;

    @PostMapping
    public ResponseEntity<UserDtoResponse> create(@RequestBody @Valid UserCreateDtoRequest userCreateDtoRequest) {
        credentialValidation.dataLimitValidate(userCreateDtoRequest.dateOfBirth());
        return new ResponseEntity<>(userFacade.create(userCreateDtoRequest), HttpStatus.CREATED);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<UserDtoResponse> updateFields(
            @PathVariable @NotNull(message = "Id cannot be null") Long id,
            @RequestBody Map<String, Object> attributes) {
        fieldsValidation.validFields(attributes);
        return new ResponseEntity<>(userFacade.updateFields(id, attributes), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDtoResponse> update(
            @PathVariable @NotNull(message = "Id cannot be null") Long id,
            @RequestBody @Valid UserUpdateDtoRequest userUpdateDtoRequest) {
        return new ResponseEntity<>(userFacade.update(id, userUpdateDtoRequest), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable @NotNull(message = "Id cannot be null") Long id) {
        userService.delete(id);
        return new ResponseEntity<>(String.format("User with id: '%s' is deleted!", id), HttpStatus.NO_CONTENT);
    }

    @PostMapping("/search")
    public ResponseEntity<Page<UserDtoResponse>> searchBetweenDateOfBirth(@RequestBody @Valid SearchDtoRequest searchDtoRequest,
                                                                          @PageableDefault Pageable pageable) {
        return new ResponseEntity<>(userFacade.searchBetweenDateOfBirth(searchDtoRequest, pageable), HttpStatus.OK);
    }

    @PostMapping("/search_between_ages")
    public ResponseEntity<Page<UserDtoResponse>> searchBetweenAges(
            @RequestParam @NotNull(message = "From can not be null") Integer from,
            @RequestParam @NotNull(message = "To can not be null") Integer to,
            @PageableDefault Pageable pageable) {
        return new ResponseEntity<>(userFacade.searchBetweenAges(from, to, pageable), HttpStatus.OK);
    }

}
