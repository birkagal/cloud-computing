package user;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
	private UserService userService;
	private Log log = LogFactory.getLog(UserServiceImplementation.class);

	@Autowired
	public UserController(UserService userService) {
		super();
		this.userService = userService;
	}

	@RequestMapping(path = "/users", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public UserWithoutPassowrd store(@RequestBody User newUser) {
		return this.userService.store(newUser);
	}
	
	@RequestMapping(path = "/users/{email}", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public void updateUser(@PathVariable("email") String email ,@RequestBody UserWithoutPassowrd updatedUser) {
		this.userService.updateUser(email,updatedUser);
	}

	@RequestMapping(path = "/users/{email}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public UserWithoutPassowrd get(@PathVariable("email") String email) {
		return this.userService.get(email);
	}

	@RequestMapping(path = "/users/login/{email}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public UserWithoutPassowrd login(@PathVariable("email") String email, @RequestParam (name="password", required = false, defaultValue = "") String password) {
		return this.userService.login(email, password);
	}

	@RequestMapping(path = "/users", method = RequestMethod.DELETE)
	public void deleteAll() {
		this.userService.deleteAll();
	}
}
