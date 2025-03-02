package com.company.member.web.controller.member;

import com.company.member.common.aop.authentication.NoAuthentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {

    @PostMapping
    @NoAuthentication
    public void test() {
        System.out.println("test Post on going");
    }

    @GetMapping
    @NoAuthentication
    public void test2() {
        System.out.println("test Get on going");
    }

}
