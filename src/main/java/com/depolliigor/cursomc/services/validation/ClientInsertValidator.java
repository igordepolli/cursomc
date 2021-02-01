package com.depolliigor.cursomc.services.validation;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.depolliigor.cursomc.domain.enums.ClientType;
import com.depolliigor.cursomc.dto.ClientNewDTO;
import com.depolliigor.cursomc.resources.exceptions.FieldMessage;
import com.depolliigor.cursomc.services.validation.utils.BR;

public class ClientInsertValidator implements ConstraintValidator<ClientInsert, ClientNewDTO> {
	
	@Override
	public void initialize(ClientInsert ann) {
	}

	@Override
	public boolean isValid(ClientNewDTO objDto, ConstraintValidatorContext context) {
		
		List<FieldMessage> list = new ArrayList<>();
		
		if (objDto.getClientType().equals(ClientType.PESSOAFISICA.getCod()) && !BR.isValidCPF(objDto.getCpfOrCnpj()))			
			list.add(new FieldMessage("cpfOrCnpj", "CPF inválido!"));
		
		if (objDto.getClientType().equals(ClientType.PESSOAJURIDICA.getCod()) && !BR.isValidCNPJ(objDto.getCpfOrCnpj()))
			list.add(new FieldMessage("cpfOrCnpj", "CNPJ inválido!"));
		
		for (FieldMessage e : list) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(e.getMessage()).addPropertyNode(e.getFieldName())
					.addConstraintViolation();
		}
		return list.isEmpty();
	}
}