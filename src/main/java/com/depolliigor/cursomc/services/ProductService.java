package com.depolliigor.cursomc.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.depolliigor.cursomc.domain.Category;
import com.depolliigor.cursomc.domain.Product;
import com.depolliigor.cursomc.repositories.CategoryRepository;
import com.depolliigor.cursomc.repositories.ProductRepository;
import com.depolliigor.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class ProductService {

	@Autowired
	private ProductRepository repo;

	@Autowired
	private CategoryRepository categoryRepository;

	public Product find(Integer id) {
		Optional<Product> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException("Pedido de ID: " + id + " não encontrado!"));
	}

	public Page<Product> search(String name, List<Integer> ids, Integer page, Integer linesPerPage, String orderBy, String direction) {
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		List<Category> categories = categoryRepository.findAllById(ids);
		return repo.findDistinctByNameContainingAndCategoriesIn(name, categories, pageRequest);
	}
}
