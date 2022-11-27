package uni.fmi.SocialWeather.notification;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import uni.fmi.SocialWeather.user.UserEntity;

@Entity
@Table(name = "notification")
public class NotificationEntity implements Serializable{

	public static final String STATUS_REQUESTED = "requested";
	public static final String STATUS_DENIED = "denied";
	public static final String STATUS_ACCEPTED = "accepted";	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(length = 500)
	private String comment;
	
	@Column(length = 50)
	private String status;
	
	@ManyToOne
	@JoinColumn(referencedColumnName = "id")
	private UserEntity fromUser;
	
	@ManyToOne
	@JoinColumn(referencedColumnName = "id")
	private UserEntity toUser;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public UserEntity getFromUser() {
		return fromUser;
	}

	public void setFromUser(UserEntity fromUser) {
		this.fromUser = fromUser;
	}

	public UserEntity getToUser() {
		return toUser;
	}

	public void setToUser(UserEntity toUser) {
		this.toUser = toUser;
	}
	
	
}
