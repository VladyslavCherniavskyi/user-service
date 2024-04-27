package com.clearsolutions.service.impl;

import com.clearsolutions.entity.User;
import com.clearsolutions.repository.UserRepository;
import com.clearsolutions.service.UserService;
import io.vavr.control.Try;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Field;
import java.util.Date;
import java.util.Map;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public User create(User user) {
        return userRepository.save(user);
    }

    public User read(Long id) {
        return userRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException(
                        String.format("User with id:%s is not found", id)
                )
        );
    }

    @Override
    public User update(User user) {
        read(user.getId());
        return userRepository.save(user);
    }

    @Override
    public void delete(Long id) {
        var user = read(id);
        userRepository.delete(user);
    }

    @Override
    public User patch(Long id, Map<String, Object> attributes) {
        var existingUser = read(id);
        attributes.forEach((attribute, value) -> Try.run(() -> {
                            Field field = User.class.getDeclaredField(attribute);
                            field.setAccessible(true);
                            field.set(existingUser, value);
                        }
                ).onFailure(e -> //TODO fix date cast
                        log.error(String.format("Error setting field '%s' with value: %s", attribute, value))
                )
        );
        return update(existingUser);
    }

    @Override
    public Page<User> searchBetweenDateOfBirth(Date from, Date to, Pageable pageable) {
        return userRepository.findAllByDateOfBirthBetween(from, to, pageable);
    }

    @Override
    public Page<User> searchBetweenAges(Integer from, Integer to, Pageable pageable) {
        return userRepository.findUsersBetweenAges(from, to, pageable);
    }

}
