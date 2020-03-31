package com.neeson.account;

import com.neeson.account.support.FullBeanNameGenerator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;
import org.springframework.stereotype.Component;


/**
 * @author neeson
 */
@SpringBootApplication
public class AuthServerApplication {
	
	public static void main(String[] args) {
		new SpringApplicationBuilder()
				.beanNameGenerator(new FullBeanNameGenerator())
				.build()
				.run(AuthServerApplication.class, args);
	}

}
