package com.example.checkinrequestMS.HelpAPI.infra.db.help;

import com.example.checkinrequestMS.HelpAPI.domain.entities.help.child.CheckIn;
import com.example.checkinrequestMS.HelpAPI.infra.aop.aspect.JPARead;
import com.example.checkinrequestMS.HelpAPI.infra.aop.aspect.JPASave;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class CheckInJPARepository {
    private final CheckInSpringJPARepository checkInSpringJPARepository;

    @JPASave
    public Long save(CheckIn checkIn) {
        CheckIn checkin = checkInSpringJPARepository.save(checkIn);
        return checkin.getId();
    }
    @JPARead
    public Optional<CheckIn> findById(Long id){
        return checkInSpringJPARepository.findById(id);
    }

}
