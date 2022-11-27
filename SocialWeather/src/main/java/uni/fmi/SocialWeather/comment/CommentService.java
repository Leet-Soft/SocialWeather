package uni.fmi.SocialWeather.comment;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import uni.fmi.SocialWeather.user.UserEntity;

@Service
public class CommentService {

	private CommentRepository commentRepository;
	
	@Autowired
	public CommentService(CommentRepository commentRepository) {
		this.commentRepository = commentRepository;
	}
		
	public CommentEntity addComment(String city, double temp, String description, String icon, UserEntity user) {
			
		CommentEntity comment = new CommentEntity();
		comment.setCity(city);
		comment.setDescription(description);
		comment.setIcon(icon);
		comment.setTemp(temp);
		comment.setUser(user);		
		
		return commentRepository.saveAndFlush(comment);
	}
	
	public List<CommentEntity> getAll(){
		return commentRepository.findAll();
	}

	public ResponseEntity<Boolean> deleteComment(int id) {
		
		Optional<CommentEntity> optinalComment 
				= commentRepository.findById(id);
		
		if(optinalComment.isPresent()) {
			
			CommentEntity comment = optinalComment.get();
			
			commentRepository.delete(comment);
	
			return new ResponseEntity<Boolean>(true, HttpStatus.OK);
			
		}		
		
		return new ResponseEntity<Boolean>(false, HttpStatus.NOT_FOUND);
	}

}
