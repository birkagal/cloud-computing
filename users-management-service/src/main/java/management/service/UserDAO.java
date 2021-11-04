package management.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import management.model.UserEntity;

@Repository
public interface UserDAO extends PagingAndSortingRepository<UserEntity, String> {

	public List<UserEntity> findAllByEmailContaining(String email, Pageable pageable);

	public List<UserEntity> findAllByBirthdateBetween(LocalDate dateStart, LocalDate dateEnd, Pageable pageable);

	public List<UserEntity> findAllByRolesContaining(String role, Pageable pageable);
}