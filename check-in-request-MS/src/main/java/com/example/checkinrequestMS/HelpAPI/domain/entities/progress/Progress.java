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
        Help help = Help.createEmptyHelpWithOnlyId(form.getHelpId());

        return Progress.builder()
                .helperId(form.getHelperId())
                .help(help)
                .build();
    }


}
