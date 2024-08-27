package com.example.checkinrequestMS.HelpAPI.infra.db.help;

import com.example.checkinrequestMS.HelpAPI.domain.entities.help.Help;
import com.example.checkinrequestMS.HelpAPI.domain.exceptions.help.HelpErrorCode;
import com.example.checkinrequestMS.HelpAPI.domain.exceptions.help.HelpException;
import com.example.checkinrequestMS.HelpAPI.infra.aop.aspect.JPARead;
import com.example.checkinrequestMS.HelpAPI.infra.aop.aspect.JPASave;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import static com.example.checkinrequestMS.HelpAPI.domain.exceptions.help.HelpErrorCode.NO_HELP_INFO;

@Repository
@RequiredArgsConstructor
public class HelpJPARepository {
    private final HelpSpringJPARepository helpSpringJPARepository;

    @JPASave
    public void save(Help help){
        helpSpringJPARepository.save(help);
    }

    @JPARead
    public Optional<Help> findById(Long id){
        return helpSpringJPARepository.findById(id);
    }
}
