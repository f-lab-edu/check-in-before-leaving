package com.example.checkinrequestMS.HelpAPI.infra.db.help;

import com.example.checkinrequestMS.HelpAPI.domain.entities.help.child.LineUp;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LineUpSpringJPARepository extends JpaRepository<LineUp, Long> {
}
