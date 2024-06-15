package edu.poly.shop.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import edu.poly.shop.domain.Order;
import edu.poly.shop.domain.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer>{
	Page<Product> findByproductNameContaining(String ProductName,Pageable pageable);
	   Page<Product> findByCategoryIdAndProductNameContaining(Long categoryId, String productName, Pageable pageable);
	
	   @Query("SELECT COUNT(p) AS totalProducts, COUNT(DISTINCT p.category) AS totalCategories FROM Product p")
	    List<Object[]> getTotalProductsAndCategories();
	    
	    
}
