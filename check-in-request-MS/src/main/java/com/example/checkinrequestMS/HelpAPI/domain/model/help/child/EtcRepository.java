package com.example.checkinrequestMS.HelpAPI.domain.model.help.child;

import org.springframework.stereotype.Repository;

@Repository
public interface EtcRepository {

    public Etc save(Etc etc);

    public Etc findById(Long id);

    public Etc update(Etc etc);
}
