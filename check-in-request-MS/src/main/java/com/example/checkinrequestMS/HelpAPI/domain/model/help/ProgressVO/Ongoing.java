package com.example.checkinrequestMS.HelpAPI.domain.model.help.ProgressVO;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Ongoing implements Progress{

    private Long helperId;
    private boolean completed;

    private Ongoing(Long helperId, boolean completed) {
        this.helperId = helperId;
        this.completed = completed;
    }

    public static Ongoing from(Long helperId) {
        return new Ongoing(helperId, false);
    }

}
