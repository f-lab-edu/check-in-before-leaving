package com.example.checkinrequestMS.HelpAPI.infra.db.progress;

import com.example.checkinrequestMS.HelpAPI.domain.entities.progress.Progress;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProgressSpringJPARepository extends JpaRepository<Progress, Long> {
}
