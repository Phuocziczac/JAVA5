package edu.poly.shop.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import edu.poly.shop.domain.OrderDetail;

public interface OrderDetailService {

	void deleteAll();

	void deleteAll(Iterable<? extends OrderDetail> entities);

	OrderDetail getReferenceById(Integer id);

	void deleteAllById(Iterable<? extends Integer> ids);

	void delete(OrderDetail entity);

	OrderDetail getById(Integer id);

	void deleteById(Integer id);

	long count();

	OrderDetail getOne(Integer id);

	void deleteAllInBatch();

	void deleteAllByIdInBatch(Iterable<Integer> ids);

	boolean existsById(Integer id);

	void deleteAllInBatch(Iterable<OrderDetail> entities);

	Optional<OrderDetail> findById(Integer id);

	void deleteInBatch(Iterable<OrderDetail> entities);

	List<OrderDetail> findAllById(Iterable<Integer> ids);

	List<OrderDetail> findAll();

	<S extends OrderDetail> List<S> saveAllAndFlush(Iterable<S> entities);

	<S extends OrderDetail> S saveAndFlush(S entity);

	Page<OrderDetail> findAll(Pageable pageable);

	void flush();

	List<OrderDetail> findAll(Sort sort);

	<S extends OrderDetail> List<S> saveAll(Iterable<S> entities);

	<S extends OrderDetail> S save(S entity);

	Page<OrderDetail> findByOrderDetailIdContaining(int OrderDetailId, Pageable pageable);

	


	
}
