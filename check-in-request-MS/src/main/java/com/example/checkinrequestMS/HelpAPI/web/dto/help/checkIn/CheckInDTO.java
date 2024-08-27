package com.example.checkinrequestMS.HelpAPI.web.dto.help.checkIn;


import com.example.checkinrequestMS.HelpAPI.domain.entities.help.child.CheckIn;
import com.example.checkinrequestMS.HelpAPI.domain.entities.help.child.LineUp;
import com.example.checkinrequestMS.HelpAPI.web.dto.progress.ProgressDTO;
import com.example.checkinrequestMS.PlaceAPI.web.dto.PlaceDTO;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Builder(access = AccessLevel.PRIVATE)
@Getter
public class CheckInDTO {
    private Long id;
    private Long memberId;
    private String title;
    private PlaceDTO place;
    private LocalDateTime start;
    private LocalDateTime end;
    private ProgressDTO progress;
    private Long reward;

    public static CheckInDTO from(CheckIn checkIn) {

        return CheckInDTO.builder()
                .id(checkIn.getId())
                .memberId(checkIn.getMemberId())
                .title(checkIn.getTitle())
                //.place(PlaceDTO.from(checkIn.getPlace()))
                // fixme: 약한 관계인가 생각했는데, DTO로 정보를 보내주려면 Place를 가지고 있어야 할것 같기도 하고
                //        약한 관계인지 강한 관계인지 계속 고민이 되기는 합니다.
                //        일단 체크인 조회시 Place 정보도 같이 주고 싶긴 한데 어떤 방법이 좋을까요?
                //        그렇지만 아무래도 둘의 라이프 사이클은 다르기에 약한 관계가 맞는듯 하긴 합니다.
                //        그렇다면
                //        Q1. 이렇게 place 정보 제외하고 프론트에서 Place도 API를 통해 따로 조회하는게 맞을까요?
                //        Q2. 아니면 서비스에서 DTO를 만들어서 넘겨 주는게 좋을 까요?
                //            이 경우 DTO는 인터페이스의 책임인것 같아 서비스에서 제작하는게 맞을지 의문이 들기는 합니다.
                .start(checkIn.getStart())
                .end(checkIn.getEnd())
                .progress(ProgressDTO.from(checkIn.getProgress()))
                //fixme: Progress 엔티티에서 Help는 약한 관계인것 같은데 Help입장에서는 Progress를 강한 관계로 가질 수 있을 것 같은데
                //        이렇게 엔티티 마다 입장이 다를 수도 있을까요?
                .reward(checkIn.getReward())
                .build();

    }
}
