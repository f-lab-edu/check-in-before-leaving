package com.example.checkinrequestMS.HelpAPI.infra.db.progress;

import com.example.checkinrequestMS.HelpAPI.domain.entities.progress.Progress;
import com.example.checkinrequestMS.HelpAPI.infra.aop.aspect.JPARead;
import com.example.checkinrequestMS.HelpAPI.infra.aop.aspect.JPASave;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
@RequiredArgsConstructor
public class ProgressJPARepository {

    private final ProgressSpringJPARepository progressSpringJPARepository;

    @JPASave
    public void save(Progress progress) {
        progressSpringJPARepository.save(progress);
    }

    @JPARead
    public Optional<Progress> findById(Long id) {
        return progressSpringJPARepository.findById(id);
    }
}
