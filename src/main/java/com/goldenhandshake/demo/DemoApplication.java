package com.goldenhandshake.demo;

import com.goldenhandshake.demo.entities.User;
import com.goldenhandshake.demo.entities.Role;
import java.util.Arrays;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;

@SpringBootApplication
@EnableConfigurationProperties(ClientDetails.class)
public class DemoApplication{

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}
        @Autowired
        public void authenticationManager(AuthenticationManagerBuilder builder, UserRepository repo) throws Exception{
            System.out.println("hello ");
            if(repo.count()==0){
          
               repo.save(new User( "admin", "admin", Arrays.asList(new Role("USER"),new Role("Actuator")) ));
               System.out.println("saved");
           }
            builder.userDetailsService((username)-> {
                return new CustomUserDetailsService(repo.findByUsername(username));
            }
            );
        }
}
