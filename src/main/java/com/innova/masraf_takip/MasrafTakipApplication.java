package com.innova.masraf_takip;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class MasrafTakipApplication {

	public static void main(String[] args) {
		SpringApplication.run(MasrafTakipApplication.class, args);
	}

}
