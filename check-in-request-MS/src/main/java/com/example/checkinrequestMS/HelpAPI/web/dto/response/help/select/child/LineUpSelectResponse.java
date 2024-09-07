package com.example.checkinrequestMS.HelpAPI.web.dto.response.help.select.child;


import com.example.checkinrequestMS.HelpAPI.domain.model.help.child.LineUp;
import com.example.checkinrequestMS.HelpAPI.web.dto.response.help.select.HelpSelectResponse;
import com.example.checkinrequestMS.HelpAPI.web.dto.response.progress.read.ProgressSelectResponse;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LineUpSelectResponse extends HelpSelectResponse {

    @Builder(access = AccessLevel.PROTECTED)
    private LineUpSelectResponse(Long id, Long helpRegisterId, String title, Long placeId, LocalDateTime start, LocalDateTime end, ProgressSelectResponse progressDTO, Long reward) {
        super(id, helpRegisterId, title, placeId, start, end, progressDTO, reward);
    }

    public static LineUpSelectResponse from(LineUp lineUp) {
        return LineUpSelectResponse.builder()
                .id(lineUp.getId())
                .helpRegisterId(lineUp.getHelpRegisterId())
                .title(lineUp.getTitle())
                .placeId(lineUp.getPlaceId())
                .start(lineUp.getStart())
                .end(lineUp.getEnd())
                .reward(lineUp.getReward())
                .progressDTO(ProgressSelectResponse.getProgressDTO(lineUp.getProgress()))
                .build();
    }
}
