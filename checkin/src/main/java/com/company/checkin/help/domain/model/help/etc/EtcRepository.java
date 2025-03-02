package com.company.checkin.help.domain.model.help.etc;

import org.springframework.stereotype.Repository;

@Repository
public interface EtcRepository {

    public Etc save(Etc etc);

    public Etc findById(Long id);

    public Etc update(Etc etc);
}
