package com.shop.living;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@PropertySource("classpath:config/secu.properties")
public class LivingShopApplication {

	public static void main(String[] args) {
		SpringApplication.run(LivingShopApplication.class, args);
	}

}
