package com.example.checkinrequestMS.HelpAPI.domain.entities.progress;

import com.example.checkinrequestMS.HelpAPI.domain.entities.help.Help;
import jakarta.persistence.*;

@Entity
public class Progress {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "progress_id", nullable = false)
    private Long id;

    @OneToOne(mappedBy = "progress")
    private Help help;
    //todo: 이 엔티티는 다음 커밋때 완성 하려고 합니다.



}
