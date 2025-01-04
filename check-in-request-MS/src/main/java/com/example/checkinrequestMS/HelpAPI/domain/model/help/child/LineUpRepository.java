package com.example.checkinrequestMS.HelpAPI.domain.model.help.child;

public interface LineUpRepository {

    public Long save(LineUp lineUp);

    public LineUp findById(Long id);

    public LineUp update(LineUp lineUp);
}
