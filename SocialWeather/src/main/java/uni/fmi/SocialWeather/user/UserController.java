package uni.fmi.SocialWeather.user;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
	
	private UserService userService;
	
	@Autowired
	public UserController(UserService userService) {
		this.userService = userService;
	}
	
	
	@PostMapping(path = "/register")
	public UserEntity register( @RequestParam(value = "email") String email, 
								@RequestParam(value = "username") String username, 
								@RequestParam(value = "password") String password, 
								@RequestParam(value = "repeatPassword") String repeatPassword) {
		
		return userService.registerUser(
				username, password, repeatPassword, email);	
		
	}
	
	@PostMapping(path = "/login")
	public String login(  @RequestParam(value = "username") String username, 
						  @RequestParam(value = "password") String password, 
						   HttpSession session) {
		
		UserEntity user = userService.login(username, password, session);
		
		if(user != null) {
			return "home.html";
		}else {
			return "error.html";
		}
		
	}
	
	@GetMapping(path = "/whoAmI")
	public ResponseEntity<Integer> loggedUserId(HttpSession session){
		
		UserEntity user = (UserEntity)session.getAttribute("loggedUser");
		
		if(user != null) {
			return new ResponseEntity<Integer>(user.getId(), HttpStatus.OK);			
		}else {
			return new ResponseEntity<Integer>(-1, HttpStatus.UNAUTHORIZED);
		}
	}
	
	@PostMapping(path = "/logout")
	public ResponseEntity<Boolean> logout(HttpSession session){
		
		UserEntity user = (UserEntity)session.getAttribute("loggedUser");
		
		if(user != null) {
			session.invalidate();
			
			return new ResponseEntity<Boolean>(true, HttpStatus.OK);
			
		}
		
		return new ResponseEntity<Boolean>(false, HttpStatus.I_AM_A_TEAPOT);
		
	}
	

}
