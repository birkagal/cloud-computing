package user;

import java.util.ArrayList;

public class UserWithoutPassowrd {
	private String email;
	private UserName name;
	private ArrayList<String> roles;
	private String birthdate;

	public UserWithoutPassowrd() {
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public UserName getName() {
		return name;
	}

	public void setName(UserName name) {
		this.name = name;
	}


	public ArrayList<String> getRoles() {
		return roles;
	}

	public void setRoles(ArrayList<String> roles) {
		this.roles = roles;
	}

	public String getBirthdate() {
		return birthdate;
	}

	public void setBirthdate(String birthdate) {
		this.birthdate = birthdate;
	}

	@Override
	public String toString() {
		return "User [email=" + email + ", name=" + name + ", roles=" + roles
				+ ", birthdate=" + birthdate + "]";
	}
}
