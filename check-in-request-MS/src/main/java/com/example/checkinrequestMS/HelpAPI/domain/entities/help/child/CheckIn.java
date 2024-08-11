package com.example.checkinrequestMS.HelpAPI.domain.entities.help.child;

import com.example.checkinrequestMS.HelpAPI.domain.entities.help.Help;
import com.example.checkinrequestMS.HelpAPI.domain.entities.progress.Progress;
import com.example.checkinrequestMS.HelpAPI.web.form.help.checkIn.CheckInRegisterForm;
import com.example.checkinrequestMS.PlaceAPI.domain.Place;
import jakarta.persistence.Entity;
import lombok.*;
import lombok.experimental.SuperBuilder;


@Getter
@Entity
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CheckIn extends Help {

    public void setCheckInTitle(Place place){
        final String checkInCommonPhrase = " 체크인 요청";
        this.title = place.getPlaceName() + checkInCommonPhrase;
    }
    public void setPlaceWithFullInfo(Place place){
        this.place = place;
    }
    public void setProgress(Progress progress){
        this.progress = progress;
    }

    public static CheckIn from(CheckInRegisterForm form){
        Place place = Place.createEmptyPlaceWithOnlyId(form.getPlaceId());

        return CheckIn.builder()
                .memberId(form.getMemberId())
                .start(form.getStart())
                .end(form.getStart().plusMinutes(form.getOption())) //30 or 60 min
                .place(place)
                .reward(form.getReward())
                .build();
    }


}
