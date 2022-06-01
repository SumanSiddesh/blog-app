package com.lrng.blog.services;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lrng.blog.entities.Category;
import com.lrng.blog.exceptions.ResourceNotFoundException;
import com.lrng.blog.payloads.CategoryDTO;
import com.lrng.blog.repositories.CategoryRepository;

@Service
public class CategoryServiceImpl implements ICategoryService {

	@Autowired
	private CategoryRepository categoryRepo;

	@Autowired
	private ModelMapper modelMapper;

	private Category convertToEntity(CategoryDTO categoryDTO) {
		return modelMapper.map(categoryDTO, Category.class);
	}

	private CategoryDTO convertToDTO(Category category) {
		return modelMapper.map(category, CategoryDTO.class);
	}

	@Override
	public CategoryDTO createCategory(CategoryDTO categoryDTO) {

		Category category = categoryRepo.save(convertToEntity(categoryDTO));
		return convertToDTO(category);
	}

	@Override
	public CategoryDTO updateCategory(Integer categoryId, CategoryDTO categoryDTO) {

		Category category = categoryRepo.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("Category", " Id ", categoryId));
		categoryDTO.setCategoryId(categoryId);
		category = categoryRepo.save(convertToEntity(categoryDTO));
		return convertToDTO(category);
	}

	@Override
	public CategoryDTO getCategory(Integer categoryId) {

		Category category = categoryRepo.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("Category", " Id ", categoryId));
		return convertToDTO(category);
	}

	@Override
	public List<CategoryDTO> getAllCategory() {

		List<Category> categoryList = categoryRepo.findAll();
		return categoryList.stream().map(category -> convertToDTO(category)).collect(Collectors.toList());
	}

	@Override
	public void deleteCategory(Integer categoryId) {

		Category category = categoryRepo.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("Category", " Id ", categoryId));
		categoryRepo.deleteById(categoryId);
	}

	@Override
	public void deleteAllCategory() {

		List<CategoryDTO> categoryDTOList = getAllCategory();

		for (CategoryDTO categoryDTO : categoryDTOList) {
			deleteCategory(categoryDTO.getCategoryId());
		}
	}

}
