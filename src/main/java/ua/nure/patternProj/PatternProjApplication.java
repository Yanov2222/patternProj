package ua.nure.patternProj;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import ua.nure.patternProj.dao.mysql.entity.AutoCaretaker;

@SpringBootApplication
public class PatternProjApplication extends SpringBootServletInitializer {


	@Bean
	public AutoCaretaker caretaker(){
		return new AutoCaretaker();
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application){
		return  application.sources(PatternProjApplication.class);
	}

	public static void main(String[] args) {
		SpringApplication.run(PatternProjApplication.class,args);
	}

}
