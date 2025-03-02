package com.company.checkin.help.infra.adapter.db.repository.help.etc;

import com.company.checkin.help.infra.adapter.db.entity.help.etc.EtcEntity;
import org.springframework.data.jpa.repository.JpaRepository;


public interface EtcSpringJPARepository extends JpaRepository<EtcEntity, Long> {

}
