package net.kbw.domain;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;

import org.apache.catalina.filters.ExpiresFilter.XServletOutputStream;
import org.hibernate.annotations.OnDeleteAction;

@Entity
public class Question {
	@Id // id속성 기본키로
	@GeneratedValue
	Long id;

	@ManyToOne
	private User writer;

	private LocalDateTime createDate;

	@Column(columnDefinition = "integer default 0", nullable = false)
	private int view;

	//댓글과 1:N 관계
	@OneToMany(mappedBy = "question", cascade = CascadeType.REMOVE)
	@OrderBy("id DESC")
	private List<Answer> answers;

	private int reply;

	private String title;
	@Lob
	private String contents;

	public Question() {

	}

	public Question(User writer, String title, String contents) {
		super();

		this.writer = writer;
		this.title = title;
		this.contents = contents;
		this.createDate = LocalDateTime.now();
	}

	
	//
	public boolean isSameWriter(User suser) {
		
		//로그인을 했으면 작성자 삭제,수정시 본인확인을 해야함
		//현재 로그인한 id와 작성자를 비교
		return this.writer.equals(suser);
		
	}
	
	//
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

////
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Question other = (Question) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	public String getFormatCreateDate() {
		if (createDate == null) {
			return "";
		}

		return createDate.format(DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm:ss"));
	}

	public void update(String title, String contents) {
		// TODO Auto-generated method stub
		this.title = title;
		this.contents = contents;
	}

	public void update() {

	

		this.view++;
		
		this.reply = answers.size();   //게시물의 댓글수를 가져온다. 
		                              // question은 게시물 조회시  최초 발동이 되는데 
		                               //answer랑 N대1 관계기 때문에 question이 실행되면 answer가 만들어지고 question기본키로 
		                               //answer 외래키를 조회하여 같은 게시물 번호를 찾아주고 size()메소드를 쓰면 몇개인지 가져온다

	}

	
}