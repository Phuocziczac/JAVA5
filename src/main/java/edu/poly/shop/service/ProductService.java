package edu.poly.shop.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import edu.poly.shop.domain.Product;

public interface ProductService {

	void deleteAll();

	void deleteAll(Iterable<? extends Product> entities);

	Product getReferenceById(Integer id);

	void deleteAllById(Iterable<? extends Integer> ids);

	void delete(Product entity);

	Product getById(Integer id);

	void deleteById(Integer id);

	long count();

	Product getOne(Integer id);

	void deleteAllInBatch();

	void deleteAllByIdInBatch(Iterable<Integer> ids);

	boolean existsById(Integer id);

	void deleteAllInBatch(Iterable<Product> entities);

	Optional<Product> findById(Integer id);

	void deleteInBatch(Iterable<Product> entities);

	List<Product> findAllById(Iterable<Integer> ids);

	List<Product> findAll();

	<S extends Product> List<S> saveAllAndFlush(Iterable<S> entities);

	<S extends Product> S saveAndFlush(S entity);

	Page<Product> findAll(Pageable pageable);

	void flush();

	List<Product> findAll(Sort sort);

	<S extends Product> List<S> saveAll(Iterable<S> entities);

	<S extends Product> S save(S entity);

	Page<Product> findByproductNameContaining(String ProductName, Pageable pageable);

	List<Object[]> getTotalProductsAndCategories();

	Page<Product> searchProducts(String keyword, Long categoryId, Pageable pageable);

	

	

	
	
}
