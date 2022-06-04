package com.lrng.blog.services;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.lrng.blog.entities.Category;
import com.lrng.blog.exceptions.ResourceNotFoundException;
import com.lrng.blog.payloads.CategoryDTO;
import com.lrng.blog.payloads.FindAllApiResponse;
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
	public FindAllApiResponse getAllCategory(Integer page, Integer size, String sortBy, String direction) {

		Sort sort = (sortBy.equalsIgnoreCase(Direction.ASC.toString()) ? Sort.by(Direction.ASC, sortBy)
				: Sort.by(Direction.DESC, sortBy));

		Pageable pageable = PageRequest.of(page, size, sort);
		Page<Category> pageCategory = categoryRepo.findAll(pageable);

		List<CategoryDTO> categoryDTOList = pageCategory.getContent().stream().map(category -> convertToDTO(category))
				.collect(Collectors.toList());

		FindAllApiResponse findAllApiResponse = new FindAllApiResponse();
		findAllApiResponse.setContent(categoryDTOList);
		findAllApiResponse.setPageNumber(pageCategory.getNumber());
		findAllApiResponse.setPageSize(pageCategory.getSize());
		findAllApiResponse.setTotalElements(pageCategory.getTotalElements());
		findAllApiResponse.setTotalPages(pageCategory.getTotalPages());
		findAllApiResponse.setIsLastPage(pageCategory.isLast());

		return findAllApiResponse;
	}

	@Override
	public void deleteCategory(Integer categoryId) {

		Category category = categoryRepo.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("Category", " Id ", categoryId));
		categoryRepo.deleteById(categoryId);
	}

	@Override
	public void deleteAllCategory() {

		List<CategoryDTO> categoryDTOList = categoryRepo.findAll().stream().map((category) -> convertToDTO(category))
				.collect(Collectors.toList());

		for (CategoryDTO categoryDTO : categoryDTOList) {
			deleteCategory(categoryDTO.getCategoryId());
		}
	}

}
