package com.example.checkinrequestMS.HelpAPI.infra.db.help;

import com.example.checkinrequestMS.HelpAPI.domain.model.help.Progress;
import com.example.checkinrequestMS.HelpAPI.domain.model.help.child.CheckIn;
import com.example.checkinrequestMS.HelpAPI.infra.db.stub.HelpSpringJPARepositoryStub;
import com.example.checkinrequestMS.fixtures.HelpAPI.domain.model.help.child.CheckInFixture;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class HelpJPARepositoryTest_Stub {

    @InjectMocks
    private HelpJPARepository sut;

    @Spy
    private HelpSpringJPARepositoryStub helpSpringJPARepository;

    @Test
    void save() {
        //given
        CheckIn checkIn = CheckInFixture.createCheckInNoId(Progress.DEFAULT);

        //when
        Long id = sut.save(checkIn);

        //then
        assertEquals(checkIn.getHelpRegisterId(), id);
    }

    @Test
    void findById() {
        //given
        Long id = sut.save(CheckInFixture.createCheckInNoId(Progress.DEFAULT));

        //when
        CheckIn checkIn = (CheckIn) sut.findById(id);

        //then
        assertEquals(id, checkIn.getHelpRegisterId());
    }
}