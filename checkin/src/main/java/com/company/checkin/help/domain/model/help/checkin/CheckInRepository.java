package com.company.checkin.help.domain.model.help.checkin;

public interface CheckInRepository {

    public CheckIn save(CheckIn checkIn);

    public CheckIn findById(Long id);

    public CheckIn update(CheckIn checkIn);
}
