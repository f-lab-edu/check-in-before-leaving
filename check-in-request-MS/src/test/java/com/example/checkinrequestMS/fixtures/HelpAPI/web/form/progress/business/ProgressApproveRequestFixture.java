package com.example.checkinrequestMS.fixtures.HelpAPI.web.form.progress.business;


import com.example.checkinrequestMS.HelpAPI.web.controller.progress.business.ProgressBusinessWriteController;

public class ProgressApproveRequestFixture extends ProgressBusinessWriteController.ProgressApproveRequest {

    private ProgressApproveRequestFixture(Long helpId, Boolean isApproved) {
        super(helpId, isApproved);
    }

    public static ProgressBusinessWriteController.ProgressApproveRequest create() {
        return ProgressBusinessWriteController.ProgressApproveRequest.builder()
                .helpId(1L)
                .isApproved(true)
                .build();
    }
}
