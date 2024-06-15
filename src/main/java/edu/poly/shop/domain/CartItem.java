package edu.poly.shop.domain;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartItem {
	@Id
private int productId;
private String name;
private int quantity;
private double unitPrice;
}
