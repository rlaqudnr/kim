package net.kbw.domain;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;


@Service
public interface QuestionRepository extends JpaRepository<Question, Long>{
	
	Page<Question> findByTitleContaining(String keyword, Pageable pageable); //검색 후 페이징


	List<Question> findBywriter_id(Long id); // 탈퇴하려는 사용자의 게시물을 검색


	void deleteBywriter_id(Long id); // 게시물 삭제


	//
	
	}


