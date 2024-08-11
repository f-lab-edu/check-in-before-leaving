package com.membercontext.memberAPI.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/test")
public class TestController {

    @PostMapping
    public void test() {
        System.out.println("test Post on going");
    }
    @GetMapping
    public void test2() {
        System.out.println("test Get on going");
    }

}
