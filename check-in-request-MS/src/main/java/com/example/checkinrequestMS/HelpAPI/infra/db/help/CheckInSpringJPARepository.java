package com.example.checkinrequestMS.HelpAPI.infra.db.help;

import com.example.checkinrequestMS.HelpAPI.infra.db.entity.child.CheckInEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CheckInSpringJPARepository extends JpaRepository<CheckInEntity, Long> {

}
