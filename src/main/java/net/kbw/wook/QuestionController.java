package net.kbw.wook;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.http.HttpResponse;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.websocket.Session;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import net.kbw.domain.AnswerRepository;
import net.kbw.domain.Question;
import net.kbw.domain.QuestionRepository;
import net.kbw.domain.User;
import net.kbw.domain.UserRepository;

@Controller
@RequestMapping("/questions")

public class QuestionController {

	@Autowired
	private QuestionRepository questionRepository;

	// 글 상세보기
	@GetMapping("/{id}")
	public String show(@PathVariable Long id, Model model, HttpSession session ) {
		Question question = questionRepository.findById(id).get();
		question.update(); //게시글믈 보게된다면. update 함수를 실행시켜 view를 1 증가 시킨다. //댓글수도 가지고옴
		questionRepository.save(question);
		model.addAttribute("question", question);
		return "qna/show";
	}
//글 수정하기 화면
	@GetMapping("/{id}/update")
	public String updateform(@PathVariable Long id, Model model, HttpSession session ,HttpServletResponse response) throws IOException {
		User suser = HttpSessionUtils.getUserFromSession(session);
		Question question = questionRepository.findById(id).get();
		// 로그인이 안됐을때 수정,삭제를 못하게함
		if (!HttpSessionUtils.isLoginUser(session)) {

			
			response.setContentType("text/html; charset=UTF-8");
	        PrintWriter out = response.getWriter();
	        out.println("<script>alert('로그인을 해주세요.'); history.go(-1);</script>");
	        out.flush(); 
	        
			model.addAttribute("question", question);
			return "users/loginForm";

		}
		// 로그인은 했으나 작성자가 아닐때 수정,삭제를 못하게함
		if (!question.isSameWriter(suser)) {
			response.setContentType("text/html; charset=UTF-8");
	        PrintWriter out = response.getWriter();
	        out.println("<script>alert('권한이 없습니다.'); history.go(-1);</script>");
	        out.flush(); 
	        

			model.addAttribute("question", question);
			return "users/loginForm";

		}
		question = questionRepository.findById(id).get();
		model.addAttribute("question", question);
		return "qna/updateform";
	}
//글 수정하기 
	@PostMapping("/{id}")
	public String update(@PathVariable Long id, Model model, String title, String contents) {
		Question question = questionRepository.findById(id).get();
		question.update(title, contents);
		questionRepository.save(question);
		return "redirect:/questions/{id}";
	}
//글 삭제하기
	@GetMapping("/{id}/delete")
	public String delete(@PathVariable Long id, HttpSession session, Model model ,HttpServletResponse response) throws IOException {

		User suser = HttpSessionUtils.getUserFromSession(session);
		Question question = questionRepository.findById(id).get();
		// 로그인이 안됐을때 수정,삭제를 못하게함
		if (!HttpSessionUtils.isLoginUser(session)) {
			
			response.setContentType("text/html; charset=UTF-8");
	        PrintWriter out = response.getWriter();
	        out.println("<script>alert('로그인을 해주세요.'); history.go(-1);</script>");
	        out.flush(); 
	        
			
			model.addAttribute("question", question);
			return "users/loginForm";

		}
		// 로그인은 했으나 작성자가 아닐때 수정,삭제를 못하게함
		if (!question.isSameWriter(suser)) {
			response.setContentType("text/html; charset=UTF-8");
	        PrintWriter out = response.getWriter();
	        out.println("<script>alert('권한이 없습니다.'); history.go(-1);</script>");
	        out.flush(); 
			model.addAttribute("question", question);
			return "users/loginForm";
		}
		
		
		
        
       
    	  
    		questionRepository.deleteById(id);
    	  
      
       
		
		
    		return "redirect:/";
	
	}

//글쓰기화면
	@GetMapping("/form")
	public String form(HttpSession session, Model model,HttpServletResponse response) throws IOException {

		// 로그인 안했을시 로그인 필요
		if (!HttpSessionUtils.isLoginUser(session)) {
			response.setContentType("text/html; charset=UTF-8");
	        PrintWriter out = response.getWriter();
	        out.println("<script>alert('로그인을 해주세요.'); history.go(-1);</script>");
	        out.flush(); 
	        

			//model.addAttribute(HttpSessionUtils.isLoginUser(session));

			return "users/loginForm";
		}

		return "qna/form";
	}
	// 글쓰기
	@PostMapping("")
	public String create(String title, String contents, HttpSession session, Model model) {

		User sessionUser = HttpSessionUtils.getUserFromSession(session);
		Question newQuestion = new Question(sessionUser, title, contents);
		questionRepository.save(newQuestion);

		return "redirect:/";

	}
	
	

	
	
}
