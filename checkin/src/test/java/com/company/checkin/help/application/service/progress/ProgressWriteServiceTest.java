//package com.example.checkinrequestMS.HelpAPI.application.service.progress;
//
//import com.example.checkinrequestMS.HelpAPI.application.service.progress.write.ProgressWriteService;
//import com.example.checkinrequestMS.HelpAPI.domain.model.help.Progress;
//import com.example.checkinrequestMS.HelpAPI.domain.model.help.child.CheckIn;
//import com.example.checkinrequestMS.HelpAPI.infra.db.stub.HelpDBAdapterStub;
//import com.example.checkinrequestMS.fixtures.HelpAPI.domain.model.help.child.CheckInFixture;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Spy;
//import org.mockito.junit.jupiter.MockitoExtension;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//
//@ExtendWith(MockitoExtension.class)
//class ProgressWriteServiceTest {
//
//    @InjectMocks
//    private ProgressWriteService sut;
//
//    @Spy
//    private HelpDBAdapterStub helpDBAdapter;
//
//    @Test
//    void registerHelper() {
//
//        //given
//        CheckIn checkIn = CheckInFixture.createCheckInNoId(Progress.DEFAULT);
//        Long helpId = helpDBAdapter.save(checkIn);
//
//
//        //when
//        Long returnedId = sut.registerHelper(helpId, 2L);
//
//        //then
//        assertEquals(returnedId, helpId);
//        CheckIn checkInReturned = (CheckIn) helpDBAdapter.findById(returnedId);
//        assertEquals(checkInReturned.getProgress().getStatus(), Progress.ProgressStatus.ONGOING);
//        assertEquals(checkInReturned.getProgress().getHelperId().get(), 2L);
//        assertEquals(checkInReturned.getProgress().isCompleted(), false);
//    }
//}