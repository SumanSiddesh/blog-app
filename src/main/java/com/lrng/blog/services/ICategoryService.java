package com.lrng.blog.services;

import java.util.List;

import com.lrng.blog.payloads.CategoryDTO;

public interface ICategoryService {

	CategoryDTO createCategory(CategoryDTO categoryDTO);

	CategoryDTO updateCategory(Integer categoryId, CategoryDTO categoryDTO);

	CategoryDTO getCategory(Integer categoryId);

	List<CategoryDTO> getAllCategory();

	void deleteCategory(Integer categoryId);

	void deleteAllCategory();

}
