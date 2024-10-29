package com.example.checkinrequestMS.fixtures.HelpAPI.web.form.progress.register;

import com.example.checkinrequestMS.HelpAPI.web.dto.form.progress.write.ProgressRegisterRequest;

public final class ProgressRegisterRequestFixture extends ProgressRegisterRequest {

    private ProgressRegisterRequestFixture(Long helpId, Long helperId) {
        super(helpId, helperId);
    }

    public static ProgressRegisterRequest create() {
        return ProgressRegisterRequest.builder()
                .helpId(1L)
                .helperId(1L)
                .build();
    }
}
