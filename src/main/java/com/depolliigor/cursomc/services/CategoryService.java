package com.depolliigor.cursomc.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.depolliigor.cursomc.domain.Category;
import com.depolliigor.cursomc.repositories.CategoryRepository;
import com.depolliigor.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class CategoryService {
	
	@Autowired
	private CategoryRepository repo;
	
	public Category find(Integer id) {
		Optional<Category> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Categoria de ID: " + id + " não encontrada!"));
	}
	
}
