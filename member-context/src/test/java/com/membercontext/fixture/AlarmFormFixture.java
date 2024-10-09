package com.membercontext.fixture;

import com.membercontext.memberAPI.web.controller.AlarmController;
import lombok.Builder;

public class AlarmFormFixture extends AlarmController.AlarmForm {

    protected AlarmFormFixture(double x, double y, String title, String message) {
        super(x, y, title, message);
    }

    public static AlarmController.AlarmForm create() {
        return AlarmController.AlarmForm.builder()
                .x(0)
                .y(0)
                .title("title")
                .message("message")
                .build();
    }
}
