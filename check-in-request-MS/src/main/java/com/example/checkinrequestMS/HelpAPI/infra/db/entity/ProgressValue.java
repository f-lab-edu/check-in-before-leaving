package com.example.checkinrequestMS.HelpAPI.infra.db.entity;

import com.example.checkinrequestMS.HelpAPI.domain.model.help.ProgressVO.Authenticated;
import com.example.checkinrequestMS.HelpAPI.domain.model.help.ProgressVO.Created;
import com.example.checkinrequestMS.HelpAPI.domain.model.help.ProgressVO.Ongoing;
import com.example.checkinrequestMS.HelpAPI.domain.model.help.ProgressVO.Progress;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ProgressValue {
    private boolean completed;
    private String photoPath;
    private Long helperId;
    private String status;

    public static final String CREATED = "Created";
    public static final String AUTHENTICATED = "Authenticated";
    public static final String ONGOING = "Ongoing";

    public static ProgressValue from(Progress progress) {


        switch (progress.getClass().getSimpleName()) {
            case CREATED:
                Created created = (Created) progress;
                return ProgressValue.builder()
                        .completed(created.isCompleted())
                        .status(CREATED)
                        .build();
            case AUTHENTICATED:
                Authenticated authenticated = (Authenticated) progress;
                return ProgressValue.builder()
                        .completed(authenticated.isCompleted())
                        .photoPath(authenticated.getPhotoPath())
                        .helperId(authenticated.getHelperId())
                        .status(AUTHENTICATED)
                        .build();
            case ONGOING:
                Ongoing ongoing = (Ongoing) progress;
                return ProgressValue.builder()
                        .completed(ongoing.isCompleted())
                        .helperId(ongoing.getHelperId())
                        .status(ONGOING)
                        .build();
        }
        throw new IllegalArgumentException("Invalid Progress");
    }
}
