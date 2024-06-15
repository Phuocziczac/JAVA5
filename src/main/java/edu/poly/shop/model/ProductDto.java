package edu.poly.shop.model;

import java.io.Serializable;
import java.util.Date;

import org.springframework.web.multipart.MultipartFile;

import edu.poly.shop.domain.ProductStatus;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDto implements Serializable {
	
private int productID;
@NotEmpty
private String productName;
@NotNull
private int quantity;
@NotNull
private double unitPrice;
@NotNull
private MultipartFile image;  
String imgurl;
private String decription;
@NotNull
private double discount;
@NotEmpty
private String enteredDate;
@NotNull
private String status;
@NotNull
private Long categoryId;

private Boolean isEdit = false;
}
