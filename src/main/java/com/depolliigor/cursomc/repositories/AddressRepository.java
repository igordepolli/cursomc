package com.depolliigor.cursomc.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.depolliigor.cursomc.domain.Address;

@Repository
public interface AddressRepository extends JpaRepository<Address, Integer> {
	
}
