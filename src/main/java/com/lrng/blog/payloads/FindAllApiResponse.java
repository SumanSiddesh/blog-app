package com.lrng.blog.payloads;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class FindAllApiResponse {

	private Object content;

	private Integer pageNumber;

	private Integer pageSize;

	private Long totalElements;

	private Integer totalPages;

	private Boolean isLastPage;

}
