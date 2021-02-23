package com.depolliigor.cursomc.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import com.depolliigor.cursomc.domain.Category;
import com.depolliigor.cursomc.domain.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {

    @Transactional(readOnly=true)
    @Query("SELECT DISTINCT obj FROM Product obj INNER JOIN obj.categories cat WHERE obj.name LIKE %:name% and cat IN :categories") //opcional juntamente com os parametros da linha abaixo
	Page<Product> findDistinctByNameContainingAndCategoriesIn(@Param("name") String name,@Param("categories") List<Category> categories, Pageable pageRequest);
	
}
