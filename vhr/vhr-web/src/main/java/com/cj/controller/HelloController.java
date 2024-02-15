package com.cj.controller;

import com.cj.HelloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {
    @Autowired
    HelloService helloService;

    @GetMapping("/employee/basic")
    public String basic(){
        helloService.hello();
        return "basic";
    }

    @GetMapping("/hello")
    public String hello(){
        helloService.hello();
        return "hello";
    }

}
