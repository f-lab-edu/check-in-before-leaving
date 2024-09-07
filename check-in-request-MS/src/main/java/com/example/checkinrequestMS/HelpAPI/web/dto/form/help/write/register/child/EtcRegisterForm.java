package com.example.checkinrequestMS.HelpAPI.web.dto.form.help.write.register.child;

import com.example.checkinrequestMS.HelpAPI.web.dto.form.help.write.register.HelpRegisterForm;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class EtcRegisterForm extends HelpRegisterForm {

    public static final String NO_CONTENTS = "내용은 필수입니다.";
    public static final String NO_TITLE = "제목은 필수입니다.";

    @NotNull(message = NO_CONTENTS)
    private String contents; //todo: Longtext(?)

    @NotNull(message = NO_TITLE)
    private String title;

    @Builder(access = AccessLevel.PROTECTED)
    public EtcRegisterForm(Long helpRegisterId, Long placeId, LocalDateTime start, int option, Long reward, String contents, String title) {
        super(helpRegisterId, placeId, start, option, reward);
        this.contents = contents;
        this.title = title;
    }


}
