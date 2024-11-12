package com.example.checkinrequestMS.HelpAPI.web.controller.dto.request.help.write.register.child;

import com.example.checkinrequestMS.HelpAPI.web.controller.dto.request.help.write.register.HelpRegisterRequest;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CheckInRegisterRequest extends HelpRegisterRequest {

    @Builder(access = AccessLevel.PROTECTED)
    private CheckInRegisterRequest(Long helpRegisterId, String placeId, LocalDateTime start, int option, Long reward) {
        super(helpRegisterId, placeId, start, option, reward);
    }
}