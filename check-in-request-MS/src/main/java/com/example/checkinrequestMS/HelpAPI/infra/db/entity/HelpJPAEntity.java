package com.example.checkinrequestMS.HelpAPI.infra.db.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "dtype")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "help")
public abstract class HelpJPAEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "help_id", nullable = false)
    protected Long id;
    protected Long helpRegisterId;
    protected String title;
    protected LocalDateTime start;
    protected LocalDateTime end;
    @Column(name = "place_id")
    protected String placeId;
    @Embedded
    protected ProgressValue progressValue;
    protected Long reward;

    protected HelpJPAEntity(Long id, Long helpRegisterId, String title, LocalDateTime start, LocalDateTime end, String placeId, ProgressValue progressValue, Long reward) {
        this.id = id;
        this.helpRegisterId = helpRegisterId;
        this.title = title;
        this.start = start;
        this.end = end;
        this.placeId = placeId;
        this.progressValue = progressValue;
        this.reward = reward;
    }
    //체크인 요청, 줄서기 요청, 기타 요청.

}
