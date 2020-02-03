package com.ccumiskey.payrollbyspring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * The @SpringBootApplication annotation is a *META*-annotation which pulls in component scanning, autoconfiguration
 * and property support. It will start up a servlet container to serve up the service.
 */
@SpringBootApplication
public class PayrollSpringApplication {

	public static void main(String[] args) {
		SpringApplication.run(PayrollSpringApplication.class, args);
	}

}
