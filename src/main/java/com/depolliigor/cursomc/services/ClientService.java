package com.depolliigor.cursomc.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.depolliigor.cursomc.domain.Client;
import com.depolliigor.cursomc.repositories.ClientRepository;
import com.depolliigor.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class ClientService {
	
	@Autowired
	private ClientRepository repo;
	
	public Client find(Integer id) {
		Optional<Client> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Cliente de ID: " + id + " não encontrada!"));
	}
	
}
