package edu.poly.shop.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Products")
public class Product implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ProductID")
    private int productID;

    @Column(name = "ProductName")
    private String productName;

    @Column(name = "CategoryID")
    private Long categoryId;

    @Column(name = "UnitPrice")
    private double unitPrice;

    @Column(name = "Quantity")
    private int quantity;

    @Column(name = "Image")
    private String image;


    @Column(name = "Discount")
    private double discount;

    @Column(name = "EnteredDate")
    private String enteredDate ;

   
    @Column(name = "status")
    public String status;
    private Boolean isEdit = false;
    @ManyToOne
    @JoinColumn(name = "CategoryID", insertable = false, updatable = false)
    @ToString.Exclude
    private Category category;

    @OneToMany
    @JoinColumn(name = "ProductID")
    private List<OrderDetail> orderDetails;


    // Getters and setters
}