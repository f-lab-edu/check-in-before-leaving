package com.example.checkinrequestMS.HelpAPI.domain.entities.help;

import com.example.checkinrequestMS.HelpAPI.domain.entities.progress.Progress;
import com.example.checkinrequestMS.PlaceAPI.domain.Place;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@SuperBuilder
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Help {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "help_id", nullable = false)
    protected Long id;
    protected Long memberId;
    protected String title;
    protected LocalDateTime start;
    protected LocalDateTime end;

    @ManyToOne
    @JoinColumn(name = "place_id")
    protected Place place;

    @OneToOne(mappedBy = "help", cascade = CascadeType.ALL)
    protected Progress progress;

    protected Long reward;

    public void setProgress(Progress progress) {
        this.progress = progress;
    }
    public void setPlace(Place place) {
        this.place = place;
    }

    //체크인 요청, 줄서기 요청, 기타 요청.





}
