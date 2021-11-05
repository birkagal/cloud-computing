package com.birkagal.management.service;

import com.birkagal.management.model.UserEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;


@Repository
public interface UserDAO extends PagingAndSortingRepository<UserEntity, String> {

    List<UserEntity> findAllByEmailContaining(String email, Pageable pageable);

    List<UserEntity> findAllByBirthdateBetween(LocalDate dateStart, LocalDate dateEnd, Pageable pageable);

    List<UserEntity> findAllByRolesContaining(String role, Pageable pageable);
}