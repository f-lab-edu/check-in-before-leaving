package com.example.checkinrequestMS.HelpAPI.infra.db.help;

import com.example.checkinrequestMS.HelpAPI.domain.entities.help.child.Etc;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EtcSpringJPARepository extends JpaRepository<Etc, Long> {
}
