package com.membercontext.memberAPI.application.service.SignUpSerivces.deco.config;

import com.membercontext.memberAPI.application.repository.MemberRepository;
import com.membercontext.memberAPI.application.service.SignUpSerivces.deco.SignUpEmailServiceImpl;
import com.membercontext.memberAPI.application.service.SignUpSerivces.deco.SignUpPasswordEncryptionServiceImpl_JavaCrypto;
import com.membercontext.memberAPI.application.service.SignUpSerivces.SignUpService;
import com.membercontext.memberAPI.application.service.SignUpSerivces.SignUpServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//@Configuration
public class SignUpConfig {

    //@Autowired
    //private JavaCryptoAdapter javaCryptoAdapter;

    //fixme (데코레이션 패턴): 기본 signUpService에 하나만 추가 할때는 @Primary를 쓰고
    //                     그것보다 많아 질때는 configuration을 통해 정의해주려 합니다.
    //                     현재는 예시를 위해 중간에 미구현된 signUpEmailService를 넣어 놓았습니다.
    //                     이렇게 구현하는게 오버 엔지니어링일지 궁금합니다.
    @Bean
    public SignUpService signUpService(MemberRepository memberRepository) {
        return new SignUpPasswordEncryptionServiceImpl_JavaCrypto(
                new SignUpEmailServiceImpl(
                        new SignUpServiceImpl(memberRepository)));
    }



}
