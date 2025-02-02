package com.db.trade.trade_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableJpaRepositories
@EnableScheduling
@SpringBootApplication
public class TradeServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(TradeServiceApplication.class, args);
	}

}
