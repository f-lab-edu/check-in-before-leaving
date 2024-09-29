package com.membercontext.memberAPI.web.controller.fixture;

import com.membercontext.memberAPI.web.controller.form.UpdateForm;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class UpdateFormTestFixture {

    public UpdateForm createAllFilledUpdateForm_Mock() {
        UpdateForm form = mock(UpdateForm.class);

        when(form.getId()).thenReturn("UPDATING_ID");
        when(form.getName()).thenReturn("tester");
        when(form.getEmail()).thenReturn("test@test.com");
        when(form.getPassword()).thenReturn("testPassword");
        when(form.getPhone()).thenReturn("010-1234-5678");
        when(form.getLocation()).thenReturn("testLocation");
        when(form.getIsLocationServiceEnabled()).thenReturn(true);
        when(form.getPoint()).thenReturn(0L);
        return form;
    }
}
