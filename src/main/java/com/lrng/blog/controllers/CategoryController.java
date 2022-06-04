package com.lrng.blog.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lrng.blog.payloads.ApiResponse;
import com.lrng.blog.payloads.CategoryDTO;
import com.lrng.blog.payloads.FindAllApiResponse;
import com.lrng.blog.services.ICategoryService;

@RestController
@RequestMapping("api/category")
public class CategoryController {

	@Autowired
	private ICategoryService categoryService;

	@PostMapping("/save")
	public ResponseEntity<Object> createCategory(@Valid @RequestBody CategoryDTO categoryDTO) {

		CategoryDTO createdCategoryDTO = categoryService.createCategory(categoryDTO);
		return new ResponseEntity<Object>(new ApiResponse(null, createdCategoryDTO, true), HttpStatus.CREATED);
	}

	@PutMapping("/byCategoryId/{categoryId}")
	public ResponseEntity<Object> updateCategory(@Valid @RequestBody CategoryDTO categoryDTO,
			@PathVariable Integer categoryId) {

		CategoryDTO updatedCategoryDTO = categoryService.updateCategory(categoryId, categoryDTO);
		return new ResponseEntity<Object>(new ApiResponse(null, updatedCategoryDTO, true), HttpStatus.CREATED);
	}

	@GetMapping("/byCategoryId/{categoryId}/search")
	public ResponseEntity<Object> getCategory(@PathVariable Integer categoryId) {

		CategoryDTO categoryDTO = categoryService.getCategory(categoryId);
		return new ResponseEntity<Object>(new ApiResponse(null, categoryDTO, true), HttpStatus.OK);
	}

	@GetMapping("/all")
	public ResponseEntity<Object> getAllCategory(
			@RequestParam(value = "page", defaultValue = "0", required = false) Integer page,
			@RequestParam(value = "size", defaultValue = "10", required = false) Integer size) {

		FindAllApiResponse findAllApiResponse = categoryService.getAllCategory(page, size);
		return new ResponseEntity<Object>(new ApiResponse(null, findAllApiResponse, true), HttpStatus.OK);
	}

	@DeleteMapping("/byCategoryId/{categoryId}")
	public ResponseEntity<Object> deleteCategory(@PathVariable Integer categoryId) {

		categoryService.deleteCategory(categoryId);
		return new ResponseEntity<Object>(
				new ApiResponse(String.format("Successfully deleted Category with Id : %d", categoryId), null, true),
				HttpStatus.OK);
	}

	@DeleteMapping("/all")
	public ResponseEntity<Object> deleteAllCategory() {

		categoryService.deleteAllCategory();
		return new ResponseEntity<Object>(new ApiResponse("Successfully deleted All Categories", null, true),
				HttpStatus.OK);
	}

}
