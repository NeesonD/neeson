package com.neeson.account;

import com.neeson.account.support.FullBeanNameGenerator;
import com.neeson.config.EnableConfigServer;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;


/**
 * @author neeson
 */
@EnableConfigServer
@SpringBootApplication
public class AuthServerApplication {
	
	public static void main(String[] args) {
		new SpringApplicationBuilder(AuthServerApplication.class)
				.beanNameGenerator(new FullBeanNameGenerator())
				.build()
				.run(args);
	}

}
