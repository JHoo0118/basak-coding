package com.basakcoding.basak;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan(basePackageClasses = BasakCodingProjApplication.class)
@SpringBootApplication
public class BasakCodingProjApplication {

	public static void main(String[] args) {
		SpringApplication.run(BasakCodingProjApplication.class, args);
	}

}
