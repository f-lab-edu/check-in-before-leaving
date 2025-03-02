package com.example.checkinrequestMS.HelpAPI.domain.model.help.child.dto;

import com.example.checkinrequestMS.HelpAPI.domain.model.help.HelpDetail;
import com.example.checkinrequestMS.HelpAPI.domain.model.help.Progress;
import com.example.checkinrequestMS.HelpAPI.domain.model.help.child.Etc;
import com.example.checkinrequestMS.HelpAPI.domain.model.help.child.EtcService;
import com.example.checkinrequestMS.fixtures.HelpAPI.EtcFixtures;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

public class EtcDTOTest {

    @Test
    @DisplayName("DTO를 비교한다")
    void equals() {
        //Given
        Etc.DTO sut = EtcFixtures.EtcT.createBasicDTO();
        Etc.DTO copy = EtcFixtures.EtcT.createBasicDTO();

        //When & Then
        assertTrue(sut.equals(copy));
    }

    @Test
    @DisplayName("값이 다르면 비교 실패.")
    void test() {
        //Given
        Etc.DTO custome = Etc.DTO.builder()
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
                .contents("contents")
                .photoPath("photoPath")
                .completed(false)
                .build();
        Etc.DTO sut = EtcFixtures.EtcT.createBasicDTO();

        //When & Then
        assertFalse(sut.equals(custome));
    }

    @Test
    @DisplayName("HashCode - DTO를 비교한다")
    void hashequals() {
        //Given
        Etc.DTO sut = EtcFixtures.EtcT.createBasicDTO();
        Etc.DTO copy = EtcFixtures.EtcT.createBasicDTO();

        //When & Then
        assertEquals(sut.hashCode(), copy.hashCode());

    }

    @Test
    @DisplayName("HashCode - 값이 다르면 비교 실패.")
    void hashtest() {
        //Given
        Etc.DTO custome = Etc.DTO.builder()
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
                .contents("contents")
                .photoPath("photoPath")
                .completed(false)
                .build();
        Etc.DTO sut = EtcFixtures.EtcT.createBasicDTO();

        //When & Then
        assertNotEquals(sut.hashCode(), custome.hashCode());
    }

    @Test
    @DisplayName("Etc.DTO getDTO 메서드 테스트")
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
        Etc etc = Etc.builder()
                .id(1L)
                .contents("contents")
                .helpDetail(helpDetail)
                .progress(progress)
                .build();

        // When
        Etc.DTO sut = Etc.DTO.getDTO(etc);
        // Then
        assertEquals(sut.getId(), etc.getId());
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
    @DisplayName("Etc.DTO getRegDTO 메서드 테스트")
    void testRegGetDTO() {
        // Given
        EtcService.Creation dto = EtcFixtures.EtcServiceT.CreationT.create();
        Etc etc = Etc.register(dto);

        // When
        Etc.DTO expectedDTO = Etc.DTO.getDTOForCreation(etc);

        // Then
        assertEquals(expectedDTO.getId(), etc.getId());
        assertEquals(expectedDTO.getHelpRegisterId(), dto.getHelpRegisterId());
        assertEquals(expectedDTO.getPlaceId(), dto.getPlaceId());
        assertEquals(expectedDTO.getStart(), dto.getStart());
        assertEquals(expectedDTO.getEnd(), dto.getEnd());
        assertEquals(expectedDTO.getReward(), dto.getReward());
        assertEquals(expectedDTO.getTitle(), dto.getTitle());
        assertThat(expectedDTO.getHelperId()).isEmpty();
        assertThat(expectedDTO.getPhotoPath()).isEmpty();
        assertEquals(expectedDTO.getStatus(), new Progress.Created());
        assertEquals(expectedDTO.isCompleted(), false);
    }


}
