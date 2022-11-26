package uni.fmi.SocialWeather.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

	private UserRepository userRepository;
	private WebSecurityConfig webSecurityConfig;
	
	@Autowired
	public UserService(UserRepository userRepository, 
			WebSecurityConfig webSecurityConfig) {
		this.userRepository = userRepository;
		this.webSecurityConfig = webSecurityConfig;		
	}
	
	public UserEntity registerUser(String username, String password,
			String repeatPassword, String email) {
		
		if(		username.isBlank()  || 
				email.isBlank() 	||
				password.isBlank()  || 
				!password.equals(repeatPassword)) {
			return null;
		}
		
		UserEntity user = new UserEntity(username, password, email);
		
		return userRepository.saveAndFlush(user);		
		
	}
	
	
}
