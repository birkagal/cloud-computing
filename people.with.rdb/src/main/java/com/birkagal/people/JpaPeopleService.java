package com.birkagal.people;

import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class JpaPeopleService implements PeopleService {
	private PeopleDao peopleDao;

	@Autowired
	public JpaPeopleService(PeopleDao peopleDao) {
		super();
		this.peopleDao = peopleDao;
	}

	@Override
	@Transactional
	public Person store(Person newPerson) {
		newPerson.setId(null);
		return this.fromEntity(this.peopleDao.save(this.toEntity(newPerson)));
	}

	@Override
	@Transactional(readOnly = true)
	public Person get(String id) {
		Optional<PersonEntity> op = this.peopleDao.findById(Long.parseLong(id));
		if (!op.isPresent())
			throw new RuntimeException("User does not exist.");

		return fromEntity(op.get());
	}

	@Override
	@Transactional(readOnly = true)
	public Person[] getAll(int page, int size) {
		Page<PersonEntity> pageOfEntities = this.peopleDao.findAll(PageRequest.of(page, size));

		ArrayList<Person> rv = new ArrayList<>();
		for (PersonEntity entity : pageOfEntities.getContent())
			rv.add(fromEntity(entity));

		return rv.toArray(new Person[0]);
	}

	@Override
	@Transactional
	public void update(String id, Person updatedPerson) {
		Optional<PersonEntity> op = this.peopleDao.findById(Long.parseLong(id));
		if (!op.isPresent())
			throw new RuntimeException("Person does not exist.");

		Person person = fromEntity(op.get());
		if (updatedPerson.getName() != null)
			person.setName(updatedPerson.getName());
		if (updatedPerson.getBirthdate() != null)
			person.setBirthdate(updatedPerson.getBirthdate());

		this.peopleDao.save(toEntity(person));
	}

	@Override
	@Transactional
	public void deleteAll() {
		this.peopleDao.deleteAll();
	}

	public PersonEntity toEntity(Person boundary) {
		PersonEntity rv = new PersonEntity();
		if (boundary.getId() != null) {
			rv.setId(Long.parseLong(boundary.getId()));
		}
		rv.setName(boundary.getName());
		rv.setBirthdate(Date.from(boundary.getBirthdate().atStartOfDay().atZone(ZoneId.systemDefault()).toInstant()));
		return rv;
	}

	public Person fromEntity(PersonEntity entity) {
		Person rv = new Person();
		if (entity.getId() != null) {
			rv.setId("" + entity.getId());
		}
		rv.setName(entity.getName());
		Date theDate = entity.getBirthdate();
		if (theDate instanceof java.sql.Date) {
			theDate = new Date(theDate.getTime());
		}

		rv.setBirthdate(theDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate());

		return rv;
	}
}
