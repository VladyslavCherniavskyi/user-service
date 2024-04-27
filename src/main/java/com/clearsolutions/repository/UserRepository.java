package com.clearsolutions.repository;

import com.clearsolutions.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query("select u from User u where u.dateOfBirth >= :fromDate and u.dateOfBirth < :toDate")
    Page<User> findAllByDateOfBirthBetween(Date fromDate, Date toDate, Pageable pageable);

    @Query(nativeQuery = true,
            value = """
                    select * from user_service.users u
                    where date_part('year', age(u.date_of_birth))
                    between :from and :to
                    """
    )
    Page<User> findUsersBetweenAges(Integer from, Integer to, Pageable pageable);

}
