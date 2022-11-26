package uni.fmi.SocialWeather.user;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.mvc.condition.RequestConditionHolder;

import uni.fmi.SocialWeather.helper.Utils;

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
		
		UserEntity user = new UserEntity(username, Utils.hashMe(password), email);
		
		return userRepository.saveAndFlush(user);	
	}
	
	public UserEntity login(String username, String password, 
									HttpSession session) {
		
		UserEntity user = 
				userRepository.findUserByUsernameAndPassword(
						username, Utils.hashMe(password));
		
		if(user != null ) {
			
			session.setAttribute("loggedUser", user);
			
			UserDetails userDetails = 
					webSecurityConfig.userDetailService()
					.loadUserByUsername(username);
			
			if(userDetails != null) {
				Authentication auth = 
						new UsernamePasswordAuthenticationToken(
								userDetails.getUsername(),
								userDetails.getPassword(),
								userDetails.getAuthorities());
				
				SecurityContextHolder.getContext().setAuthentication(auth);
				
				ServletRequestAttributes attr = 
						(ServletRequestAttributes)RequestContextHolder
						.currentRequestAttributes();
				
				session.setAttribute("SPRING_SECURITY_CONTEXT"
						, SecurityContextHolder.getContext());				
			}
			
		}
		
		return user;
		
		
	}
	
	
}
