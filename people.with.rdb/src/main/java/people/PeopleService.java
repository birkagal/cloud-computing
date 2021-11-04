package people;

public interface PeopleService {

	public Person store(Person newPerson);

	public Person get(String id);

	public Person[] getAll(int page, int size);

	public void update(String id, Person updatedPerson);

	public void deleteAll();
}
