package com.mint.cardscheme;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableKafka
public class CardSchemeApplication {

	public static void main(String[] args) {
		SpringApplication.run(CardSchemeApplication.class, args);
	}
    
        
        @Bean
	
	public RestTemplate restTemplate() {
		
		return new RestTemplate();
	}
}
