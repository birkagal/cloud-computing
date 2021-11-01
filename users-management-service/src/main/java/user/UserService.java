package user;

public interface UserService {

	public UserWithoutPassowrd store(User newUser);

	public UserWithoutPassowrd get(String email);

	public UserWithoutPassowrd login(String email, String password);
	
	public void updateUser(String email,UserWithoutPassowrd updatedUser);
	
	public void deleteAll();

}
