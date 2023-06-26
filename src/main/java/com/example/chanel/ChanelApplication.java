package com.example.chanel;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


@EnableSwagger2
@SpringBootApplication
public class ChanelApplication {

	public static void main(String[] args) {
		SpringApplication.run(ChanelApplication.class, args);
	}

}
