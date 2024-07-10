package com.membercontext.memberAPI.web.controller;

import com.membercontext.memberAPI.application.service.SignUpService;
import com.membercontext.memberAPI.domain.entity.Member;
import com.membercontext.memberAPI.web.controller.form.SignUpForm;
import com.membercontext.memberAPI.web.controller.form.UpdateForm;
import com.membercontext.memberAPI.web.dto.MemberDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/sign-in")
public class SignUpController {

    private final SignUpService signUpService;

    @PostMapping
    public ResponseEntity<String> signIn(@Validated @RequestBody SignUpForm signUpForm) {
        return ResponseEntity.ok(signUpService.signUp(Member.from(signUpForm)));
    }

    @PutMapping
    public ResponseEntity<MemberDto> update(@Validated @RequestBody UpdateForm updateForm) {
        return ResponseEntity.ok(MemberDto.from(signUpService.update(Member.from(updateForm))));
    }

    @DeleteMapping
    public ResponseEntity<String> delete(@RequestParam Long id) {
        return ResponseEntity.ok(signUpService.delete(id));
    }
    //TODO: checking commit
}
