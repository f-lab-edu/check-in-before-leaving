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
public class CheckInRegisterDTO extends HelpRegisterDTO {

    @Builder(access = AccessLevel.PROTECTED)
    protected CheckInRegisterDTO(Long helpRegisterId, String placeId, LocalDateTime start, LocalDateTime end, Long reward) {
        super(helpRegisterId, placeId, start, end, reward);
    }

    public static CheckInRegisterDTO from(HelpWriteController.CheckInRegisterRequest form) {
        return CheckInRegisterDTO.builder()
                .helpRegisterId(form.getHelpRegisterId())
                .start(form.getStart())
                .end(form.getStart().plusMinutes(form.getOption())) //30 or 60 min
                .placeId(form.getPlaceId())
                .reward(form.getReward())
                .build();
    }

}
