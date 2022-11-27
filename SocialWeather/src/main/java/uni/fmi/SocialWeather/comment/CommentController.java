package uni.fmi.SocialWeather.comment;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
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
	
	@GetMapping(path = "/comment/all")
	public ResponseEntity<List<CommentEntity>> 
					getAllComments(HttpSession session) {
		
		UserEntity user = (UserEntity)session.getAttribute("loggedUser");
		
		if(user != null) {
			
			return new ResponseEntity<List<CommentEntity>>
							(commentService.getAll(), HttpStatus.OK);
			
		}
		return new ResponseEntity<>(null,HttpStatus.UNAUTHORIZED);	
	}	
	
	@DeleteMapping(path = "/comment/delete")
	@Secured("ROLE_ADMIN")
	public ResponseEntity<Boolean> deleteComment(
										@RequestParam(value = "id")int id,
										HttpSession session){
		
		UserEntity user = (UserEntity)session.getAttribute("loggedUser");
		
		if(user == null) {
			return new ResponseEntity<Boolean>(false, HttpStatus.UNAUTHORIZED);
		}
				
		return commentService.deleteComment(id);
	}
}
