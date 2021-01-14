package com.depolliigor.cursomc.domain;

import java.io.Serializable;

public class Category implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Integer idCategory;
	private String name;
	
	public Category() {
		
	}

	public Category(Integer idCategory, String name) {
		this.idCategory = idCategory;
		this.name = name;
	}

	public Integer getIdCategory() {
		return idCategory;
	}

	public void setIdCategory(Integer idCategory) {
		this.idCategory = idCategory;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idCategory == null) ? 0 : idCategory.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Category other = (Category) obj;
		if (idCategory == null) {
			if (other.idCategory != null)
				return false;
		} else if (!idCategory.equals(other.idCategory))
			return false;
		return true;
	}

}
