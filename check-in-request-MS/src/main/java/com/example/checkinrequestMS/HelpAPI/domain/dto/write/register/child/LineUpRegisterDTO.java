package com.example.checkinrequestMS.HelpAPI.domain.dto.write.register.child;

import com.example.checkinrequestMS.HelpAPI.domain.dto.write.register.HelpRegisterDTO;
import com.example.checkinrequestMS.HelpAPI.web.controller.help.write.HelpWriteController;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LineUpRegisterDTO extends HelpRegisterDTO {

    @Builder(access = AccessLevel.PROTECTED)
    protected LineUpRegisterDTO(Long helpRegisterId, String placeId, java.time.LocalDateTime start, LocalDateTime end, Long reward) {
        super(helpRegisterId, placeId, start, end, reward);
    }

    public static LineUpRegisterDTO from(HelpWriteController.LineUpRegisterRequest request) {
        return LineUpRegisterDTO.builder()
                .helpRegisterId(request.getHelpRegisterId())
                .start(request.getStart())
                .end(request.getStart().plusHours(request.getOption())) // Hours
                .placeId(request.getPlaceId())
                .reward(request.getReward())
                .build();
    }

}
