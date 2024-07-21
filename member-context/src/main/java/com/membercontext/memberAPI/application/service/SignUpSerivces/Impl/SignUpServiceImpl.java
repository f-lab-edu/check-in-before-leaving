package com.membercontext.memberAPI.application.service.SignUpSerivces.Impl;

import com.membercontext.memberAPI.application.exception.member.MemberException;
import com.membercontext.memberAPI.application.repository.MemberRepository;
import com.membercontext.memberAPI.application.service.SignUpSerivces.SignUpService;
import com.membercontext.memberAPI.domain.entity.member.Member;
import com.membercontext.memberAPI.infrastructure.encryption.JavaCryptoUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.membercontext.memberAPI.application.exception.member.MemberErrorCode.ALREADY_REGISTERED_USER;
import static com.membercontext.memberAPI.application.exception.member.MemberErrorCode.SIGNUP_FAILED;

@Service
@RequiredArgsConstructor
public class SignUpServiceImpl implements SignUpService {

    private final MemberRepository memberRepository;
    private final JavaCryptoUtil encryption;

    @Override
    @Transactional
    public String signUp(Member member) {
        //fixme: 지역변수가 좋을까요, 인스턴스 변수가 좋을까요?
        //       제 생각으로는 지역변수를 쓰면 메모리 낭비가 덜하지만 자주 호출 된다면
        //       GC전에 재사용 가능한 인스턴스 변수도 괜찮을것 같습니다.
        final String signUpSuccessMessage = "회원가입 성공";

        String encryptedPassword = encryption.encrypt(member.getPassword()); // 초기화 백터(IV) 미적용
        //fixme 취약점: iv를 사용하려면 password 저장 후 id를 받아오던가 email로 저장을 해야 합니다.
        //            문제 1. email로 저장하도록 바꾸려고 했지만 현재 이메일은 변경할 수 있도록 하려고 생각했는데
        //            암호화기능에서 기술 선택 실수 때문에 비즈니스로직을 변경하는 건 좋지 않다고 생각했습니다.
        //            문제 2. 그대로 id를 사용한다면 저장 후에 id값을 받아서 다시 저장 할 수 있지만 비밀번호가
        //            암호화 되지 않고 일단 db에 저장 되게 됩니다.
        //            문제 3. 그래서 IV를 쓰지 않고 암호화를 하게 되면 동일한 암호에 대해 동일한 암호화 값이 나오게 됩니다.
        //fixme 질문:  현재 상황은 일단 3번을 차선책으로 선택 하고 시간이 남을 때 해싱으로 변경하려고 합니다.
        //            (현업이라면 해싱으로 바로 바꿔야 겠지만 중요한 비즈니스 로직이 아니라 프로젝트 진행을 하고 나중에 수정을 하려고 합니다.)
        //            저의 불찰이지만 암호화에 대해 잘 몰라서 일어난 일인것 같습니다.
        //            그냥 넘어갈뻔 했는데 테스트를 하면서 취약점에 대해서 알게 되었습니다.
        //            보통 이런 경우는 테크리더 분이나 팀장님이 어떤 기술을 적용할지 알려 주시는 건가요?
        //            아직 많을 걸 모르는 상황이라 현업에서 이런 경우는 어떻게 해야 하는지 궁금합니다.

        member.encryptPassword(encryptedPassword);
        memberRepository.save(member);
        return signUpSuccessMessage;
    }

    @Override
    @Transactional
    public Member update(Member updatingMember) {
        return memberRepository.update(updatingMember);
    }

    @Override
    @Transactional
    public String delete(Long id) {
        final String deleteSuccessMessage = "회원 삭제 성공";

        memberRepository.delete(id);
        return deleteSuccessMessage;
    }

}


