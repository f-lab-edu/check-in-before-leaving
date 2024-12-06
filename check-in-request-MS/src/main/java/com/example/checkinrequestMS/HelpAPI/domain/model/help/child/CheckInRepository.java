package com.example.checkinrequestMS.HelpAPI.domain.model.help.child;

import org.springframework.stereotype.Repository;

@Repository
public interface CheckInRepository {

    public Long save(CheckIn checkIn);

    public CheckIn findById(Long id);
}
