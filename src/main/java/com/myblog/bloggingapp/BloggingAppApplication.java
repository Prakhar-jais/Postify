package com.myblog.bloggingapp;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.myblog.bloggingapp.config.AppConstants;
import com.myblog.bloggingapp.entities.Role;
import com.myblog.bloggingapp.repositories.RoleRepo;


//implements CommandLineRunner 
@SpringBootApplication
public class BloggingAppApplication  implements CommandLineRunner  {

	@Autowired
	private RoleRepo roleRepo;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	public static void main(String[] args) {
		SpringApplication.run(BloggingAppApplication.class, args);
	}

	@Bean
	public ModelMapper modelMapper(){
		return new ModelMapper();		
	}

	@Override
	public void run(String... args) throws Exception {
		// TODO Auto-generated method stub
		System.out.println(this.passwordEncoder.encode("123"));
		
		try{

			// 1 time if roles nhi hai toh 

			Role role1 = new Role();
			role1.setId(AppConstants.ADMIN_USER);
			role1.setName("ADMIN_USER");

			Role role2 = new Role();
			role2.setId(AppConstants.NORMAL_USER);
			role2.setName("NORMAL_USER");

			List<Role> roles = List.of(role1,role2);
			List<Role>savedRoles = this.roleRepo.saveAll(roles);

		}
		catch(Exception e){
			e.printStackTrace();
		}

		
	}


}

