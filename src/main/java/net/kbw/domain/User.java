package net.kbw.domain;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity //db랑 연결.
public class User {
	@Id // id속성 기본키로
	@GeneratedValue
	private Long id;
	
	@Column(nullable=false, length = 20 ,unique =true )
	
	private String userId;
	

	public Long getId() {
		return id;
		
		
	}
	
	public boolean matchId(Long newId) {
		
		if(newId == null) {
			return false;
	}
		return newId.equals(id);
		
	}
	
	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}
	
	
	

	@Override
	public boolean equals(Object obj) {
		
		
		
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	public void setId(Long id) {
		this.id = id;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUserId() {
		return userId;
	}
	@Override
	public String toString() {
		return "User [userId=" + userId + ", password=" + password + ", name=" + name + ", email=" + email + "]";
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public boolean matchPassword(String newPassword) {
		
		
		
			if(newPassword ==null) {
				return false;
			}
			
			else {
		
			 
			
		       return newPassword.equals(password);
		
			}
	}
	

	

	

	
	

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	private String password;
	private String name;
	private String email;
	
	public void update(User newUser) {
		this.userId=newUser.userId;
		this.password = newUser.password;
		this.name = newUser.name;
		this.email = newUser.email;
		
	}
	
	
	

}
