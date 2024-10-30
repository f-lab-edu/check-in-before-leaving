package com.example.checkinrequestMS.HelpAPI.domain.model.help;

import com.example.checkinrequestMS.HelpAPI.domain.dto.write.register.child.EtcRegisterDTO;
import com.example.checkinrequestMS.HelpAPI.domain.model.help.ProgressVO.Created;
import com.example.checkinrequestMS.HelpAPI.domain.model.help.child.Etc;
import com.example.checkinrequestMS.HelpAPI.infra.db.entity.ProgressValue;
import com.example.checkinrequestMS.HelpAPI.infra.db.entity.child.EtcJPAEntity;
import com.example.checkinrequestMS.PlaceAPI.domain.Place;
import com.example.checkinrequestMS.fixtures.HelpAPI.domain.dto.register.EtcRegisterDTOFixture;
import com.example.checkinrequestMS.fixtures.HelpAPI.infra.entity.EtcJPAEntityFixture;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class EtcTest {

    @Test
    void of() {
        //given
        EtcRegisterDTO dto = EtcRegisterDTOFixture.create();

        Place place = mock(Place.class);
        given(place.getPlaceName()).willReturn("placeName");

        //when
        Etc<Created> sut = Etc.of(dto, Created.create());

        //then
        assertEquals(dto.getHelpRegisterId(), sut.getHelpRegisterId());
        assertEquals(dto.getTitle(), sut.getTitle());
        assertEquals(dto.getPlaceId(), sut.getPlaceId());
        assertEquals(dto.getReward(), sut.getReward());
        assertEquals(dto.getStart(), sut.getStart());
        assertEquals(dto.getEnd(), sut.getEnd());
        assertEquals(dto.getContents(), sut.getContents());
    }

    @Test
    void from() {
        //given
        EtcJPAEntity entity = EtcJPAEntityFixture.createWithId(1L);

        //when
        Etc etc = Etc.from(entity);

        //then
        assertEquals(entity.getId(), etc.getHelpRegisterId());
        assertEquals(entity.getTitle(), etc.getTitle());
        assertEquals(entity.getHelpRegisterId(), etc.getHelpRegisterId());
        assertEquals(entity.getPlaceId(), etc.getPlaceId());
        assertEquals(entity.getReward(), etc.getReward());
        assertEquals(entity.getStart(), etc.getStart());
        assertEquals(entity.getEnd(), etc.getEnd());
        assertEquals(entity.getProgressValue().isCompleted(), false);
        assertEquals(entity.getProgressValue().getStatus(), ProgressValue.CREATED);
        assertEquals(entity.getContents(), etc.getContents());
    }
}