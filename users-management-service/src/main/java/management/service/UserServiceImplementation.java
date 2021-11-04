package management.service;

import static java.time.temporal.TemporalAdjusters.firstDayOfYear;
import static java.time.temporal.TemporalAdjusters.lastDayOfYear;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import management.exception.InvalidInputException;
import management.exception.UnauthorizedLoginException;
import management.exception.UserDoesntExistException;
import management.exception.UserExistException;
import management.model.UserBoundary;
import management.model.UserEntity;
import management.model.UserWithoutPasswordBoundary;

@Service
public class UserServiceImplementation implements UserService {

	private UserDAO userDAO;
	private Log log;
	private ServiceUtil util;

	@Autowired
	public UserServiceImplementation(UserDAO userDAO) {
		super();
		this.userDAO = userDAO;
		this.util = new ServiceUtil();
		this.log = LogFactory.getLog(UserServiceImplementation.class);
	}

	@Override
	@Transactional
	public UserWithoutPasswordBoundary store(UserBoundary newUser) {
		// Input must include an email and a password
		if (newUser.getEmail() == null)
			throw new InvalidInputException("New user must have an email.");
		if (newUser.getPassword() == null)
			throw new InvalidInputException("New user must have a password.");

		// Make sure newUser email is not already in database
		Optional<UserEntity> op = this.userDAO.findById(newUser.getEmail());
		if (op.isPresent())
			throw new UserExistException("Email already exist: " + newUser.getEmail());

		// No user found with the email, convert new user and add to database
		this.userDAO.save(util.fromBoundary(newUser));
		this.log.debug("Added user to database: " + newUser);

		// Convert to NoPassword boundary and return
		return util.toNoPassword(newUser);
	}

	@Override
	@Transactional(readOnly = true)
	public UserWithoutPasswordBoundary get(String email) {
		this.log.debug("Looking for user with email: " + email);

		// Look for user email in database, if not found throw a message
		Optional<UserEntity> op = this.userDAO.findById(email);
		if (!op.isPresent())
			throw new UserDoesntExistException("Email does not exist: " + email);

		UserBoundary user = util.toBoundary(op.get());
		this.log.debug("User found: " + user);

		// Convert to NoPassword boundary and return
		return util.toNoPassword(user);
	}

	@Override
	@Transactional(readOnly = true)
	public UserWithoutPasswordBoundary login(String email, String password) {
		// Look for user email in database, if not found throw a message
		Optional<UserEntity> op = this.userDAO.findById(email);
		if (!op.isPresent())
			throw new UserDoesntExistException("Email does not exist: " + email);

		UserBoundary user = util.toBoundary(op.get());

		// Validate the given password
		if (!user.getPassword().equals(password))
			throw new UnauthorizedLoginException("Wrong password: " + password);

		this.log.debug("User logged-in successfully: " + user);
		// Convert to NoPassword boundary and return
		return util.toNoPassword(user);
	}

	@Override
	@Transactional
	public void update(String email, UserBoundary updatedUser) {
		// Look for user email in database, if not found throw a message
		Optional<UserEntity> op = this.userDAO.findById(email);
		if (!op.isPresent())
			throw new UserDoesntExistException("Email does not exist: " + email);

		UserBoundary user = util.toBoundary(op.get());
		// Validate each new field is not null, then update
		if (updatedUser.getName() != null)
			user.setName(updatedUser.getName());
		if (updatedUser.getPassword() != null)
			user.setPassword(updatedUser.getPassword());
		if (updatedUser.getBirthdate() != null)
			user.setBirthdate(updatedUser.getBirthdate());
		if (updatedUser.getRoles() != null)
			user.setRoles(updatedUser.getRoles());
		this.log.debug("User updated in successfully: " + user);

		// Convert updated user to entity and store in database
		this.userDAO.save(util.fromBoundary(user));
	}

	@Override
	@Transactional
	public void deleteAll() {
		this.log.debug("Users database cleared.");
		this.userDAO.deleteAll();
	}

	@Override
	@Transactional(readOnly = true)
	public UserWithoutPasswordBoundary[] getAllFiltered(String criteriaType, String criteriaValue, int size, int page,
			String sortBy, String sortOrder) {
		// Validate sortOrder is ASC/DESC only
		if (!sortOrder.isEmpty() && !sortOrder.equals("ASC") && !sortOrder.equals("DESC"))
			throw new InvalidInputException("Invalid sortOrder. Use ASC/DESC or none. Received: " + sortOrder);
		// Validate sortBy is email/name/birthdate/roles
		sortBy = sortBy.toLowerCase();
		if (!sortBy.isEmpty() && !sortBy.equals("email") && !sortBy.equals("name") && !sortBy.equals("birthdate")
				&& !sortBy.equals("roles"))
			throw new InvalidInputException(
					"Invalid sortBy. Use email/name/birthdate/roles or none. Received: " + sortBy);

		List<UserEntity> entities;
		switch (criteriaType.toLowerCase()) {
		case "":
			Page<UserEntity> pageOfEntities = this.userDAO
					.findAll(PageRequest.of(page, size, Sort.Direction.fromString(sortOrder), sortBy));
			entities = pageOfEntities.getContent();
			break;
		case "byemaildomain":
			if (!Pattern.compile("([A-Z0-9]+\\.)+[A-Z0-9]{2,6}$", Pattern.CASE_INSENSITIVE).matcher(criteriaValue)
					.find())
				throw new InvalidInputException("Invalid Domain: " + criteriaValue);

			entities = this.userDAO.findAllByEmailContaining("@" + criteriaValue,
					PageRequest.of(page, size, Sort.Direction.fromString(sortOrder), sortBy));
			break;
		case "bybirthyear":
			if (criteriaValue.length() > 5 || !criteriaValue.matches("-?\\d+(\\.\\d+)?"))
				throw new InvalidInputException(
						"Invalid criteriaValue. Year must be an integer. Received: " + criteriaValue);

			// In order to match birthdate in given year, check if its between first day and
			// last day of the year
			LocalDate instance = LocalDate.now().withYear(Integer.parseInt(criteriaValue));
			LocalDate firstDay = instance.with(firstDayOfYear());
			LocalDate lastDay = instance.with(lastDayOfYear());
			entities = this.userDAO.findAllByBirthdateBetween(firstDay, lastDay,
					PageRequest.of(page, size, Sort.Direction.fromString(sortOrder), sortBy));
			break;
		case "byrole":
			entities = this.userDAO.findAllByRolesContaining(criteriaValue,
					PageRequest.of(page, size, Sort.Direction.fromString(sortOrder), sortBy));
			break;
		default:
			throw new InvalidInputException(
					"Invalid criteriaType. Use byEmailDomain/byBirthYear/byRole or none. Received: " + criteriaType);
		}

		ArrayList<UserWithoutPasswordBoundary> rv = new ArrayList<>();
		for (UserEntity entity : entities)
			rv.add(util.toNoPassword(util.toBoundary(entity)));

		return rv.toArray(new UserWithoutPasswordBoundary[0]);
	}
}
