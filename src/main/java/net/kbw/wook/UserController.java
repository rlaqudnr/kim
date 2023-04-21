package net.kbw.wook;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.swing.JOptionPane;
import javax.transaction.Transactional;
import javax.websocket.Session;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import net.kbw.domain.Answer;
import net.kbw.domain.AnswerRepository;
import net.kbw.domain.Question;
import net.kbw.domain.QuestionRepository;
import net.kbw.domain.User;
import net.kbw.domain.UserRepository;

@Controller
@RequestMapping("/users")
public class UserController {
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private QuestionRepository questionRepository;
	
	@Autowired
	private AnswerRepository answerRepository;
	// 회원정보수정화면
	@GetMapping("/{id}/privacy")
	public String updatemy(@PathVariable Long id, Model model, HttpSession session) {

		User suser = HttpSessionUtils.getUserFromSession(session);

		if (!HttpSessionUtils.isLoginUser(session)) {

			// 로그인이 안됐을때 로그인폼으로
			return "users/loginForm";
		
		//
		
		} else {

			User user = userRepository.findById(id).get();

			model.addAttribute("user", user);
			return "user/privacy";
		}
	}

	// 회원가입페이지
	@GetMapping("/form")
	public String form() {

		return "user/form";

	}
	// 회원가입
		@PostMapping("")
		public String create(User user, HttpSession session) {

			userRepository.save(user);

			return "redirect:/";

		}
		
	// 회원수정
	@PostMapping("/{id}/update")
	public String update(@PathVariable Long id, User newUser) {
		System.out.println(id);
		User user = userRepository.findById(id).get();
		user.update(newUser);
		userRepository.save(user);
		return "redirect:/";
	}
	// 회원탈퇴
	@GetMapping("/{id}/delete")
	@Transactional
	public String delete(@PathVariable Long id, HttpSession session) {
		User user = userRepository.findById(id).get();
		//회원탈퇴시 회원이 쓴글,댓글을 삭제하는 코드 
		List<Question> question= questionRepository.findBywriter_id(id);
		List<Answer> answer= answerRepository.findBywriter_id(id);
	for (int i=1; i<=question.size(); i++) {
		//회원이 쓴 게시글 삭제
		questionRepository.deleteBywriter_id(id);
	}
	for (int i=1; i<=answer.size(); i++) {	
		//회원이 쓴 게시글 댓글
		answerRepository.deleteBywriter_id(id);
	}
	//회원탈퇴	
	userRepository.deleteById(id);
	session.removeAttribute(HttpSessionUtils.USER_SESSION_KEY);
		return "redirect:/";
		
	}

	// 로그인페이지
	@GetMapping("/loginForm")
	public String loginForm() {

		return "user/login";

	}

	// 로그인 백엔드
	@PostMapping("/login")
	public String login(String userId, String password, HttpSession session ,Model model ,HttpServletResponse response) throws IOException {
		User user = userRepository.findByUserId(userId);
		

		// 로그인시 아이디가 일치하지 않을때.
	if (user == null) {
		
		response.setContentType("text/html; charset=UTF-8");
        PrintWriter out = response.getWriter();
        out.println("<script>alert('아이디가 없습니다.'); history.go(-1);</script>");
        out.flush(); 
			//return "redirect:/users/loginForm";
        return "users/loginForm";
		}

		// 아이디는 일치하지만 비밀번호가 틀릴때
		if (!user.matchPassword(password)) {
			
			  response.setContentType("text/html; charset=UTF-8");
	            PrintWriter out = response.getWriter();
	            out.println("<script>alert('비밀번호가 일치하지 않습니다.'); history.go(-1);</script>");
	            out.flush(); 
				return "users/loginForm";

		}
		
	
	
     	// 로그인 성공후 세션담기
		session.setAttribute(HttpSessionUtils.USER_SESSION_KEY, user);
		return "redirect:/";
		

	}
	
	

	
	// 로그아웃
	@GetMapping("/logout")

	public String logout(HttpSession session) {
		

		session.removeAttribute(HttpSessionUtils.USER_SESSION_KEY);

		return "redirect:/";

	}

	
	//아이디 중복
	
	@ResponseBody
	@GetMapping("/idcheck")

	public String idcheck(Model model, String id) {

		User user = userRepository.findByUserId(id);

		if (user == null) {

			return "";
		} else {

			return "1";
		}
	}
}
