package com.example.checkinrequestMS.HelpAPI.infra.db.help;

import com.example.checkinrequestMS.HelpAPI.infra.db.entity.HelpJPAEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface HelpSpringJPARepository extends JpaRepository<HelpJPAEntity, Long> {

    @Query("SELECT e FROM HelpJPAEntity e WHERE e.placeId IN :placeIds")
    public List<HelpJPAEntity> findAllByPlaceId(List<String> placeIds);
}
