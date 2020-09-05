package com.interviewtask.springbootsimpleecommerce;

import com.interviewtask.springbootsimpleecommerce.repositories.IProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringBootSimpleECommerceApplication implements CommandLineRunner {
	@Autowired
	private IProductRepository productRepository;

	public static void main(String[] args) {
		SpringApplication.run(SpringBootSimpleECommerceApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
	}
}
