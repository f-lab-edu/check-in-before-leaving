package com.example.checkinrequestMS.HelpAPI.domain.entities.help.child;

import com.example.checkinrequestMS.HelpAPI.domain.entities.help.Help;
import com.example.checkinrequestMS.HelpAPI.domain.entities.progress.Progress;

import com.example.checkinrequestMS.HelpAPI.web.form.help.lineUp.LineUpRegisterForm;
import com.example.checkinrequestMS.PlaceAPI.domain.Place;
import jakarta.persistence.Entity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Getter
@Entity
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LineUp extends Help {
    //fixme: Common Phrase랑 분단위가 아니라 분단위가 아닌 시간단위로 끝나는 시간을 설정하는것만 체크인과 다릅니다.
    public void setLineUpTitle(Place place){
        final String lineUpCommonPhrase = " 줄서기 요청";
        this.title = place.getPlaceName() + lineUpCommonPhrase;
    }
    public void setPlaceWithFullInfo(Place place){
        this.place = place;
    }
    public void setProgress(Progress progress){
        this.progress = progress;
    }

    public static LineUp from(LineUpRegisterForm form){
        Place place = Place.createEmptyPlaceWithOnlyId(form.getPlaceId());

        return LineUp.builder()
                .memberId(form.getMemberId())
                .start(form.getStart())
                .end(form.getStart().plusHours(form.getOption())) // 체크인은 30분 단위, 줄서기는 시간 단위.
                .place(place)
                .reward(form.getReward())
                .build();
    }
}
