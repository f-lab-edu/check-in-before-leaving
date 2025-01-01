package com.example.checkinrequestMS.HelpAPI.domain.model.help.child;

public interface CheckInRepository {

    public Long save(CheckIn checkIn);

    public CheckIn findById(Long id);

    public CheckIn update(CheckInService.Update dto);
}
