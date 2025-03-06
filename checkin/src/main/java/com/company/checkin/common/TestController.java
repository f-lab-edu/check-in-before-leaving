package com.company.checkin.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/test")
@Slf4j
public class TestController {

    @PostMapping
    public ResponseEntity<Void> test() {
        log.info("test on going");
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<Void> test2() {
        log.info("test2 on going");
        return ResponseEntity.ok().build();
    }
}
