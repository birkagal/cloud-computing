package management.service;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import management.model.UserBoundary;
import management.model.UserEntity;
import management.model.UserName;
import management.model.UserWithoutPasswordBoundary;

public class ServiceUtil {

	private ObjectMapper jackson;

	public ServiceUtil() {
		super();
		this.jackson = new ObjectMapper();
	}

	public UserEntity convertFromBoundary(UserBoundary boundary) {
		UserEntity entity = new UserEntity();
		entity.setEmail(boundary.getEmail());
		entity.setPassword(boundary.getPassword());

		entity.setBirthdate(boundary.getBirthdate());

		// User name format: FirstName$LastName
		entity.setName(boundary.getName().getFirst() + "$" + boundary.getName().getLast());

		String roles = this.marshal(boundary.getRoles());
		entity.setRoles(roles);
		return entity;
	}

	public UserBoundary convertToBoundary(UserEntity entity) {
		UserBoundary boundary = new UserBoundary();
		boundary.setEmail(entity.getEmail());
		boundary.setPassword(entity.getPassword());
		DateTimeFormatter formatters = DateTimeFormatter.ofPattern("dd-MM-yyyy");
		boundary.setBirthdate(entity.getBirthdate().format(formatters));

		// UserEntity name format is: FirstName$LastName
		String[] splittedUserName = entity.getName().split("\\$");
		UserName userName = new UserName(splittedUserName[0], splittedUserName[1]);
		boundary.setName(userName);

		// Found unmarshaling arraylist method here:
		// https://www.baeldung.com/jackson-collection-array
		ArrayList<String> roles = this.unmarshal(entity.getRoles(), new TypeReference<ArrayList<String>>() {
		});
		boundary.setRoles(roles);
		return boundary;
	}

	public UserWithoutPasswordBoundary convertToNoPassword(UserBoundary user) {
		UserWithoutPasswordBoundary userWithoutPassword = new UserWithoutPasswordBoundary();
		userWithoutPassword.setEmail(user.getEmail());
		userWithoutPassword.setBirthdate(user.getBirthdate());
		userWithoutPassword.setName(user.getName());
		userWithoutPassword.setRoles(user.getRoles());
		return userWithoutPassword;
	}

	public <T> T unmarshal(String json, TypeReference<T> type) {
		try {
			return this.jackson.readValue(json, type);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public String marshal(Object obj) {
		try {
			return this.jackson.writeValueAsString(obj);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
