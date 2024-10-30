package com.example.checkinrequestMS.HelpAPI.web.dto.response.help.select.child;


import com.example.checkinrequestMS.HelpAPI.domain.model.help.child.Etc;
import com.example.checkinrequestMS.HelpAPI.web.dto.response.help.select.HelpSelectResponse;
import com.example.checkinrequestMS.HelpAPI.web.dto.response.progress.read.ProgressSelectResponse;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class EtcSelectResponse extends HelpSelectResponse {

    private String contents;


    @Builder(access = AccessLevel.PROTECTED)
    private EtcSelectResponse(Long id, Long helpRegisterId, String title, String placeId, LocalDateTime start, LocalDateTime end, ProgressSelectResponse progressSelectResponse, Long reward, String contents) {
        super(id, helpRegisterId, title, placeId, start, end, progressSelectResponse, reward);
        this.contents = contents;
    }

    public static EtcSelectResponse from(Etc etc) {
        return EtcSelectResponse.builder()
                .id(etc.getId())
                .title(etc.getTitle())
                .helpRegisterId(etc.getHelpRegisterId())
                .placeId(etc.getPlaceId())
                .progressSelectResponse(ProgressSelectResponse.getProgressSelectResponse(etc.getProgress()))
                .start(etc.getStart())
                .end(etc.getEnd())
                .reward(etc.getReward())
                .contents(etc.getContents())
                .build();
    }
}
