package net.kbw.wook;

import javax.servlet.http.HttpSession;


//로그인 여부 확인하는 클래스

import net.kbw.domain.User;

public class HttpSessionUtils {

	public static final String USER_SESSION_KEY = "suser";

	public static boolean isLoginUser(HttpSession session) {

		//로그인 여부 확인 
		Object suser = session.getAttribute(USER_SESSION_KEY); //세션에 값이 있는지 확인후 리턴
		if (suser == null) {

			return false;
		}
		return true;
		//
	}

	//로그인 했으면 현재 세션을 리턴함
	public static User getUserFromSession(HttpSession session) {
		if (!isLoginUser(session)) {
			return null;
		}

		return (User) session.getAttribute(USER_SESSION_KEY);
		
		

	}

}
