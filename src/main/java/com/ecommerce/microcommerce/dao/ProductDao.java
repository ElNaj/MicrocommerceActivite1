package com.ecommerce.microcommerce.dao;

import com.ecommerce.microcommerce.model.Product;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductDao extends JpaRepository<Product, Integer> {

    public List<Product> findAll();
    Product findById(int id);

    List<Product> findByPrixGreaterThan(int prix);
    public Product save(Product product);

    List<Product> findByNomLike(String recherche);

    List<Product> OrderByNomAsc();
}
