package com.membercontext.memberAPI.application.service;

import com.membercontext.memberAPI.application.exception.member.MemberException;
import com.membercontext.memberAPI.application.repository.MemberRepository;
import com.membercontext.memberAPI.domain.entity.Member;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.membercontext.memberAPI.application.exception.member.MemberErrorCode.SIGNUP_FAILED;

@Service
@RequiredArgsConstructor
public class SignUpServiceImpl implements SignUpService{

    private final MemberRepository memberRepository;

    @Override
    @Transactional
    public String signUp(Member member) {
        try {
            memberRepository.save(member);
            return "회원가입 성공";
        }catch(Exception e){
            throw new MemberException(SIGNUP_FAILED);
        }


    //Fixme 범용성:
    //      그리고 언제나 기대대로 작동할지에 대한 부분은
    //      메서드 일부를 리포지토리로 옮겨서 나름대로 해결해 보았는데
    //      질문하신 의도에 맞는 적용인지도 궁금합니다.

    //Fixme 범용성:
    //      update랑 delete는 findById 해야되는 부분이 명확히 JPA에 의존 한다고 생각했는데
    //      findByEmail은 JDBC tempalte 같은 다른 라이브러리라도
    //      인터페이스에서 Optional로 리턴을 강제하면 되지 않을까 했는데
    //      조금 더 General한 적용을 하려면 리포지토리로 빼는게 나을것 같다고 생각했는데 괜찮은지도 궁금합니다.
    }

//    @Override
//    @Transactional
//    public Member update(Member updatingMember) {
//        Member member = memberRepository.findById(updatingMember.getId())
//                .orElseThrow(() -> new MemberException(NOT_EXITING_USER));
//        member.update(updatingMember);
//        memberRepository.save(member);
//        return member;
//    }
    @Override
    @Transactional
    public Member update(Member updatingMember) {
        return memberRepository.update(updatingMember);
    }

    @Override
    @Transactional
    public String delete(Long id) {
        memberRepository.delete(id);
        return "회원 삭제 성공";
    }

}


