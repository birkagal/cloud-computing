package com.birkagal.people.service;

import com.birkagal.people.model.PersonEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PeopleDao extends PagingAndSortingRepository<PersonEntity, String> {

    List<PersonEntity> findAllByBirthdateEpocLessThan(
            @Param("maxDate") long maxDate,
            Pageable pageable);

    List<PersonEntity> findAllByNameEquals(
            @Param("name") String name,
            Pageable pageable);
}
