//package com.membercontext.memberAPI.application.service.SignUpSerivces.Impl.decoration_deprecated.config;
//
//import com.membercontext.memberAPI.application.repository.MemberRepository;
//import com.membercontext.memberAPI.application.service.SignUpSerivces.Impl.decoration_deprecated.SignUpEmailServiceImpl;
//import com.membercontext.memberAPI.application.service.SignUpSerivces.Impl.decoration_deprecated.SignUpPasswordEncryptionServiceImpl_JavaCrypto;
//import com.membercontext.memberAPI.application.service.SignUpSerivces.SignUpService;
//import com.membercontext.memberAPI.application.service.SignUpSerivces.Impl.SignUpServiceImpl;
//import org.springframework.context.annotation.Bean;
//
////@Configuration
//public class SignUpConfig {
//
//    //@Autowired
//    //private JavaCryptoAdapter javaCryptoAdapter;
//
//    @Bean
//    public SignUpService signUpService(MemberRepository memberRepository) {
//        return new SignUpPasswordEncryptionServiceImpl_JavaCrypto(
//                new SignUpEmailServiceImpl(
//                        new SignUpServiceImpl(memberRepository)));
//    }
//
//
//
//}
