package com.example.checkinrequestMS.HelpAPI.domain.entities.help.child;

import com.example.checkinrequestMS.HelpAPI.domain.entities.help.Help;
import com.example.checkinrequestMS.HelpAPI.domain.entities.progress.Progress;
import com.example.checkinrequestMS.HelpAPI.web.form.help.etc.EtcRegisterForm;
import com.example.checkinrequestMS.HelpAPI.web.form.help.lineUp.LineUpRegisterForm;
import com.example.checkinrequestMS.PlaceAPI.domain.Place;
import jakarta.persistence.Entity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@SuperBuilder
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Etc extends Help {

    private String contents;


    public static Etc from(EtcRegisterForm form){
        return Etc.builder()
                .title(form.getTitle())
                .memberId(form.getMemberId())
                .start(form.getStart())
                .end(form.getStart().plusHours(form.getOption())) // 체크인은 30분 단위, 나머지는 시간 단위.
                .place(null)
                .progress(null)
                .reward(form.getReward())
                .contents(form.getContents())
                .build();
    }

}
