package people;

import java.time.LocalDate;
import java.util.Random;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class Init implements CommandLineRunner {
	@Autowired
	private PeopleService people;

	@Override
	public void run(String... args) throws Exception {
		Random rand = new Random(System.currentTimeMillis());
		Stream.of("Jane", "Jill", "James", "Jeff").map(name -> {
			Person p = new Person();
			p.setName(name);
			p.setBirthdate(LocalDate.of(rand.nextInt(50) + 1950, rand.nextInt(12) + 1, rand.nextInt(28) + 1));
			return p;
		}).forEach(this.people::store);
	}
}
