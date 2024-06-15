package edu.poly.shop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import lombok.Data;


@SpringBootApplication

public class FoodPolyshopApplication {

	public static void main(String[] args) {
		SpringApplication.run(FoodPolyshopApplication.class, args);
	}

}
