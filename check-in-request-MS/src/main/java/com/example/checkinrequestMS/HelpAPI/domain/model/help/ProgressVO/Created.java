package com.example.checkinrequestMS.HelpAPI.domain.model.help.ProgressVO;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Created implements Progress{

    private boolean completed = false;

    public static Created create(){
        return new Created();
    }

}
