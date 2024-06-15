package edu.poly.shop.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import edu.poly.shop.domain.Order;
import edu.poly.shop.domain.OrderDetail;
import edu.poly.shop.model.Report;
import edu.poly.shop.repository.OrderRepository;
import edu.poly.shop.service.OrderService;

@Service
public class OrderServiceImpl implements OrderService {

	OrderRepository orderRepository;

	public OrderServiceImpl(OrderRepository orderRepository) {
		super();
		this.orderRepository = orderRepository;
	}

	@Override
	public <S extends Order> S save(S entity) {
		return orderRepository.save(entity);
	}

	@Override
	public List<Report> getRevenueByMonth() {
		return orderRepository.getRevenueByMonth();
	}

	@Override
	public List<OrderDetail> findOrderDetailListByOrderId(Long orderId) {
		return orderRepository.findOrderDetailListByOrderId(orderId);
	}

	

	

	@Override
	public Page<Order> findByOrderId(Long orderId, Pageable pageable) {
		return orderRepository.findByOrderId(orderId, pageable);
	}

	@Override
	public <S extends Order> List<S> saveAll(Iterable<S> entities) {
		return orderRepository.saveAll(entities);
	}

	@Override
	public List<Order> findAll(Sort sort) {
		return orderRepository.findAll(sort);
	}

	@Override
	public void flush() {
		orderRepository.flush();
	}

	@Override
	public Page<Order> findAll(Pageable pageable) {
		return orderRepository.findAll(pageable);
	}

	@Override
	public <S extends Order> S saveAndFlush(S entity) {
		return orderRepository.saveAndFlush(entity);
	}

	@Override
	public <S extends Order> List<S> saveAllAndFlush(Iterable<S> entities) {
		return orderRepository.saveAllAndFlush(entities);
	}

	@Override
	public List<Order> findAll() {
		return orderRepository.findAll();
	}

	@Override
	public List<Order> findAllById(Iterable<Long> ids) {
		return orderRepository.findAllById(ids);
	}

	@Override
	public void deleteInBatch(Iterable<Order> entities) {
		orderRepository.deleteInBatch(entities);
	}

	@Override
	public Optional<Order> findById(Long id) {
		return orderRepository.findById(id);
	}

	@Override
	public void deleteAllInBatch(Iterable<Order> entities) {
		orderRepository.deleteAllInBatch(entities);
	}

	@Override
	public boolean existsById(Long id) {
		return orderRepository.existsById(id);
	}

	@Override
	public void deleteAllByIdInBatch(Iterable<Long> ids) {
		orderRepository.deleteAllByIdInBatch(ids);
	}

	@Override
	public void deleteAllInBatch() {
		orderRepository.deleteAllInBatch();
	}

	@Override
	public Order getOne(Long id) {
		return orderRepository.getOne(id);
	}

	@Override
	public long count() {
		return orderRepository.count();
	}

	@Override
	public void deleteById(Long id) {
		orderRepository.deleteById(id);
	}

	@Override
	public Order getById(Long id) {
		return orderRepository.getById(id);
	}

	@Override
	public void delete(Order entity) {
		orderRepository.delete(entity);
	}

	@Override
	public void deleteAllById(Iterable<? extends Long> ids) {
		orderRepository.deleteAllById(ids);
	}

	@Override
	public Order getReferenceById(Long id) {
		return orderRepository.getReferenceById(id);
	}

	@Override
	public void deleteAll(Iterable<? extends Order> entities) {
		orderRepository.deleteAll(entities);
	}

	@Override
	public void deleteAll() {
		orderRepository.deleteAll();
	}

	
	
	
}
