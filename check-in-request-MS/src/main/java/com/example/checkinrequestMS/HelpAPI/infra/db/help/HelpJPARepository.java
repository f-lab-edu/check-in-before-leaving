package com.example.checkinrequestMS.HelpAPI.infra.db.help;

import com.example.checkinrequestMS.HelpAPI.domain.entities.help.Help;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HelpJPARepository extends JpaRepository<Help, Long> {

}
