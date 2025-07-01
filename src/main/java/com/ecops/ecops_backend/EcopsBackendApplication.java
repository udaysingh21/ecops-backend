package com.ecops.ecops_backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;

@SpringBootApplication
@EnableGlobalMethodSecurity(prePostEnabled=true) // This annotation enables method-level security in Spring, allowing you to use annotations 
//like @PreAuthorize and @PostAuthorize to secure methods based on user roles or permissions.
public class EcopsBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(EcopsBackendApplication.class, args);
	}

}
