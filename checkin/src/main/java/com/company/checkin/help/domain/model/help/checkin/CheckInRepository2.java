package com.company.checkin.help.domain.model.help.checkin;

public interface CheckInRepository2 {

    public CheckIn2 save(CheckIn2 checkIn);

    public CheckIn2 findById(Long id);

    public CheckIn2 update(CheckIn2 checkIn);
}
