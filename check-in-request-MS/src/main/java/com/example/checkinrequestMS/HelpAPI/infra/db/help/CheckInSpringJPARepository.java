package com.example.checkinrequestMS.HelpAPI.infra.db.help;

import com.example.checkinrequestMS.HelpAPI.domain.entities.help.child.CheckIn;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CheckInSpringJPARepository extends JpaRepository<CheckIn, Long> {
}
