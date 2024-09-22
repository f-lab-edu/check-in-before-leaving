package com.example.checkinrequestMS.HelpAPI.web.dto.response.help.select.child;


import com.example.checkinrequestMS.HelpAPI.domain.model.help.ProgressVO.Progress;
import com.example.checkinrequestMS.HelpAPI.domain.model.help.child.CheckIn;
import com.example.checkinrequestMS.HelpAPI.web.dto.response.help.select.HelpSelectResponse;
import com.example.checkinrequestMS.HelpAPI.web.dto.response.progress.read.ProgressSelectResponse;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CheckInSelectResponse extends HelpSelectResponse {

    @Builder(access = AccessLevel.PROTECTED)
    private CheckInSelectResponse(Long id, Long helpRegisterId, String title, String placeId, LocalDateTime start, LocalDateTime end, ProgressSelectResponse progressSelectResponse, Long reward) {
        super(id, helpRegisterId, title, placeId, start, end, progressSelectResponse, reward);
    }

    public static CheckInSelectResponse from(CheckIn checkIn) {
        return CheckInSelectResponse.builder()
                .id(checkIn.getId())
                .helpRegisterId(checkIn.getHelpRegisterId())
                .title(checkIn.getTitle())
                .placeId(checkIn.getPlaceId())
                .start(checkIn.getStart())
                .end(checkIn.getEnd())
                .reward(checkIn.getReward())
                .progressSelectResponse(ProgressSelectResponse.getProgressSelectResponse(checkIn.getProgress()))
                .build();
    }

}
