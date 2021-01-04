package org.javaboy.vhr.controller;

import org.javaboy.vhr.model.Employee;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author JKXAING on 2020/8/9
 */

@RestController
public class HelloController {

    @GetMapping("/employee/basic/demo")
    public String demo(Employee e){
        System.out.println(e);
        return "Hello ";
    }

    @GetMapping("hello")
    public String hello(){
        return "Hello ";
    }

    @GetMapping("/employee/basic/hello")
    public String hello2(){
        return "/employee/basic/hello ";
    }

    @GetMapping("/employee/advanced/hello")
    public String hello3(){
        return "/employee/advanced/hello ";
    }
}
