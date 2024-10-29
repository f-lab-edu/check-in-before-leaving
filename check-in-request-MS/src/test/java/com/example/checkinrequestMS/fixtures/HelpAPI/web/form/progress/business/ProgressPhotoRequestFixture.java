package com.example.checkinrequestMS.fixtures.HelpAPI.web.form.progress.business;

import com.example.checkinrequestMS.HelpAPI.web.dto.form.progress.business.ProgressPhotoRequest;

public class ProgressPhotoRequestFixture extends ProgressPhotoRequest {

    private ProgressPhotoRequestFixture(Long helpId) {
        super(helpId);
    }

    public static ProgressPhotoRequest create() {
        return ProgressPhotoRequest.builder()
                .helpId(1L)
                .build();
    }
}
