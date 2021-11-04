package people;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

public interface PeopleDao extends PagingAndSortingRepository<PersonEntity, Long> {

	public List<PersonEntity> findAllByNameLike(@Param("pattern") String pattern, Pageable pageable);
}
