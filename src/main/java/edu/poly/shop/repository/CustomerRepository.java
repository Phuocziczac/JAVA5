package edu.poly.shop.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import edu.poly.shop.domain.Category;
import edu.poly.shop.domain.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long>{
	  Page<Customer> findByNameContaining(String name,Pageable pageable);
}