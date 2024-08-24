package com.example.checkinrequestMS.HelpAPI.domain.entities.progress;

import com.example.checkinrequestMS.HelpAPI.domain.entities.help.Help;
import com.example.checkinrequestMS.HelpAPI.web.form.progress.crud.ProgressRegisterForm;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.boot.context.properties.bind.DefaultValue;

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

    private String photoPath;

    private boolean completed;

    public void setHelp(Help help) {
        this.help = help;
    }

    public static Progress from(ProgressRegisterForm form) {

        return Progress.builder()
                .help(null)
                .helperId(form.getHelperId())
                .photoPath(null)
                .completed(false)
                .build();
    }

    public void approve() {
        this.completed = true;
    }
    public void setPhotoPath(String photoPath){
        this.photoPath = photoPath;
    }
}
