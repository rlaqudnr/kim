//package net.kbw.domain;
//
//import java.time.LocalDateTime;
//import java.time.format.DateTimeFormatter;
//
//import javax.persistence.Entity;
//import javax.persistence.ForeignKey;
//import javax.persistence.GeneratedValue;
//import javax.persistence.Id;
//import javax.persistence.JoinColumn;
//import javax.persistence.ManyToOne;
//
//@Entity
//public class Answer {
//	//
//
//	@ManyToOne
//	@JoinColumn(foreignKey = @ForeignKey(name = "fk_answer_to_question"))
//	private Question question;
//	
//	
//
//	public Answer() {
//		
//	}
//	
//
//	public Answer(User writer,String contents,Question question) {
//		this.question =question;
//		this.writer=writer;
//		this.contents=contents;
//		this.createDate=LocalDateTime.now();
//	}
//	
//	
//
//
//	@Id
//	@GeneratedValue
//	private Long id;
//
//	@ManyToOne
//	@JoinColumn(foreignKey = @ForeignKey(name = "fk_answer_writer"))
//	private User writer;
//	
//	
//	private LocalDateTime createDate;
//
//	
//	@SuppressWarnings("unused")
//	private String contents;
//	public String getFormatCreateDate() {
//		if(createDate == null) {
//			return "";
//		}
//		
//		return createDate.format(DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm:ss"));
//	}
//	
//	
//
//	
//	
//	@Override
//	public int hashCode() {
//		final int prime = 31;
//		int result = 1;
//		result = prime * result + ((id == null) ? 0 : id.hashCode());
//		return result;
//	}
//	
//	
//	
//	
//	public boolean equals(Object obj) {
//		if (this == obj)
//			return true;
//		if (obj == null)
//			return false;
//		if (getClass() != obj.getClass())
//			return false;
//		Question other = (Question) obj;
//		if (id == null) {
//			if (other.id != null)
//				return false;
//		} else if (!id.equals(other.id))
//			return false;
//		return true;
//	}
//
//
//	public void update(String contents) {
//   
//		this.contents=contents;
//
//		
//	}
//	
//}
//
//	
