package uni.fmi.SocialWeather.comment;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import uni.fmi.SocialWeather.user.UserEntity;

@RestController
public class CommentController {

	private CommentService commentService;
	
	@Autowired
	public CommentController(CommentService commentService) {
		this.commentService = commentService;
	}
	
	
	@PostMapping(path = "/comment/add")
	public String addComment(@RequestParam(value = "city") String city, 
				@RequestParam(value= "temp") double temp, 
				@RequestParam(value= "description") String description, 
				@RequestParam(value = "icon") String icon,
				HttpSession session) {
		
		UserEntity user = (UserEntity)session.getAttribute("loggedUser");
		
		if(user != null) {
			
			CommentEntity comment = 
					commentService.addComment(
							city, temp, description, icon, user);
			
			if(comment != null) {
				//return String.valueOf(comment.getId());
				return comment.getId() + "";
			}
			
			return "Error: comment post caput....";
			
		}
		
		return "Error: Please log in to post comment... duh...";
		
	}
	
	
}
