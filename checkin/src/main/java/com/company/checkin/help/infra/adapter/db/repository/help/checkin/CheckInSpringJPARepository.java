package com.company.checkin.help.infra.adapter.db.repository.help.checkin;

import com.company.checkin.help.infra.adapter.db.entity.help.checkin.CheckInEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CheckInSpringJPARepository extends JpaRepository<CheckInEntity, Long> {

}
