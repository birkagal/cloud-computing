package user;

import javax.annotation.PostConstruct;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class UserServiceImplementation implements UserService {
	private String producerUrl;
	private RestTemplate restTemplate;
	private Log log = LogFactory.getLog(UserServiceImplementation.class);
	
	@Value("${dependency.service.key-value-store.url}")
	public void setProducerUrl(String producerUrl) {
		this.producerUrl = producerUrl;
	}
	
	@PostConstruct
	public void init() {
		this.restTemplate = new RestTemplate();
	}
	
	@Override
	public User store(User newUser) {
		this.log.debug("sending user to producer service: " + newUser);
		
		KeyUserPairBoundary userWithKey = this.restTemplate
			.postForObject(
				this.producerUrl, 
				newUser, KeyUserPairBoundary.class);
		
		this.log.debug("received from producer service: " + userWithKey);
		return newUser;
	}
	
	@Override
	public User get(String email) {
		return null;
//		this.log.debug("requesting user with email to producer service: " + email);
//		
//		KeyUserPairBoundary userWithKey = this.restTemplate
//			.getForObject(
//				this.producerUrl + "/{key}", 
//				KeyUserPairBoundary.class, key);
//		
//		this.log.debug("received from producer service: " + personWithKey);
//		
//		User rv = new User();
//		rv.setBirthdate(personWithKey.getValue().getBirthdate());
//		rv.setName(personWithKey.getValue().getName());
//		rv.setId(personWithKey.getKey());
//
//		this.log.trace("sending back to consumer: " + rv);
//		
//		return rv;
	}

}
