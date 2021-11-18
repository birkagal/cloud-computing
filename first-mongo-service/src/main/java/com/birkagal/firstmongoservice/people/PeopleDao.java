package com.birkagal.firstmongoservice.people;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PeopleDao extends //MongoRepository<PersonEntity, String>{ 
        PagingAndSortingRepository<PersonEntity, String> {

    public List<PersonEntity> findAllByBirthdateEpocLessThan(
            @Param("maxDate") long maxDate,
            Pageable pageable);

    public List<PersonEntity> findAllByNameEquals(@Param("name") String name, Pageable pageable);
}
