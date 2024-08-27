package com.example.checkinrequestMS.HelpAPI.domain.service.LineUp;

import com.example.checkinrequestMS.HelpAPI.domain.entities.help.child.LineUp;
import com.example.checkinrequestMS.HelpAPI.domain.exceptions.help.HelpException;
import com.example.checkinrequestMS.HelpAPI.infra.db.help.LineUpJPARepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.example.checkinrequestMS.HelpAPI.domain.exceptions.help.HelpErrorCode.NO_LINE_UP_INFO;

@Service
@RequiredArgsConstructor
public class LineUpSelectService {

    private final LineUpJPARepository lineUpJPARepository;

    @Transactional
    public LineUp selectLineUp(Long id) {
        return lineUpJPARepository.findById(id).orElseThrow(
                () -> new HelpException(NO_LINE_UP_INFO));
    }
}
