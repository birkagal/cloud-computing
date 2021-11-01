package user;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import exception.InvalidInputException;

public class User {
	private String email;
	private UserName name;
	private String password;
	private ArrayList<String> roles;
	private String birthdate;

	public User() {
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		Pattern valid_email_regex = Pattern.compile("[A-Z0-9_.]+@([A-Z0-9]+\\.)+[A-Z0-9]{2,6}$",
				Pattern.CASE_INSENSITIVE);
		if (email == null) // If user doesn't have email
			throw new InvalidInputException("Email can't be null"); // Users must have email

		Matcher matcher = valid_email_regex.matcher(email);

		if (!matcher.find())
			throw new InvalidInputException("Invalid email: " + email);
		
		this.email = email;

	}

	public UserName getName() {
		return name;
	}

	public void setName(UserName name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		Pattern passPattern = Pattern.compile("\\p{Nd}");
		if(password.length()<5)
			throw new InvalidInputException("Password must contain at least five characters");
		//Matcher matcher = passPattern.matcher(password);

		if (!passPattern.matcher(password).find())
			throw new InvalidInputException("Password must contain at least one number");

		this.password = password;
	}


	public ArrayList<String> getRoles() {
		return roles;
	}

	public void setRoles(ArrayList<String> roles) {
		if(roles.isEmpty())
			throw new InvalidInputException("User must have at least one roll");
		for (String role:roles)
			if(role.isEmpty())
				throw new InvalidInputException("Role cannot be empty");
		this.roles = roles;
	}

	public String getBirthdate() {
		return birthdate;
	}

	public void setBirthdate(String birthdate) {
		Pattern passPattern = Pattern.compile( "^(0[1-9]|1[012])[-](0[1-9]|[12][0-9]|3[01])[-](19|20)\\d\\d$" );
		if (!passPattern.matcher(birthdate).find())
			throw new InvalidInputException("Invalid Birthdate");
		this.birthdate = birthdate;
	}

	@Override
	public String toString() {
		return "User [email=" + email + ", name=" + name + ", password=" + password + ", roles=" + roles
				+ ", birthdate=" + birthdate + "]";
	}
}
