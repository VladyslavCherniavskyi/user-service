package com.clearsolutions.repository;

import com.clearsolutions.dto.SearchDto;
import com.clearsolutions.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {

    @Query("select u from User u where u.dateOfBirth >= :#{#dto.from} and u.dateOfBirth <= :#{#dto.to}")
    Page<User> search(@Param("dto") SearchDto searchDto, Pageable pageable);

}
