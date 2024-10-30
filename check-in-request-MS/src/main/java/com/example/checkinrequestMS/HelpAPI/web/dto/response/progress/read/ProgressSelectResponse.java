package com.example.checkinrequestMS.HelpAPI.web.dto.response.progress.read;

import com.example.checkinrequestMS.HelpAPI.domain.model.help.ProgressVO.Authenticated;
import com.example.checkinrequestMS.HelpAPI.domain.model.help.ProgressVO.Ongoing;
import com.example.checkinrequestMS.HelpAPI.domain.model.help.ProgressVO.Progress;
import com.example.checkinrequestMS.HelpAPI.web.dto.response.progress.read.child.AuthenticatedSelectResponse;
import com.example.checkinrequestMS.HelpAPI.web.dto.response.progress.read.child.CreatedSelectResponse;
import com.example.checkinrequestMS.HelpAPI.web.dto.response.progress.read.child.OnGoingSelectResponse;

import static com.example.checkinrequestMS.HelpAPI.infra.db.entity.ProgressValue.*;

public interface ProgressSelectResponse {

    public static ProgressSelectResponse getProgressDTO(Progress progress) {
        switch (progress.getClass().getSimpleName()) {
            case CREATED:
                return CreatedSelectResponse.basic();
            case ONGOING:
                return OnGoingSelectResponse.from((Ongoing) progress);
            case AUTHENTICATED:
                return AuthenticatedSelectResponse.from((Authenticated) progress);
        }
        throw new IllegalArgumentException("Invalid Progress");
    }



}
