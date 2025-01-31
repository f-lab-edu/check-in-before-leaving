package com.example.checkinrequestMS.HelpAPI.domain.model.help.child;

public interface CheckInRepository {

    public CheckIn save(CheckIn checkIn);
    
    public CheckIn findById(Long id);

    public CheckIn update(CheckIn checkIn);
}
