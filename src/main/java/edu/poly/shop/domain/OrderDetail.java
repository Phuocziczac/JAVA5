package edu.poly.shop.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name= "OrderDetail")
public class OrderDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_detail_id")
    private int orderDetailId;
    private Integer quantity;
    private Double unitPrice;
    private Boolean isEdit = false;
    @ManyToOne
    @JoinColumn(name = "orderId")
    @ToString.Exclude
    private Order orders;
    
    @ManyToOne
    @JoinColumn(name = "ProductID")
    @ToString.Exclude
    private Product product;
    
    // getters and setters
}
