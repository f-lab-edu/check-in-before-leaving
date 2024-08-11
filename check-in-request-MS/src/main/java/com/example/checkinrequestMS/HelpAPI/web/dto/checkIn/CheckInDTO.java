package com.example.checkinrequestMS.HelpAPI.web.dto.checkIn;


import com.example.checkinrequestMS.HelpAPI.domain.entities.help.child.CheckIn;
import com.example.checkinrequestMS.HelpAPI.web.dto.progress.ProgressDTO;
import com.example.checkinrequestMS.PlaceAPI.domain.Place;
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
    // fixme: 이런 식으로 DTO를 섞어서 쓰는 걸 처음 적용해 봐서 괜찮은지 잘 모르겠습니다.
    //        이렇게 사용하기도 하나요?
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
                .place(PlaceDTO.from(checkIn.getPlace()))
                .start(checkIn.getStart())
                .end(checkIn.getEnd())
                .progress(ProgressDTO.from(checkIn.getProgress()))
                .reward(checkIn.getReward())
                .build();

    }
}
