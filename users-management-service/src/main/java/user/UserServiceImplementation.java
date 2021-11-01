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
	private Log log = LogFactory.getLog(UserServiceImplementation.class);
	private ArrayList<User> DB = new ArrayList<User>();

	@Override
	public UserWithoutPassowrd store(User newUser) {
		this.log.debug("Adding user to database: " + newUser);
		for (User user : DB) {
			if (user.getEmail().equals(newUser.getEmail())) {
				throw new UserExistException("Email already exist: " + newUser.getEmail());
			}
		}
		DB.add(newUser);
		UserWithoutPassowrd rv = new UserWithoutPassowrd();
		rv.setEmail(newUser.getEmail());
		rv.setBirthdate(newUser.getBirthdate());
		rv.setName(newUser.getName());
		rv.setRoles(newUser.getRoles());
		return rv;
	}

	@Override
	public UserWithoutPassowrd get(String email) {
		this.log.debug("requesting user with email to producer service: " + email);
		UserWithoutPassowrd rv = new UserWithoutPassowrd();
		for (User user : DB) {
			if (user.getEmail().equals(email)) {
				rv.setEmail(user.getEmail());
				rv.setBirthdate(user.getBirthdate());
				rv.setName(user.getName());
				rv.setRoles(user.getRoles());
				return rv;
			}
		}
		throw new InvalidInputException("User does not exist with this email: " + email);
	}

	@Override
	public UserWithoutPassowrd login(String email, String password) {
		UserWithoutPassowrd rv = new UserWithoutPassowrd();
		for (User user : DB) {
			if (user.getEmail().equals(email)) {
				if (user.getPassword().equals(password)) {
					rv.setEmail(user.getEmail());
					rv.setBirthdate(user.getBirthdate());
					rv.setName(user.getName());
					rv.setRoles(user.getRoles());
					return rv;
				} else
					throw new UnauthorizedLoginException("Incorrect Email or Password");
			}
		}
		throw new UnauthorizedLoginException("Email does not exist");
	}

	@Override
	public void deleteAll() {
		DB.clear();
	}

	@Override
	public void updateUser(String email ,UserWithoutPassowrd updatedUser) {
		for (User user : DB) {
			if (user.getEmail().equals(email)) {
				user.setBirthdate(updatedUser.getBirthdate());
				user.setName(updatedUser.getName());
				user.setRoles(updatedUser.getRoles());
				return;
			}
		}
		throw new InvalidInputException("User does not exist");

	}

}
