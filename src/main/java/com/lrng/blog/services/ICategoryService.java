package com.lrng.blog.services;

import java.util.List;

import com.lrng.blog.payloads.CategoryDTO;
import com.lrng.blog.payloads.FindAllApiResponse;

public interface ICategoryService {

	CategoryDTO createCategory(CategoryDTO categoryDTO);

	CategoryDTO updateCategory(Integer categoryId, CategoryDTO categoryDTO);

	CategoryDTO getCategory(Integer categoryId);

	FindAllApiResponse getAllCategory(Integer page, Integer size, String sortBy, String direction);

	void deleteCategory(Integer categoryId);

	void deleteAllCategory();

}
