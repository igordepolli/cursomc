package com.depolliigor.cursomc.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.depolliigor.cursomc.domain.Address;
import com.depolliigor.cursomc.domain.City;
import com.depolliigor.cursomc.domain.Client;
import com.depolliigor.cursomc.domain.enums.ClientType;
import com.depolliigor.cursomc.dto.ClientDTO;
import com.depolliigor.cursomc.dto.ClientNewDTO;
import com.depolliigor.cursomc.repositories.AddressRepository;
import com.depolliigor.cursomc.repositories.ClientRepository;
import com.depolliigor.cursomc.services.exceptions.DataIntegrityException;
import com.depolliigor.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class ClientService {
	
	@Autowired
	private ClientRepository repo;
	
	@Autowired
	private AddressRepository addressRepository;
	
	public Client find(Integer id) {
		Optional<Client> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Cliente de ID: " + id + " não encontrado!"));
	}
	
	public List<Client> findAll() {
		return repo.findAll();
	}
	
	@Transactional
	public Client insert(Client obj) {
		obj.setId(null);
		obj = repo.save(obj);
		addressRepository.saveAll(obj.getAdresses());
		return obj;
	}
	
	public Client update(Client obj) {
		Client newObj = find(obj.getId());
		updateData(newObj, obj);
		return repo.save(newObj);
	}
	
	public void delete(Integer id) {
		find(id);
		
		try {
			repo.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possível excluir este cliente porque há pedidos relacionados a ele");
		}
	}
	
	public Page<Client> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		return repo.findAll(pageRequest);
	}
	
	public Client fromDTO(ClientDTO objDTO) {
		return new Client(objDTO.getId(), objDTO.getName(), objDTO.getEmail(), null, null);
	}
	
	public Client fromDTO(ClientNewDTO objDTO) {
		Client cli = new Client(null, objDTO.getName(), objDTO.getEmail(), objDTO.getCpfOrCnpj(), ClientType.toEnum(objDTO.getClientType()));
		City city = new City(objDTO.getCityId(), null, null);
		Address add = new Address(null, objDTO.getStreet(), objDTO.getNumber(), objDTO.getComplement(), objDTO.getNbhd(), objDTO.getCep(), cli, city);
		cli.getAdresses().add(add);
		cli.getPhones().add(objDTO.getPhone1());
		if (objDTO.getPhone2() != null) {
			cli.getPhones().add(objDTO.getPhone2());
		}
		if (objDTO.getPhone3() != null) {
			cli.getPhones().add(objDTO.getPhone3());
		}
		return cli;
	}
	
	private void updateData(Client newObj, Client obj) {
		newObj.setName(obj.getName());
		newObj.setEmail(obj.getEmail());
	}
	
}
