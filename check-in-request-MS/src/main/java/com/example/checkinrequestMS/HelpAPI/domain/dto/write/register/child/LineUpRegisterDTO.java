package com.example.checkinrequestMS.HelpAPI.domain.dto.write.register.child;

import com.example.checkinrequestMS.HelpAPI.domain.dto.write.register.HelpRegisterDTO;
import com.example.checkinrequestMS.HelpAPI.web.dto.form.help.write.register.child.LineUpRegisterForm;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LineUpRegisterDTO extends HelpRegisterDTO {

    @Builder(access = AccessLevel.PROTECTED)
    protected LineUpRegisterDTO(Long helpRegisterId, Long placeId, java.time.LocalDateTime start, LocalDateTime end, Long reward) {
        super(helpRegisterId, placeId, start, end, reward);
    }

    public static LineUpRegisterDTO from(LineUpRegisterForm form){
        return LineUpRegisterDTO.builder()
                .helpRegisterId(form.getHelpRegisterId())
                .start(form.getStart())
                .end(form.getStart().plusHours(form.getOption())) // Hours
                .placeId(form.getPlaceId())
                .reward(form.getReward())
                .build();
    }

}
