package edu.poly.shop.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import edu.poly.shop.domain.Category;
import edu.poly.shop.domain.Customer;

public interface CustomerService {

	void deleteAll();

	void deleteAll(Iterable<? extends Customer> entities);

	Customer getReferenceById(Long id);

	void deleteAllById(Iterable<? extends Long> ids);

	void delete(Customer entity);

	Customer getById(Long id);

	void deleteById(Long id);

	long count();

	Customer getOne(Long id);

	void deleteAllInBatch();

	void deleteAllByIdInBatch(Iterable<Long> ids);

	boolean existsById(Long id);

	void deleteAllInBatch(Iterable<Customer> entities);

	Optional<Customer> findById(Long id);

	void deleteInBatch(Iterable<Customer> entities);

	List<Customer> findAllById(Iterable<Long> ids);

	List<Customer> findAll();

	<S extends Customer> List<S> saveAllAndFlush(Iterable<S> entities);

	<S extends Customer> S saveAndFlush(S entity);

	Page<Customer> findAll(Pageable pageable);

	void flush();

	List<Customer> findAll(Sort sort);

	<S extends Customer> List<S> saveAll(Iterable<S> entities);

	<S extends Customer> S save(S entity);

	Page<Customer> findByNameContaining(String name, Pageable pageable);

}
