package com.technogise.expensesharingapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class ExpenseSharingAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(ExpenseSharingAppApplication.class, args);
	}

}
