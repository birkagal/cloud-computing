package user;

import java.util.ArrayList;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;
import exception.InvalidInputException;
import exception.UnauthorizedLoginException;
import exception.UserExistException;

@Service
public class UserServiceImplementation implements UserService {

	private ArrayList<User> DATABASE = new ArrayList<User>();
	private Log log = LogFactory.getLog(UserServiceImplementation.class);

	@Override
	public UserWithoutPassowrd store(User newUser) {
		// Input must include an email and a password
		if (newUser.getEmail() == null)
			throw new InvalidInputException("New user must have an email.");
		if (newUser.getPassword() == null)
			throw new InvalidInputException("New user must have a password.");

		for (User user : DATABASE)
			if (user.getEmail().equals(newUser.getEmail()))
				throw new UserExistException("Email already exist: " + newUser.getEmail());

		DATABASE.add(newUser);
		this.log.debug("Added user to database: " + newUser);

		UserWithoutPassowrd rv = new UserWithoutPassowrd();
		rv.setEmail(newUser.getEmail());
		rv.setName(newUser.getName());
		rv.setBirthdate(newUser.getBirthdate());
		rv.setRoles(newUser.getRoles());
		return rv;
	}

	@Override
	public UserWithoutPassowrd get(String email) {
		this.log.debug("Looking for user with email: " + email);

		for (User user : DATABASE)
			if (user.getEmail().equals(email)) {
				this.log.debug("User found: " + user);

				UserWithoutPassowrd rv = new UserWithoutPassowrd();
				rv.setEmail(user.getEmail());
				rv.setBirthdate(user.getBirthdate());
				rv.setName(user.getName());
				rv.setRoles(user.getRoles());
				return rv;
			}
		throw new InvalidInputException("There is no user with email: " + email);
	}

	@Override
	public UserWithoutPassowrd login(String email, String password) {
		for (User user : DATABASE) {
			if (user.getEmail().equals(email)) {
				if (user.getPassword().equals(password)) {
					this.log.debug("User logged in successfully: " + user);

					UserWithoutPassowrd rv = new UserWithoutPassowrd();
					rv.setEmail(user.getEmail());
					rv.setBirthdate(user.getBirthdate());
					rv.setName(user.getName());
					rv.setRoles(user.getRoles());
					return rv;
				} else
					throw new UnauthorizedLoginException("Wrong password: " + password);
			}
		}
		throw new UnauthorizedLoginException("Email does not exist: " + email);
	}

	@Override
	public void update(String email, User updatedUser) {
		for (User user : DATABASE) {
			if (user.getEmail().equals(email)) {
				// Validate each new field is not null, then update
				if (updatedUser.getName() != null)
					user.setName(updatedUser.getName());
				if (updatedUser.getPassword() != null)
					user.setPassword(updatedUser.getPassword());
				if (updatedUser.getBirthdate() != null)
					user.setBirthdate(updatedUser.getBirthdate());
				if (updatedUser.getRoles() != null)
					user.setRoles(updatedUser.getRoles());
				return;
			}
		}
		throw new InvalidInputException("There is no user with email: " + email);
	}

	@Override
	public void delete() {
		DATABASE.clear();
	}
}
