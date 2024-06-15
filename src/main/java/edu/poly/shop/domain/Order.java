package edu.poly.shop.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="Orders")
public class Order implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long orderId;
    private String orderDate;
    private Double amount;
    private String status;


    private Boolean isEdit = false;
    @ManyToOne
    @JoinColumn(name = "CustomerID")
    private Customer customer;
    
    @OneToMany(mappedBy = "orders", cascade = CascadeType.ALL)
    @ToString.Exclude
    private List <OrderDetail> orderDetails;
  
    // getters and setters
}
