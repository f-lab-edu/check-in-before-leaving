package com.example.checkinrequestMS.HelpAPI.domain.model.help.child.dto;

import com.example.checkinrequestMS.HelpAPI.domain.model.help.HelpDetail;
import com.example.checkinrequestMS.HelpAPI.domain.model.help.Progress;
import com.example.checkinrequestMS.HelpAPI.domain.model.help.child.LineUp;
import com.example.checkinrequestMS.HelpAPI.domain.model.help.child.LineUpService;
import com.example.checkinrequestMS.fixtures.HelpAPI.LineUpFixtures;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

public class LineUpDTOTest {

    @Test
    @DisplayName("DTO를 비교한다")
    void equals() {
        //Given
        LineUp.DTO sut = LineUpFixtures.LineUpT.createBasicDTO();
        LineUp.DTO copy = LineUpFixtures.LineUpT.createBasicDTO();

        //When & Then
        assertTrue(sut.equals(copy));
    }

    @Test
    @DisplayName("값이 다르면 비교 실패.")
    void test() {
        //Given
        LineUp.DTO custome = LineUp.DTO.builder()
                .id(1L)
                .title("title")
                .reward(100L)
                .helperId(1L)
                .helpRegisterId(1L)
                .placeId("1")
                .start(LocalDateTime.of(1999, 1, 1, 1, 1))
                .end(LocalDateTime.of(1999, 1, 1, 1, 2))
                .status(new Progress.Created())
                .helperId(1L)
                .photoPath("photoPath")
                .completed(false)
                .build();
        LineUp.DTO sut = LineUpFixtures.LineUpT.createBasicDTO();

        //When & Then
        assertFalse(sut.equals(custome));
    }

    @Test
    @DisplayName("HashCode - DTO를 비교한다")
    void hashequals() {
        //Given
        LineUp.DTO sut = LineUpFixtures.LineUpT.createBasicDTO();
        LineUp.DTO copy = LineUpFixtures.LineUpT.createBasicDTO();

        //When & Then
        assertEquals(sut.hashCode(), copy.hashCode());

    }

    @Test
    @DisplayName("HashCode - 값이 다르면 비교 실패.")
    void hashtest() {
        //Given
        LineUp.DTO custome = LineUp.DTO.builder()
                .id(1L)
                .title("title")
                .reward(100L)
                .helperId(1L)
                .helpRegisterId(1L)
                .placeId("1")
                .start(LocalDateTime.of(1999, 1, 1, 1, 1))
                .end(LocalDateTime.of(1999, 1, 1, 1, 2))
                .status(new Progress.Created())
                .helperId(1L)
                .photoPath("photoPath")
                .completed(false)
                .build();
        LineUp.DTO sut = LineUpFixtures.LineUpT.createBasicDTO();

        //When & Then
        assertNotEquals(sut.hashCode(), custome.hashCode());
    }

    @Test
    @DisplayName("LineUp.DTO getDTO 메서드 테스트")
    void testGetDTO() {
        // Given
        HelpDetail helpDetail = HelpDetail.builder()
                .helpRegisterId(1L)
                .placeId("placeId")
                .start(LocalDateTime.of(2021, 1, 1, 1, 1))
                .end(LocalDateTime.of(2021, 1, 1, 1, 11))
                .reward(100L)
                .title("title")
                .build();
        Progress progress = Progress.builder()
                .helperId(1L)
                .photoPath("photoPath")
                .status(new Progress.Created())
                .completed(false)
                .build();
        LineUp lineUp = LineUp.builder()
                .id(1L)
                .helpDetail(helpDetail)
                .progress(progress)
                .build();

        // When
        LineUp.DTO sut = LineUp.DTO.getDTO(lineUp);
        // Then
        assertEquals(sut.getId(), lineUp.getId());
        assertEquals(sut.getHelpRegisterId(), helpDetail.getHelpRegisterId());
        assertEquals(sut.getPlaceId(), helpDetail.getPlaceId());
        assertEquals(sut.getStart(), helpDetail.getStart());
        assertEquals(sut.getEnd(), helpDetail.getEnd());
        assertEquals(sut.getReward(), helpDetail.getReward());
        assertEquals(sut.getTitle(), helpDetail.getTitle());
        assertThat(sut.getHelperId()).isNotEmpty().hasValue(progress.getHelperId());
        assertThat(sut.getPhotoPath()).isNotEmpty().hasValue(progress.getPhotoPath());
        assertEquals(sut.getStatus(), progress.getStatus());
        assertEquals(sut.isCompleted(), progress.isCompleted());

    }

    @Test
    @DisplayName("LineUp.DTO getRegDTO 메서드 테스트")
    void testRegGetDTO() {
        // Given
        LineUpService.Creation dto = LineUpFixtures.LineUpServiceT.CreationT.create();
        LineUp lineUp = LineUp.register(dto);

        // When
        LineUp.DTO expectedDTO = LineUp.DTO.getDTOForCreation(lineUp);

        // Then
        assertEquals(expectedDTO.getId(), lineUp.getId());
        assertEquals(expectedDTO.getHelpRegisterId(), dto.getHelpRegisterId());
        assertEquals(expectedDTO.getPlaceId(), dto.getPlaceId());
        assertEquals(expectedDTO.getStart(), dto.getStart());
        assertEquals(expectedDTO.getEnd(), dto.getEnd());
        assertEquals(expectedDTO.getReward(), dto.getReward());
        assertEquals(expectedDTO.getTitle(), LineUpService.CreationInitializer.createTitle(dto.getPlaceName()));
        assertThat(expectedDTO.getHelperId()).isEmpty();
        assertThat(expectedDTO.getPhotoPath()).isEmpty();
        assertEquals(expectedDTO.getStatus(), new Progress.Created());
        assertEquals(expectedDTO.isCompleted(), false);
    }


}
