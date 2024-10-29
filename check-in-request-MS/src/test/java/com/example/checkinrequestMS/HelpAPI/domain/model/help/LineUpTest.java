package com.example.checkinrequestMS.HelpAPI.domain.model.help;

import com.example.checkinrequestMS.HelpAPI.domain.dto.write.register.child.LineUpRegisterDTO;
import com.example.checkinrequestMS.HelpAPI.domain.model.help.child.LineUp;
import com.example.checkinrequestMS.HelpAPI.infra.db.entity.child.LineUpJPAEntity;
import com.example.checkinrequestMS.PlaceAPI.domain.Place;
import com.example.checkinrequestMS.fixtures.HelpAPI.domain.dto.register.LineUpRegisterDTOFixture;
import com.example.checkinrequestMS.fixtures.HelpAPI.infra.entity.LineUpJPAEntityFixture;
import org.junit.jupiter.api.Test;


import static com.example.checkinrequestMS.HelpAPI.domain.model.help.child.LineUp.LINE_UP_TITLE;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class LineUpTest {

    @Test
    void of() {
        //given
        LineUpRegisterDTO dto = LineUpRegisterDTOFixture.create();

        Place place = mock(Place.class);
        given(place.getPlaceName()).willReturn("placeName");

        //when
        LineUp sut = LineUp.of(dto, place, Progress.DEFAULT);

        //then
        assertEquals(dto.getHelpRegisterId(), sut.getHelpRegisterId());
        assertEquals(place.getPlaceName() + LINE_UP_TITLE, sut.getTitle());
        assertEquals(dto.getPlaceId(), sut.getPlaceId());
        assertEquals(dto.getReward(), sut.getReward());
        assertEquals(dto.getStart(), sut.getStart());
        assertEquals(dto.getEnd(), sut.getEnd());
    }

    @Test
    void from() {
        //given
        LineUpJPAEntity entity = LineUpJPAEntityFixture.createWithId(1L);

        //when
        LineUp lineUp = LineUp.from(entity);

        //then
        assertEquals(entity.getId(), lineUp.getHelpRegisterId());
        assertEquals(entity.getTitle(), lineUp.getTitle());
        assertEquals(entity.getHelpRegisterId(), lineUp.getHelpRegisterId());
        assertEquals(entity.getPlaceId(), lineUp.getPlaceId());
        assertEquals(entity.getReward(), lineUp.getReward());
        assertEquals(entity.getStart(), lineUp.getStart());
        assertEquals(entity.getEnd(), lineUp.getEnd());
        assertEquals(entity.getProgressVO().isCompleted(), false);
        assertEquals(entity.getProgressVO().getStatus(), Progress.ProgressStatus.CREATED);
    }
}