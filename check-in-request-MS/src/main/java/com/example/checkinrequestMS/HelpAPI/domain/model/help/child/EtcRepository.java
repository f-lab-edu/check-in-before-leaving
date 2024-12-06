package com.example.checkinrequestMS.HelpAPI.domain.model.help.child;

import org.springframework.stereotype.Repository;

@Repository
public interface EtcRepository {

    public Long save(Etc etc);

    public Etc findById(Long id);
}
