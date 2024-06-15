package edu.poly.shop.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import edu.poly.shop.domain.Category;
import edu.poly.shop.domain.Customer;
import edu.poly.shop.repository.CustomerRepository;
import edu.poly.shop.service.CustomerService;

@Service
public class CustomerServiceImpl  implements CustomerService{

	CustomerRepository customerRepository;

	public CustomerServiceImpl(CustomerRepository customerRepository) {
		super();
		this.customerRepository = customerRepository;
	}

	@Override
	public <S extends Customer> S save(S entity) {
		return customerRepository.save(entity);
	}

	@Override
	public Page<Customer> findByNameContaining(String name, Pageable pageable) {
		return customerRepository.findByNameContaining(name, pageable);
	}

	@Override
	public <S extends Customer> List<S> saveAll(Iterable<S> entities) {
		return customerRepository.saveAll(entities);
	}

	@Override
	public List<Customer> findAll(Sort sort) {
		return customerRepository.findAll(sort);
	}

	@Override
	public void flush() {
		customerRepository.flush();
	}

	@Override
	public Page<Customer> findAll(Pageable pageable) {
		return customerRepository.findAll(pageable);
	}

	@Override
	public <S extends Customer> S saveAndFlush(S entity) {
		return customerRepository.saveAndFlush(entity);
	}

	@Override
	public <S extends Customer> List<S> saveAllAndFlush(Iterable<S> entities) {
		return customerRepository.saveAllAndFlush(entities);
	}

	@Override
	public List<Customer> findAll() {
		return customerRepository.findAll();
	}

	@Override
	public List<Customer> findAllById(Iterable<Long> ids) {
		return customerRepository.findAllById(ids);
	}

	@Override
	public void deleteInBatch(Iterable<Customer> entities) {
		customerRepository.deleteInBatch(entities);
	}

	@Override
	public Optional<Customer> findById(Long id) {
		return customerRepository.findById(id);
	}

	@Override
	public void deleteAllInBatch(Iterable<Customer> entities) {
		customerRepository.deleteAllInBatch(entities);
	}

	@Override
	public boolean existsById(Long id) {
		return customerRepository.existsById(id);
	}

	@Override
	public void deleteAllByIdInBatch(Iterable<Long> ids) {
		customerRepository.deleteAllByIdInBatch(ids);
	}

	@Override
	public void deleteAllInBatch() {
		customerRepository.deleteAllInBatch();
	}

	@Override
	public Customer getOne(Long id) {
		return customerRepository.getOne(id);
	}

	@Override
	public long count() {
		return customerRepository.count();
	}

	@Override
	public void deleteById(Long id) {
		customerRepository.deleteById(id);
	}

	@Override
	public Customer getById(Long id) {
		return customerRepository.getById(id);
	}

	@Override
	public void delete(Customer entity) {
		customerRepository.delete(entity);
	}

	@Override
	public void deleteAllById(Iterable<? extends Long> ids) {
		customerRepository.deleteAllById(ids);
	}

	@Override
	public Customer getReferenceById(Long id) {
		return customerRepository.getReferenceById(id);
	}

	@Override
	public void deleteAll(Iterable<? extends Customer> entities) {
		customerRepository.deleteAll(entities);
	}

	@Override
	public void deleteAll() {
		customerRepository.deleteAll();
	}
	
}
