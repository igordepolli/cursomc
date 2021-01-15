package com.depolliigor.cursomc.domain.enums;

public enum ClientType {
	
	PESSOAFISICA(1, "Pessoa Física"),
	PESSOAJURIDICA(2, "Pessoa Jurídica");
	
	private Integer cod;
	private String description;
	
	private ClientType(Integer cod, String description) {
		this.cod = cod;
		this.description = description;
	}
	
	public Integer getCod() {
		return cod;
	}
	
	public String getDescription() {
		return description;
	}
	
	public static ClientType toEnum(Integer cod) {
		
		if (cod == null) {
			return null;
		}
		
		for (ClientType x : ClientType.values()) {
			if (cod.equals(x.getCod())) {
				return x;
			}
		}
		
		throw new IllegalArgumentException("Código " + cod + " inválido!");
	}
}
