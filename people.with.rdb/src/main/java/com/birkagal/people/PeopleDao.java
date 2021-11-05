package com.birkagal.people;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PeopleDao extends PagingAndSortingRepository<PersonEntity, Long> {

    List<PersonEntity> findAllByNameLike(@Param("pattern") String pattern, Pageable pageable);
}
