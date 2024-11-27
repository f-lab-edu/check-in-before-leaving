package com.example.checkinrequestMS.fixtures.HelpAPI.web.form.progress.register;

import com.example.checkinrequestMS.HelpAPI.web.controller.progress.wrtie.ProgressWriteController;

public final class ProgressRegisterRequestFixture extends ProgressWriteController.ProgressRegisterRequest {

    private ProgressRegisterRequestFixture(Long helpId, Long helperId) {
        super(helpId, helperId);
    }

    public static ProgressWriteController.ProgressRegisterRequest create() {
        return ProgressWriteController.ProgressRegisterRequest.builder()
                .helpId(1L)
                .helperId(1L)
                .build();
    }
}
