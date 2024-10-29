package com.example.checkinrequestMS.HelpAPI.application.service.progress.business;

import com.example.checkinrequestMS.HelpAPI.domain.model.help.Progress;
import com.example.checkinrequestMS.HelpAPI.domain.model.help.child.CheckIn;
import com.example.checkinrequestMS.HelpAPI.infra.db.stub.HelpDBAdapterStub;
import com.example.checkinrequestMS.HelpAPI.infra.fileIO.PhotoSaver;
import com.example.checkinrequestMS.fixtures.HelpAPI.domain.model.help.child.CheckInFixture;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.multipart.MultipartFile;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
class ProgressBusinessWriteServiceTest {

    @InjectMocks
    private ProgressBusinessWriteService sut;

    @Spy
    private HelpDBAdapterStub helpDBAdapter;

    @Mock
    private PhotoSaver photoSaver;

    @Test
    void approveProgress() {
        //given
        CheckIn checkIn = CheckInFixture.createCheckInWithId(Progress.DEFAULT, 1L);
        checkIn.start(1L);
        checkIn.authenticate("photoPath");
        helpDBAdapter.save(checkIn);

        //when
        Long id = sut.approveProgress(1L);

        //then
        assertEquals(1L, id);
        CheckIn checkInReturned = (CheckIn) helpDBAdapter.findById(id);
        assertEquals(checkInReturned.getProgress().getStatus(), Progress.ProgressStatus.COMPLETED);
        assertEquals(checkInReturned.getProgress().isCompleted(), true);

    }

    @Test
    void addPhoto() {

        //given
        CheckIn checkIn = CheckInFixture.createCheckInWithId(Progress.DEFAULT, 1L);
        checkIn.start(1L);
        helpDBAdapter.save(checkIn);

        MultipartFile photo = mock(MultipartFile.class);
        given(photoSaver.saveOnePhoto(1L, photo)).willReturn("photoPath");

        //when
        Long id = sut.addPhoto(1L, photo);

        //then
        assertEquals(1L, id);
        CheckIn checkInReturned = (CheckIn) helpDBAdapter.findById(id);
        assertEquals(checkInReturned.getProgress().getStatus(), Progress.ProgressStatus.AUTHENTICATED);
        assertEquals(checkInReturned.getProgress().getPhotoPath().get(), "photoPath");
    }
}