package com.membercontext.memberAPI.domain.service;

import com.membercontext.memberAPI.domain.entity.Member;
import com.membercontext.memberAPI.repository.SignUpJPARepository;
import com.membercontext.memberAPI.web.exception.member.MemberException;
import com.membercontext.memberAPI.web.form.SignUpForm;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

import static com.membercontext.memberAPI.web.exception.member.MemberErrorCode.ALREADY_REGISTERED_USER;

@Service
@RequiredArgsConstructor
public class SignUpService {

    private SignUpJPARepository signUpJPARepository;

    public String signUp(SignUpForm signUpForm) {
        signUpJPARepository.findByEmail(signUpForm.getEmail()).ifPresent(member -> {
            throw new MemberException(ALREADY_REGISTERED_USER);
        });
        signUpJPARepository.save(Member.from(signUpForm));
        return "회원가입 성공";

        }
        //TODO: SignUpForm 서비스까지 데리고 와도 괜찮은지?
        //TODO: SignUpFormMock MockBuilder 괜찮은지?

}


