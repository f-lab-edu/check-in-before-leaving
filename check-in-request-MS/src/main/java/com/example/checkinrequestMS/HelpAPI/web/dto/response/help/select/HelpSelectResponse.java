package com.example.checkinrequestMS.HelpAPI.web.dto.response.help.select;

import com.example.checkinrequestMS.HelpAPI.domain.model.help.Help;
import com.example.checkinrequestMS.HelpAPI.domain.model.help.child.CheckIn;
import com.example.checkinrequestMS.HelpAPI.domain.model.help.child.Etc;
import com.example.checkinrequestMS.HelpAPI.domain.model.help.child.LineUp;
import com.example.checkinrequestMS.HelpAPI.web.dto.response.help.select.child.CheckInSelectResponse;
import com.example.checkinrequestMS.HelpAPI.web.dto.response.help.select.child.EtcSelectResponse;
import com.example.checkinrequestMS.HelpAPI.web.dto.response.help.select.child.LineUpSelectResponse;
import com.example.checkinrequestMS.HelpAPI.web.dto.response.progress.read.ProgressSelectResponse;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class HelpSelectResponse {

    protected Long id;
    protected Long helpRegisterId;
    protected String title;
    protected String placeId;
    protected LocalDateTime start;
    protected LocalDateTime end;
    protected ProgressSelectResponse progressSelectResponse;
    protected Long reward;

    protected HelpSelectResponse(Long id, Long helpRegisterId, String title, String placeId, LocalDateTime start, LocalDateTime end, ProgressSelectResponse progressSelectResponse, Long reward) {
        this.id = id;
        this.helpRegisterId = helpRegisterId;
        this.title = title;
        this.placeId = placeId;
        this.start = start;
        this.end = end;
        this.progressSelectResponse = progressSelectResponse;
        this.reward = reward;
    }

    public static HelpSelectResponse from(Help help) {
        if (help instanceof CheckIn) {
            return CheckInSelectResponse.from((CheckIn) help);
        } else if (help instanceof LineUp) {
            return LineUpSelectResponse.from((LineUp) help);
        } else if (help instanceof Etc) {
            return EtcSelectResponse.from((Etc) help);
        }
        throw new IllegalArgumentException("Unknown help type");
    }
}
