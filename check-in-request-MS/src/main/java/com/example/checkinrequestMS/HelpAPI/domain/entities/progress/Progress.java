package com.example.checkinrequestMS.HelpAPI.domain.entities.progress;

import com.example.checkinrequestMS.HelpAPI.domain.entities.help.Help;
import com.example.checkinrequestMS.HelpAPI.web.form.progress.ProgressRegisterForm;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Progress {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "progress_id", nullable = false)
    private Long id;

    @OneToOne
    @JoinColumn(name = "help_id")
    private Help help;

    private Long helperId;

    private String picturePath;

    private boolean isCompleted;

    public void setHelp(Help help) {
        this.help = help;
    }

    public static Progress from(ProgressRegisterForm form) {
        //fixme: 이렇게 ID만 가지고 생성해도 괜찮은 걸까요?
        //       이후에 서비스에서 조회해서 바꿔치기 하려고 합니다.
        Help help = Help.createEmptyHelpWithOnlyId(form.getHelpId());

        return Progress.builder()
                .helperId(form.getHelperId())
                .help(help)
                .build();
    }


}
