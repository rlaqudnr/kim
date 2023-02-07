package net.kbw.wook;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import net.kbw.domain.Answer;
import net.kbw.domain.AnswerRepository;
import net.kbw.domain.Question;
import net.kbw.domain.QuestionRepository;
import net.kbw.domain.User;

@Controller

@RequestMapping("/questions/{questionId}/answers") //기본 URL이다. questionId는 게시물에 댓글을 넣을때 댓글 테이블에 몇번 게시물에 넣었는지 확인하기 위해
public class AnswerController {

	@Autowired
	private AnswerRepository answerRepository;
	@Autowired
	private QuestionRepository questionRepository;

	@GetMapping("/create")
	//댓글쓰기
	public String create(@PathVariable Long questionId, String contents, HttpSession session ,HttpServletResponse response) throws IOException {

		//로그인 안됐을때 댓글작성 불가 로그인페이지로 이동
		if (!HttpSessionUtils.isLoginUser(session)) {

			response.setContentType("text/html; charset=UTF-8");
	        PrintWriter out = response.getWriter();
	        out.println("<script>alert('로그인을 해주세요.'); history.go(-1);</script>");
	        out.flush(); 
	        
			return "redirect:/users/loginForm";
		}
		//
		User loginUser = HttpSessionUtils.getUserFromSession(session);    //현재 로그인한 id를 loginUser 에 넣어준다.
		Question question = questionRepository.findById(questionId).get(); //해당 게시물의 번호를 받아와서 조회를 한다 조회를 한뒤 question에 넣어준다.
		Answer answer = new Answer(loginUser, contents, question);         //그리고 question을  answer생성자에 넣어준다. answer와 question은 one to many , many to one
		                                                                   //관계기 때문에 question을 넣어주면 기본키인 게시물 번호가 들어간다. 
		                                                                   
		answerRepository.save(answer);
		return String.format("redirect:/questions/%d", questionId);

	}

	//댓글수정
	@GetMapping("/update/{id}")
	public String update(@PathVariable Long questionId, String contents) {

		
		Answer answer = answerRepository.findById(questionId).get();
	
		answer.update(contents);
	
		answerRepository.save(answer);
		
		
	
		return "redirect:/questions/{id}";
	}

	//댓글수정 화면
	@GetMapping("/updateform/{id}")
	public String updateform(@PathVariable Long questionId, Model model, HttpSession session,HttpServletResponse response) throws IOException {
		User suser = HttpSessionUtils.getUserFromSession(session);

		Answer answer = answerRepository.findById(questionId).get();

	
		if (!HttpSessionUtils.isLoginUser(session)) {

			response.setContentType("text/html; charset=UTF-8");
	        PrintWriter out = response.getWriter();
	        out.println("<script>alert('로그인을 해주세요.'); history.go(-1);</script>");
	        out.flush(); 
	        
			// 로그인이 안됐을때 로그인폼으로
			return "users/loginForm";
		}
		

		// 로그인은 했으나 작성자가 아닐때 수정,삭제를 못하게함
		if (!answer.isSameWriter(suser)) {

			response.setContentType("text/html; charset=UTF-8");
	        PrintWriter out = response.getWriter();
	        out.println("<script>alert('권한이 없습니다.'); history.go(-1);</script>");
	        out.flush(); 
	        
			return "users/loginForm";

		}
		model.addAttribute("answers", answerRepository.findById(questionId).get());

		return "qna/AnswerForm";

	}

    //댓글 삭제
	@GetMapping("/delete/{id}")
	public String delete(@PathVariable Long questionId, HttpSession session,HttpServletResponse response) throws IOException {

		User suser = HttpSessionUtils.getUserFromSession(session);
		Answer answer = answerRepository.findById(questionId).get();

		if (!HttpSessionUtils.isLoginUser(session)) {
			// 로그인이 안됐을때 로그인폼으로
			response.setContentType("text/html; charset=UTF-8");
	        PrintWriter out = response.getWriter();
	        out.println("<script>alert('로그인을 해주세요.'); history.go(-1);</script>");
	        out.flush(); 
	        
			
			return "users/loginForm";
		}

		// 로그인은 했으나 작성자가 아닐때 수정,삭제를 못하게함
		if (!answer.isSameWriter(suser)) {
			
			response.setContentType("text/html; charset=UTF-8");
	        PrintWriter out = response.getWriter();
	        out.println("<script>alert('권한이 없습니다 .'); history.go(-1);</script>");
	        out.flush(); 
	        

			return "users/loginForm";

		}
         
		 
		answerRepository.deleteById(questionId);
		
		
		

		return "redirect:/questions/{id}";

	}
}
