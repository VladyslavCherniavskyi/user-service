package com.clearsolutions.service;

import com.clearsolutions.dto.SearchDto;
import com.clearsolutions.dto.UserPatchDto;
import com.clearsolutions.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService {

    User create(User user);

    User read(Long id);

    User update(User user);

    void delete(Long id);

    User patch(Long id, UserPatchDto patchDto);

    Page<User> search(SearchDto searchDto, Pageable pageable);

}
