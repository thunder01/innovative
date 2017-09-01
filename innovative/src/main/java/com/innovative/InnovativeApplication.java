package com.innovative;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;

@SpringBootApplication
@MapperScan(basePackages = "com.innovative")
public class InnovativeApplication {

	public static void main(String[] args) {

		SpringApplication.run(InnovativeApplication.class, args);
	}

	/*打war包时使用，然后将pom.xml中的jar改成war, 此类需要继承SpringBootServletInitializer*/
//	@Override
//	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
//		return builder.sources(InnovativeApplication.class);
//	}
}
