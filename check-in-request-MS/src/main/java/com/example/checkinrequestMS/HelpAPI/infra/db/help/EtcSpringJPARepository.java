package com.example.checkinrequestMS.HelpAPI.infra.db.help;

import com.example.checkinrequestMS.HelpAPI.infra.db.entity.child.CheckInEntity;
import com.example.checkinrequestMS.HelpAPI.infra.db.entity.child.EtcEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EtcSpringJPARepository extends JpaRepository<EtcEntity, Long> {

}
