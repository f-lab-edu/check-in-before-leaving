package com.example.checkinrequestMS.HelpAPI.domain.entities.help;

import com.example.checkinrequestMS.HelpAPI.domain.entities.progress.Progress;
import com.example.checkinrequestMS.PlaceAPI.domain.Place;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
//fixme: Enum 같은걸로 타입만 나눌까 하다가 비즈니스 로직이 조금씩 다른 면이 있어서
//       상속 관계로 구성하였습니다. 데이터 정규화를 일단 지키기고 개발하기 위해 JOINED로 구성하였습니다.
//       다만 걸리는 점은 CheckIn과 LineUp은 비즈니스 로직은 다르지만
//       Help에서 추가되는 컬럼은 사실 없어서 DB의 테이블은 PK하나 뿐입니다.
//       이런 경우에도 상속으로 구성을 해도 좋을지 궁급합니다.
@SuperBuilder
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Help {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "help_id", nullable = false)
    protected Long id;
    //fixme: 모놀리식으로 구성할 경우 Member와 다대일 양방향 관계를 만들어야 할것 같은데
    //       MSA에서 다른 MS인 경우로 생각해서 이렇게 두었습니다.
    //       당연한 걸 질문하는 걸 수 있지만 gradle test fixture 같이 공유해서 쓰기도 하는지 궁금해서요.
    //       서로 다른 MS일 경우는 이렇게 식별자만 가져와서 서버간 통신으로 필요시 조회하는게 맞을까요?
    protected Long memberId;
    protected String title;
    protected LocalDateTime start;
    protected LocalDateTime end;
    //fixme: Place에서 Help를 찾는 경우가 적을 것 같아 일단 단방향으로 구성하였습니다.
    @ManyToOne
    @JoinColumn(name = "place_id")
    protected Place place;

    @OneToOne
    @JoinColumn(name = "progress_id")
    protected Progress progress;
    protected Long reward;



    //private String content;


    //체크인 요청, 줄서기 요청, 기타 요청.





}
