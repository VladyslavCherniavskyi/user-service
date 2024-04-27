package com.clearsolutions.service.impl;

import com.clearsolutions.dto.SearchDto;
import com.clearsolutions.dto.UserPatchDto;
import com.clearsolutions.entity.User;
import com.clearsolutions.mapper.UserMapper;
import com.clearsolutions.repository.UserRepository;
import com.clearsolutions.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

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
    public User patch(Long id, UserPatchDto patchDto) {
        var user = read(id);
        userMapper.patch(patchDto, user);
        return userRepository.save(user);
    }

    @Override
    public Page<User> search(SearchDto searchDto, Pageable pageable) {
        return userRepository.search(searchDto, pageable);
    }

}
