package edu.poly.shop.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import edu.poly.shop.domain.Customer;
import edu.poly.shop.domain.Order;
import edu.poly.shop.domain.OrderDetail;
import edu.poly.shop.model.Report;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long>{
	 Page<Order> findByOrderId(Long orderId, Pageable pageable);
	 @Query("SELECT od FROM OrderDetail od WHERE od.orders.orderId = :orderId")
	    List<OrderDetail> findOrderDetailListByOrderId(Long orderId);
	 
	 @Query("SELECT new edu.poly.shop.model.Report(SUBSTRING(o.orderDate, 1, 7), SUM(o.amount)) " +
		       "FROM Order o " +
		       "GROUP BY SUBSTRING(o.orderDate, 1, 7)")

	    List<Report> getRevenueByMonth();
}
