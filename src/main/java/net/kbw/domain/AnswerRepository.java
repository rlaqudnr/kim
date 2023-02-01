package net.kbw.domain;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AnswerRepository extends JpaRepository<Answer, Long>{

	List<Answer> findBywriter_id(Long id); //사용자가 쓴 댓글을 검색

	void deleteBywriter_id(Long id); // 사용자가 쓴 댓글 삭제


	//
	









}
