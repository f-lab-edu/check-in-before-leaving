package com.company.checkin.place.web;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/test")
public class TestController {

    @PostMapping
    public ResponseEntity<?> test() {
        System.out.println("test on going");
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<?> test2() {
        System.out.println("test2 on going");
        return ResponseEntity.ok().build();
    }
}
