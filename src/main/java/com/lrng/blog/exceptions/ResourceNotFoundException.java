package com.lrng.blog.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ResourceNotFoundException extends RuntimeException {

	String resourceName;
	String fieldName;
	Object fieldValue;

	public String toStringCustom() {
		return String.format("%s not found with %s : %d", resourceName, fieldName, fieldValue);
	}
}
