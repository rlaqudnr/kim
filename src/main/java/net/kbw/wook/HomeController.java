package net.kbw.wook;

//import org.h2.index.PageBtreeLeaf;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import net.kbw.domain.Question;
import net.kbw.domain.QuestionRepository;
import net.kbw.domain.Serch;
  
@Controller
public class HomeController {
	@Autowired
	private QuestionRepository questionRepository;
	//검색 페이징이 된 게시글 리스트를 보여준다 
	@GetMapping("")
	public String home(Model model,
		@PageableDefault(sort = "id", direction = Sort.Direction.DESC, size = 5) Pageable pageable,String keyword)
	{  //id를 기준으로 내림차순 정렬,한페이지에 글5개 보이기
		           
		Serch serch = new Serch();
		//검색을 했을때 검색을 한 페이징을 보여준다
		if (serch.Serch(keyword)) {
			Page<Question> question = questionRepository.findByTitleContaining(keyword, pageable);
			model.addAttribute("previous", pageable.previousOrFirst().getPageNumber()); //이전페이지
			model.addAttribute("next", pageable.next().getPageNumber());	//다음페이지
			model.addAttribute("hasNext", question.hasNext()); //다음페이지가 존재?
			model.addAttribute("hasPrev", question.hasPrevious()); //이전페이지 존재?
			model.addAttribute("keyword", keyword); //검색어
			model.addAttribute("question", question); //게시글 제목 날짜 조회수 ..
			//검색을 했는데 값이 없을때 일반 페이징
			if (question.getTotalPages() == 0) {
				return "/user/index";
			}
			return "/user/index";
			//검색을 하지 않았을때. 일반 페이징 화면을 보여준다
		} else {
			Page<Question> question = questionRepository.findAll(pageable);
			model.addAttribute("question", question);
			model.addAttribute("previous", pageable.previousOrFirst().getPageNumber());
			model.addAttribute("next", pageable.next().getPageNumber());
			model.addAttribute("hasNext", question.hasNext());
			model.addAttribute("hasPrev", question.hasPrevious());
			keyword = "";
			model.addAttribute("keyword", keyword);
			return "/user/index";

		}

	}

}
