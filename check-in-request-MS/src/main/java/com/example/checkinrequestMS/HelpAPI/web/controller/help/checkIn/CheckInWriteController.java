package com.example.checkinrequestMS.HelpAPI.web.controller.help.checkIn;

import com.example.checkinrequestMS.HelpAPI.domain.entities.help.child.CheckIn;
import com.example.checkinrequestMS.HelpAPI.domain.service.checkIn.CheckInWriteService;
import com.example.checkinrequestMS.HelpAPI.web.form.help.checkIn.CheckInRegisterForm;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/help/checkIn")
@RequiredArgsConstructor
public class CheckInWriteController {

    private final CheckInWriteService checkInWriteService;

    private static final String CHECK_IN_SUCCESS = "체크인 요청 등록 성공";

    @PostMapping
    public ResponseEntity<String> registerCheckIn(@Validated @RequestBody CheckInRegisterForm form) {
        // fixme: 좀 길긴하지만 하나의 질문이라 문단들로 묶었습니다.

        // fixme 문단 1 - 데이터 모델링 관점:
        //       강한 엔티티와 약한 엔티티의 차이를 구분하는 기준으로 보면
        //       Place는 CheckIn과 독립적인 ID를 가지는게 맞고 라이프사이클도 달라서
        //       데이터 모델링 관점에서 서로 약한 엔티티 관계가 맞고 placeId만 CheckIn에 있는게 맞다는 생각입니다.

        //fixme 문단2 - JPA 관점:
        //      그런데 이렇게 되면 대부분의 JPA 관계에서 연관관계가 아닌 ID값만 쓰게 될것 같습니다.
        //      예를 들면 Author랑 Book이 있다고 하더라도 데이터 모델링 관점에서 접근하면
        //      서로 독립된 라이프 사이클을 가지는 약한 엔티티 관계로 서로를 모델링 하여야 하지만
        //      JPA는 객체 지향적 관점에서 접근하기에 단순히 ID값을 가지고 있는 것보다 객체와 연관관계를
        //      가지고 사용 방식이 더 효율적이고 자연스럽다는 생각이 듭니다.

        //fixme 문단3 - 코드에서 해결책 :
        //      그렇다면 어쩌면 제가 CheckIn을 from 이라는 정적 팩토리 메소드에서 null값 없이
        //      모든 필드를 채우려고 한게 문제가 아닐까 생각했습니다.
        //      JPA의 객체 지향적 관점에서 객체들을 사용하고 싶다면 ID값을 넘겨줘서
        //      서비스에서 다른 객체를 찾아서 채워주고 save 하는 방법도 괜찮지 않을까 생각했습니다.

        //fixme 문단 4 - 고민하게 되는 문제점:
        //      이 경우 코드레벨에서 JPA 엔티티들의 구조가 데이터 모델링 관점에의 엔티티와 불일치 한다고 생각합니다.
        //      하지만 데이터 모델링 관점에서는 정규화를 선호하지만 필요에 따라 역정규화를 해서 사용을 하듯이
        //      JPA를 사용하는 경우, 데이터 모델링과 정확히 일치하게 엔티티를 만들지 않고
        //      객체간의 연관관계로 엔티티를 엮어서 사용시 객체지향의 장점을 살려 객체간 관계를 이용하는 서비스 코드를 작성하기 편리한것 같습니다.
        //      Q. 그렇다면 JPA 사용 하는 이유는 코드레벨에서는 이런 객체 지향적 구조에서 얻는 혜택을 생각해
        //      데이터 모델링과 일치하지 않더라도 연관관계로 묶어서 편의상 강한 엔티티처럼 사용하는 걸까요?
        //      데이터 모델링 대로 약한 객체로 만들어서 사용한다면 꼭 JPA를 사용하지 않고
        //      POJO로 엔티티를 만들어서 필요시 Repository를 통해 값들을 가져와서 이용해도 괜찮다는 생각이 듭니다.

        // fixme 문단 5 - 추가 질문
        //      Q. 또한, JPA 방식으로 엔티티를 사용하고 싶다면 이 코드처럼 CheckIn에서 Place에 null값을 넣은후 ID값을 따로 넘겨 주어어서
        //         서비스에서 온전한 Place객체를 set 해주는게 일반적인 방법일까요?
        //         (어쩌면 이것도 null값 때문에 불완전 객체일까요?)
        //         약간 걱정되는 부분은 연관관계로 생기는 객체가 늘어나면 넘겨주는 파라미터도 길어지게 될 것같습니다.
        //         파라미터 보통 2,3개 정도만 최대로 넘겨줘 왔다보니 경험상 늘어나는게 괜찮을지 염려되기는 합니다.

        CheckIn checkIn = CheckIn.from(form);
        checkInWriteService.registerCheckIn(checkIn, form.getPlaceId());
        return ResponseEntity.ok(CHECK_IN_SUCCESS);
    }



}
