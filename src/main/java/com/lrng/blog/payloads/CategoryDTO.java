package com.lrng.blog.payloads;

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
public class CategoryDTO {

	private Integer categoryId;

	@NotNull
	@Length(min = 3, max = 40, message = "Category Title must be minimum of 3 and maximum of 40 characters.")
	private String categoryTitle;

	@NotNull(message = "Category Description cannot be null or empty")
	private String categoryDescription;

}
