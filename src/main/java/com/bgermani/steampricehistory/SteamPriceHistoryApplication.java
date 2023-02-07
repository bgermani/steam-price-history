package com.bgermani.steampricehistory;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(ConfigProperties.class)
public class SteamPriceHistoryApplication {

	public static void main(String[] args) {
		SpringApplication.run(SteamPriceHistoryApplication.class, args);
	}

}
