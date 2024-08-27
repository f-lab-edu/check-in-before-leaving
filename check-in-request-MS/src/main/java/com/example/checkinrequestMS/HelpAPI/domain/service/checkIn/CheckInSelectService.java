package com.example.checkinrequestMS.HelpAPI.domain.service.checkIn;

import com.example.checkinrequestMS.HelpAPI.domain.entities.help.child.CheckIn;
import com.example.checkinrequestMS.HelpAPI.domain.exceptions.help.HelpException;
import com.example.checkinrequestMS.HelpAPI.infra.db.help.CheckInJPARepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.example.checkinrequestMS.HelpAPI.domain.exceptions.help.HelpErrorCode.NO_CHECK_IN_INFO;

@Service
@RequiredArgsConstructor
public class CheckInSelectService {

    private final CheckInJPARepository checkInJPARepository;

    @Transactional
    public CheckIn selectCheckIn(Long id) {
        return checkInJPARepository.findById(id).orElseThrow(
                () -> new HelpException(NO_CHECK_IN_INFO)
        );
    }

}
