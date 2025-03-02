package com.company.checkin.help.domain.model.help.lineup;

public interface LineUpRepository {

    public LineUp save(LineUp lineUp);

    public LineUp findById(Long id);

    public LineUp update(LineUp lineUp);
}
