package management.model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import management.exception.InvalidInputException;

@Entity
@Table(name = "USERS")
public class UserEntity {

	@Id
	private String email;
	private String name;
	private String password;
	private LocalDate birthdate;
	private String roles;

	public UserEntity() {
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Temporal(TemporalType.DATE)
	public LocalDate getBirthdate() {
		return birthdate;
	}

	public void setBirthdate(String birthdate) {
		try {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
			this.birthdate = LocalDate.parse(birthdate, formatter);
		} catch (DateTimeParseException e) {
			throw new InvalidInputException("Invalid birthdate: " + birthdate + " Enter in format dd-MM-yyyy.");
		}
		
	}

	public String getRoles() {
		return roles;
	}

	public void setRoles(String roles) {
		this.roles = roles;
	}
}