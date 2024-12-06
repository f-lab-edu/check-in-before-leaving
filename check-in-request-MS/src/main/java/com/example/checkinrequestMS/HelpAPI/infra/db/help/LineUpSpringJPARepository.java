package com.example.checkinrequestMS.HelpAPI.infra.db.help;

import com.example.checkinrequestMS.HelpAPI.infra.db.entity.child.CheckInEntity;
import com.example.checkinrequestMS.HelpAPI.infra.db.entity.child.LineUpEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LineUpSpringJPARepository extends JpaRepository<LineUpEntity, Long> {

}
