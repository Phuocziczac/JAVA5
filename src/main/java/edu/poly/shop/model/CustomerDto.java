package edu.poly.shop.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import edu.poly.shop.domain.Order;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerDto implements Serializable {
    
    private Long customerId;
    
    @NotEmpty(message = "Name is required")
    private String name;
    
    @Email(message = "Email should be valid")
    @NotEmpty(message = "Email is required")
    private String email;
    @NotEmpty(message = "Adress number is required")
    private String adress;
    
    @NotEmpty(message = "Phone number is required")
    @Size(min = 10, max = 15, message = "Phone number should be between 10 and 15 characters")
    private String phone;
    
    

    private String registerDate;

    private String status;

	private Boolean isEdit = false;
}
