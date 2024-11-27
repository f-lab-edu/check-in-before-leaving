package com.example.checkinrequestMS.fixtures.HelpAPI.web.form.progress.business;


import com.example.checkinrequestMS.HelpAPI.web.controller.dto.request.progress.business.ProgressApproveRequest;

public class ProgressApproveRequestFixture extends ProgressApproveRequest {

    private ProgressApproveRequestFixture(Long helpId, Boolean isApproved) {
        super(helpId, isApproved);
    }

    public static ProgressApproveRequest create() {
        return ProgressApproveRequest.builder()
                .helpId(1L)
                .isApproved(true)
                .build();
    }
}
