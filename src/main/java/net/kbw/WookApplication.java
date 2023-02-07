package net.kbw;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.SpringBootCondition;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class WookApplication  extends SpringBootServletInitializer{
	
	
	
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		// TODO Auto-generated method stub
		return builder.sources(WookApplication.class);
		
		//
		
	}

	public static void main(String[] args) {
		
		//
		SpringApplication.run(WookApplication.class, args);
		
		
	}

}
