package com.membercontext;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@EnableAspectJAutoProxy
public class MemberContextApplication {

    public static void main(String[] args) {
        SpringApplication.run(MemberContextApplication.class, args);
    }

}
