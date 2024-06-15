package edu.poly.shop.model;

import java.io.Serializable;

import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountDto implements Serializable {
	@NotNull
	private String username;
	@NotEmpty
	@Length(min = 8, max = 50)
	private String password;
	@NotNull
	private boolean role = false;
	private Boolean isEdit = false;
}
