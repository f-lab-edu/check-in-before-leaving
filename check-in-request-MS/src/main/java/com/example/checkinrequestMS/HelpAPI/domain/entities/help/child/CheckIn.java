package com.example.checkinrequestMS.HelpAPI.domain.entities.help.child;

import com.example.checkinrequestMS.HelpAPI.domain.entities.help.Help;
import com.example.checkinrequestMS.HelpAPI.domain.entities.progress.Progress;
import com.example.checkinrequestMS.HelpAPI.web.form.help.checkIn.CheckInRegisterForm;
import com.example.checkinrequestMS.PlaceAPI.domain.Place;
import jakarta.persistence.Entity;
import lombok.*;
import lombok.experimental.SuperBuilder;


@Getter
@Entity
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CheckIn extends Help {

    public void setCheckInTitle(Place place){
        final String checkInCommonPhrase = " 체크인 요청";
        this.title = place.getPlaceName() + checkInCommonPhrase;
    }
    //public void setPlaceWithFullInfo(Place place){this.place = place;}
    public void setProgress(Progress progress){
        this.progress = progress;
    }

    public static CheckIn from(CheckInRegisterForm form){
        //Place place = Place.createEmptyPlaceWithOnlyId(form.getPlaceId());
        // fixme: 원래는 이렇게 불완전 객체로 가지고 왔었는데 CheckIn을 조회할 때 Place 정보도 같이 주면 좋을 것 같기도 합니다.
        //        CheckIn에서 Place 정보를 조회는 해도 변경할 경우는 없을것 같습니다.
        //        변경 트랜잭션은 아니어도 조회 트랜잭션은 같이 타면 좋을 것 같습니다.
        //        Q1. 이런 경우가 약한 참조가 더 어울리는 관계 일까요?
        //        Q2. 강한 참조라고 생각된다면 코드레벨에서 어떻게 Place엔티티를 가져올지 생각해 보았는데
        //            이 경우 from 메소드에 Place Repository를 추가 해도 좋을 까요?
        //            아니면 컨트롤러에 Place Repository를 놓고 Place 객체를 같이 받는 from 메서드를 하나 더 만드나요?

        return CheckIn.builder()
                .memberId(form.getMemberId())
                .start(form.getStart())
                .end(form.getStart().plusMinutes(form.getOption())) //30 or 60 min
                .placeId(form.getPlaceId())
                //.place(place)
                .reward(form.getReward())
                .build();
    }


}
