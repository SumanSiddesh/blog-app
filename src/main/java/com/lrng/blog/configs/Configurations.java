package com.lrng.blog.configs;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

//@Configuration
//@EnableJpaRepositories({"com.lrng.blog.repositories.UserRepo"})
public class Configurations {

	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}
}
