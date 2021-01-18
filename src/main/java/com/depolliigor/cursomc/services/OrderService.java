package com.depolliigor.cursomc.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.depolliigor.cursomc.domain.Order;
import com.depolliigor.cursomc.repositories.OrderRepository;
import com.depolliigor.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class OrderService {
	
	@Autowired
	private OrderRepository repo;
	
	public Order find(Integer id) {
		Optional<Order> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Pedido de ID: " + id + " não encontrado!"));
	}
	
}
