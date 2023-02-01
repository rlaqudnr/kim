package net.kbw.domain;



import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long>{

	User findByUserId(String UserId); //로그인시 해당 매개변수로 받은 UserId를 검색하여 id 존재여부를 확인


	

	

	
	
	
	

}
