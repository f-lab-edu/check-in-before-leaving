package com.membercontext.memberAPI.web.controller;

import com.membercontext.memberAPI.domain.service.SignUpService;
import com.membercontext.memberAPI.web.form.SignUpForm;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/sign-in")
public class SignUpController {

    private SignUpService signUpService;

    @PostMapping
    public ResponseEntity<String> signIn(@Validated @RequestBody SignUpForm signUpForm) {

        return ResponseEntity.ok(signUpService.signUp(signUpForm));
    }

    @PutMapping
    public ResponseEntity<String> update() {
        return ResponseEntity.ok("회원정보 수정 성공");
    }

    @DeleteMapping
    public ResponseEntity<String> delete() {
        return ResponseEntity.ok("회원탈퇴 성공");
    }

}
