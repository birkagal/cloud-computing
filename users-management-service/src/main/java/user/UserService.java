package user;

public interface UserService {

	public User store(User newUser);

	public User get(String email);

}
