package com.company.checkin.help.infra.adapter.db.repository.help.checkin;

import com.company.checkin.help.infra.adapter.db.entity.help.checkin.CheckInEntity;
import com.company.checkin.help.infra.adapter.db.entity.help.checkin.CheckInEntity2;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CheckInSpringJPARepository2 extends JpaRepository<CheckInEntity2, Long> {

}
