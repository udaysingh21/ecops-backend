// Controller in Spring Boot are java classes which are responsible for handling incoming HTTP requests and returning appropriate responses.
package com.ecops.ecops_backend.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController // This annotation indicates that this class is a controller where every method returns a domain object instead of a view.
@RequestMapping("/api") // This annotation maps HTTP requests to specific handler methods in the controller.
public class HelloController {

    @GetMapping("/hello")
    public String sayHello() {
        return "Hello, World!";
    }
}
