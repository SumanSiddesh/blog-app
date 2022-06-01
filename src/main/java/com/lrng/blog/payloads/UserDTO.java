package com.lrng.blog.payloads;

import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class UserDTO {

	private int id;

	@NotEmpty
	@Size(min = 3, message = "User name must be minimum of 3 characters.")
	private String name;

	@Email(message = "Email address is not valid")
	private String email;

	@NotEmpty
	@Size(min = 3, max = 10, message = "Password must be minimum of 3 and maximum of 10 characters.")
	@Pattern(regexp = ".*", message = "Password dosent satisfy req")
	private String password;

	@NotEmpty
	private String about;

}
