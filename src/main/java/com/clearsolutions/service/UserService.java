package com.clearsolutions.service;

import com.clearsolutions.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Date;
import java.util.Map;

public interface UserService {

    User create(User user);

    User read(Long id);

    User update(User user);

    void delete(Long id);

    User patch(Long id, Map<String, Object> attributes);

    Page<User> searchBetweenDateOfBirth(Date from, Date to, Pageable pageable);

    Page<User> searchBetweenAges(Integer from, Integer to, Pageable pageable);

}
