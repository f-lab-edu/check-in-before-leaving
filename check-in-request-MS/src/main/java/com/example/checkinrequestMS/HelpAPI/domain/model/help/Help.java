package com.example.checkinrequestMS.HelpAPI.domain.model.help;

import com.example.checkinrequestMS.HelpAPI.domain.model.help.ProgressVO.*;
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
    protected String placeId;
    protected Long reward;
    protected T progress;

    protected Help(Long id, Long helpRegisterId, String title, LocalDateTime start, LocalDateTime end, String placeId, Long reward, T progress) {
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
                return Authenticated.of(progressEntity.getHelperId(), progressEntity.getPhotoPath());
            case COMPLETED:
                return Completed.of(progressEntity.getHelperId(), progressEntity.getPhotoPath());
        }
        throw new RuntimeException("illegal Progress type");
    }

    public void registerHelper(Long helperId) {
        this.progress = (T) Ongoing.from(helperId);
    }

    public void addPhoto(String photoPath) {
        Ongoing ongoing = (Ongoing) this.progress;
        this.progress = (T) Authenticated.of(ongoing.getHelperId(), photoPath);
    }

    public void approved() {
        Authenticated authenticated = (Authenticated) this.progress;
        this.progress = (T) Completed.of(authenticated.getHelperId(), authenticated.getPhotoPath());
    }


}
