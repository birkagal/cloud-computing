package management.service;

import management.model.UserBoundary;
import management.model.UserWithoutPasswordBoundary;

public interface UserService {

	public UserWithoutPasswordBoundary store(UserBoundary newUser);

	public UserWithoutPasswordBoundary get(String email);

	public UserWithoutPasswordBoundary login(String email, String password);

	public void update(String email, UserBoundary updatedUser);

	public void deleteAll();

	public UserWithoutPasswordBoundary[] getAllFiltered(String criteriaType, String criteriaValue, int size, int page,
			String sortBy, String sortOrder);
}
