package com.example.checkinrequestMS.HelpAPI.domain.model.help.child;

import org.springframework.stereotype.Repository;

@Repository
public interface LineUpRepository {

    public Long save(LineUp lineUp);

    public LineUp findById(Long id);
}
