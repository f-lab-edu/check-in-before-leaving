package com.example.checkinrequestMS.HelpAPI.infra.db.help;

import com.example.checkinrequestMS.HelpAPI.domain.entities.help.child.Etc;
import com.example.checkinrequestMS.HelpAPI.infra.aop.aspect.JPARead;
import com.example.checkinrequestMS.HelpAPI.infra.aop.aspect.JPASave;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class EtcJPARepository {

    private final EtcSpringJPARepository etcSpringJPARepository;

    @JPASave
    public void save(Etc etc){
        etcSpringJPARepository.save(etc);
    }

    @JPARead
    public Optional<Etc> findById(Long id){
        return etcSpringJPARepository.findById(id);
    }
}
