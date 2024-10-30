package com.example.checkinrequestMS.HelpAPI.domain.model.help;

import com.example.checkinrequestMS.HelpAPI.domain.model.help.ProgressVO.Authenticated;
import com.example.checkinrequestMS.HelpAPI.domain.model.help.ProgressVO.Created;
import com.example.checkinrequestMS.HelpAPI.domain.model.help.ProgressVO.Ongoing;
import com.example.checkinrequestMS.HelpAPI.domain.model.help.ProgressVO.Progress;
import com.example.checkinrequestMS.HelpAPI.infra.db.entity.ProgressValue;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

import static com.example.checkinrequestMS.HelpAPI.infra.db.entity.ProgressValue.*;

@Getter
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class Help<T extends Progress> {

    protected Long id;
    protected Long helpRegisterId;

    protected String title;
    protected LocalDateTime start;
    protected LocalDateTime end;
    protected Long placeId;
    protected Long reward;
    protected T progress;

    protected Help(Long id, Long helpRegisterId, String title, LocalDateTime start, LocalDateTime end, Long placeId, Long reward, T progress) {
        this.id = id;
        this.helpRegisterId = helpRegisterId;
        this.title = title;
        this.start = start;
        this.end = end;
        this.placeId = placeId;
        this.reward = reward;
        this.progress = progress;
    }

    protected static Progress getProgress(ProgressValue progressEntity) {
        switch (progressEntity.getStatus()) {
            case ONGOING:
                return Ongoing.from(progressEntity.getHelperId());
            case CREATED:
                return Created.create();
            case AUTHENTICATED:
                return Authenticated.of(progressEntity.getHelperId(), progressEntity.getPhotoPath(), progressEntity.isCompleted());
        }
        throw new RuntimeException("illegal Progress type");
    }
    public void registerHelper(Long helperId){
        this.progress = (T) Ongoing.from(helperId);
    }
    public void addPhoto(String photoPath){
        Ongoing ongoing = (Ongoing) this.progress;
        this.progress = (T) Authenticated.of(ongoing.getHelperId(), photoPath, ongoing.isCompleted());
    }
    public void approved() {
        Authenticated authenticated = (Authenticated) this.progress;
        authenticated.approved();
        this.progress = (T) authenticated;
    }

    //체크인 요청, 줄서기 요청, 기타 요청.





}
