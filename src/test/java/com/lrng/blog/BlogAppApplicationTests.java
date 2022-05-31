package com.lrng.blog;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.lrng.blog.repositories.UserRepo;

@SpringBootTest
class BlogAppApplicationTests {

	@Autowired
	private UserRepo userRepo;

	@Test
	void contextLoads() {
	}

	@Test
	void userRepoTest() {

		String className = userRepo.getClass().getName();
		String packageName = userRepo.getClass().getPackageName();

		System.out.println("\nClass Name : " + className + " , Package Name : " + packageName + "\n");

	}

}
