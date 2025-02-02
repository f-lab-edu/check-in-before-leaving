package com.example.checkinrequestMS.HelpAPI.domain.model.help.child;

import javax.sound.sampled.Line;

public interface LineUpRepository {

    public LineUp save(LineUp lineUp);

    public LineUp findById(Long id);

    public LineUp update(LineUp lineUp);
}
