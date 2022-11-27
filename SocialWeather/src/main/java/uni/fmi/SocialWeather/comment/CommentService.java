package uni.fmi.SocialWeather.comment;

import org.springframework.stereotype.Service;

import uni.fmi.SocialWeather.user.UserEntity;

@Service
public class CommentService {

	private CommentRepository commentRepository;
	
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

}
