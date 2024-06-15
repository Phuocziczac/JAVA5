package edu.poly.shop.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import edu.poly.shop.domain.Order;
import edu.poly.shop.domain.OrderDetail;
import edu.poly.shop.model.Report;

public interface OrderService {

	void deleteAll();

	void deleteAll(Iterable<? extends Order> entities);

	Order getReferenceById(Long id);

	void deleteAllById(Iterable<? extends Long> ids);

	void delete(Order entity);

	Order getById(Long id);

	void deleteById(Long id);

	long count();

	Order getOne(Long id);

	void deleteAllInBatch();

	void deleteAllByIdInBatch(Iterable<Long> ids);

	boolean existsById(Long id);

	void deleteAllInBatch(Iterable<Order> entities);

	Optional<Order> findById(Long id);

	void deleteInBatch(Iterable<Order> entities);

	List<Order> findAllById(Iterable<Long> ids);

	List<Order> findAll();

	<S extends Order> List<S> saveAllAndFlush(Iterable<S> entities);

	<S extends Order> S saveAndFlush(S entity);

	Page<Order> findAll(Pageable pageable);

	void flush();

	List<Order> findAll(Sort sort);

	<S extends Order> List<S> saveAll(Iterable<S> entities);

	<S extends Order> S save(S entity);



	List<OrderDetail> findOrderDetailListByOrderId(Long orderId);


	Page<Order> findByOrderId(Long orderId, Pageable pageable);

	List<Report> getRevenueByMonth();

}
