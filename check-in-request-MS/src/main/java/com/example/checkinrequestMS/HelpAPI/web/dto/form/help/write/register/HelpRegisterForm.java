package com.example.checkinrequestMS.HelpAPI.web.dto.form.help.write.register;

import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class HelpRegisterForm {

    public static final String NO_HELP_REGISTER_ID = "요청 등록자는 필수입니다.";
    public static final String NO_PLACE_ID = "가게 정보는 필수입니다.";
    public static final String NO_START = "시작 시간은 필수입니다.";
    public static final String NO_TIME_OPTION = "수행 시간 옵션은 필수입니다.";
    public static final String NO_REWARD = "보상은 필수입니다.";
    // ETC 관련 상수는 ETCRegisterForm에 추가.

    @NotNull(message = NO_HELP_REGISTER_ID)
    private Long helpRegisterId;

    @NotNull(message = NO_PLACE_ID)
    private String placeId;

    @NotNull(message = NO_START)
    private LocalDateTime start;

    @NotNull(message = NO_TIME_OPTION)
    private Integer option;

    @NotNull(message = NO_REWARD)
    private Long reward;

    protected HelpRegisterForm(Long helpRegisterId, String placeId, LocalDateTime start, int option, Long reward) {
        this.helpRegisterId = helpRegisterId;
        this.placeId = placeId;
        this.start = start;
        this.option = option;
        this.reward = reward;
    }
}
