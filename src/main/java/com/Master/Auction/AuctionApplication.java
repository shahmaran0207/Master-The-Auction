package com.Master.Auction;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.boot.SpringApplication;

@EnableScheduling
@SpringBootApplication(scanBasePackages = {"com.Master.Auction"})
public class AuctionApplication {

	public static void main(String[] args) {
		SpringApplication.run(AuctionApplication.class, args);
	}

}
