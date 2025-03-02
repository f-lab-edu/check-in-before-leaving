package com.company.checkin.help.infra.adapter.db.repository.help.lineup;

import com.company.checkin.help.infra.adapter.db.entity.help.lineup.LineUpEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LineUpSpringJPARepository extends JpaRepository<LineUpEntity, Long> {

}
