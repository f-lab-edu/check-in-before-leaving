package com.example.checkinrequestMS.HelpAPI.domain.model.help;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;


@Getter
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class Help {

    protected Long id;
    @NotNull
    protected Long helpRegisterId;
    @NotBlank
    protected String title;
    @NotNull
    protected LocalDateTime start;
    @NotNull
    protected LocalDateTime end;
    @NotNull
    protected String placeId;
    @NotNull
    protected Long reward;
    @NotNull
    protected Progress progress;

    protected Help(Long id, Long helpRegisterId, String title, LocalDateTime start, LocalDateTime end, String placeId, Long reward, Progress progress) {
        this.id = id;
        this.helpRegisterId = helpRegisterId;
        this.title = title;
        this.start = start;
        this.end = end;
        this.placeId = placeId;
        this.reward = reward;
        this.progress = progress;
    }

    public void start(Long helperId) {
        this.progress = this.progress.registerHelper(helperId);
    }

    public void authenticate(String photoPath) {
        this.progress = this.progress.addPhoto(photoPath);
    }

    public void approve() {
        this.progress = this.progress.approve();
    }


}
