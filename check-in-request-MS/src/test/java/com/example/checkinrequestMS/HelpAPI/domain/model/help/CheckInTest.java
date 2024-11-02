package com.example.checkinrequestMS.HelpAPI.domain.model.help;

import com.example.checkinrequestMS.HelpAPI.domain.dto.write.register.child.CheckInRegisterDTO;
import com.example.checkinrequestMS.HelpAPI.domain.model.help.child.CheckIn;
import com.example.checkinrequestMS.HelpAPI.infra.db.entity.child.CheckInJPAEntity;
import com.example.checkinrequestMS.PlaceAPI.domain.Place;
import com.example.checkinrequestMS.fixtures.HelpAPI.domain.dto.register.CheckInRegisterDTOFixture;
import com.example.checkinrequestMS.fixtures.HelpAPI.infra.entity.CheckInJPAEntityFixture;
import org.junit.jupiter.api.Test;

import static com.example.checkinrequestMS.HelpAPI.domain.model.help.child.CheckIn.CHECK_IN_TITLE;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class CheckInTest {

    @Test
    void of() {
        //given
        CheckInRegisterDTO dto = CheckInRegisterDTOFixture.create();

        Place place = mock(Place.class);
        given(place.getPlaceName()).willReturn("placeName");

        //when
        CheckIn sut = CheckIn.of(dto, place, Progress.DEFAULT);

        //then
        assertEquals(dto.getHelpRegisterId(), sut.getHelpRegisterId());
        assertEquals(place.getPlaceName() + CHECK_IN_TITLE, sut.getTitle());
        assertEquals(dto.getPlaceId(), sut.getPlaceId());
        assertEquals(dto.getReward(), sut.getReward());
        assertEquals(dto.getStart(), sut.getStart());
        assertEquals(dto.getEnd(), sut.getEnd());
    }

    @Test
    void from() {
        //given
        CheckInJPAEntity entity = CheckInJPAEntityFixture.createWithId(1L);

        //when
        CheckIn checkIn = CheckIn.from(entity);

        //then
        assertEquals(entity.getId(), checkIn.getHelpRegisterId());
        assertEquals(entity.getTitle(), checkIn.getTitle());
        assertEquals(entity.getHelpRegisterId(), checkIn.getHelpRegisterId());
        assertEquals(entity.getPlaceId(), checkIn.getPlaceId());
        assertEquals(entity.getReward(), checkIn.getReward());
        assertEquals(entity.getStart(), checkIn.getStart());
        assertEquals(entity.getEnd(), checkIn.getEnd());
        assertEquals(entity.getProgressVO().isCompleted(), false);
        assertEquals(entity.getProgressVO().getStatus(), Progress.ProgressStatus.CREATED);
    }
}