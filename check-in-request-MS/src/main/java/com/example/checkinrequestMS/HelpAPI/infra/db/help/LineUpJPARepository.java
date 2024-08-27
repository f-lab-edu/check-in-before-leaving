package com.example.checkinrequestMS.HelpAPI.infra.db.help;

import com.example.checkinrequestMS.HelpAPI.domain.entities.help.child.LineUp;
import com.example.checkinrequestMS.HelpAPI.domain.exceptions.help.HelpException;
import com.example.checkinrequestMS.HelpAPI.infra.aop.aspect.JPARead;
import com.example.checkinrequestMS.HelpAPI.infra.aop.aspect.JPASave;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import static com.example.checkinrequestMS.HelpAPI.domain.exceptions.help.HelpErrorCode.NO_LINE_UP_INFO;

@Repository
@RequiredArgsConstructor
public class LineUpJPARepository {
    private final LineUpSpringJPARepository lineUpSpringJPARepository;

    @JPASave
    public void save(LineUp lineUp){
        lineUpSpringJPARepository.save(lineUp);
    }

    @JPARead
    public Optional<LineUp> findById(Long id){
        return lineUpSpringJPARepository.findById(id);
    }
}
