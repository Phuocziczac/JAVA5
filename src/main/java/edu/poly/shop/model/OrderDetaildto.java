package edu.poly.shop.model;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDetaildto {
private int orderDetailId;
@NotNull
private Long orderId;
@NotNull
private int productId;
@NotNull
private int quantity;

private double unitPrice;
private Boolean isEdit = false;
	
}
