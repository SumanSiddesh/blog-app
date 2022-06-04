package com.lrng.blog.payloads;

import java.util.Date;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PostDTO {

	@NotNull
	@Length(min = 3, max = 15, message = "Post Title must be minimum of 3 and maximum of 15 characters.")
	private String title;

	@NotNull
	@Length(min = 3, max = 1000, message = "Post Content must be minimum of 3 and maximum of 1000 characters.")
	private String content;

	private String imageName;

	private Date createdDate;
	
	private Date updatedDate;

	private CategoryDTO category;

	private UserDTO user;

}
