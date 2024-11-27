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
public class EtcRegisterDTO extends HelpRegisterDTO {

    private String title;
    private String contents;

    @Builder(access = AccessLevel.PROTECTED)
    protected EtcRegisterDTO(Long helpRegisterId, String placeId, java.time.LocalDateTime start, LocalDateTime end, Long reward, String title, String contents) {
        super(helpRegisterId, placeId, start, end, reward);
        this.title = title;
        this.contents = contents;
    }

    public static EtcRegisterDTO from(HelpWriteController.EtcRegisterRequest form) {
        return EtcRegisterDTO.builder()
                .helpRegisterId(form.getHelpRegisterId())
                .start(form.getStart())
                .end(form.getStart().plusHours(form.getOption())) // Hours
                .placeId(form.getPlaceId())
                .reward(form.getReward())
                .title(form.getTitle())
                .contents(form.getContents())
                .build();
    }

}
