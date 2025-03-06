package com.company.checkin.help.domain.model.help.checkin;

import com.company.checkin.help.domain.model.help.HelpDetail;
import com.company.checkin.help.domain.model.help.Progress;
import com.company.checkin.fixtures.checkin.help.CheckInFixtures;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class CheckInDTOTest {

    @Nested
    @DisplayName("equals")
    class equalsTest {
        @Test
        @DisplayName("equals - null 비교")
        void equalsWithNull() {
            CheckIn.DTO sut = CheckInFixtures.CheckInT.createBasicDTO();
            assertNotEquals(null, sut);
        }

        @Test
        @DisplayName("equals - 다른 클래스 비교")
        void equalsWithDifferentClass() {
            CheckIn.DTO sut = CheckInFixtures.CheckInT.createBasicDTO();
            assertNotEquals(sut, new Object());
        }

        @Test
        @DisplayName("equals - 자기 자신과 비교")
        void equalsWithSameObject() {
            CheckIn.DTO sut = CheckInFixtures.CheckInT.createBasicDTO();
            assertEquals(sut, sut);
        }

        @Test
        @DisplayName("equals - 각 필드별 비교 테스트")
        void equalsFieldByField() {
            CheckIn.DTO original = CheckInFixtures.CheckInT.createBasicDTO();

            // id가 다른 경우
            CheckIn.DTO diffId = CheckIn.DTO.builder()
                    .id(999L)
                    .title(original.getTitle())
                    .reward(original.getReward())
                    .helperId(original.getHelperId().orElse(null))
                    .helpRegisterId(original.getHelpRegisterId())
                    .placeId(original.getPlaceId())
                    .start(original.getStart())
                    .end(original.getEnd())
                    .status(original.getStatus())
                    .photoPath(original.getPhotoPath().orElse(null))
                    .completed(original.isCompleted())
                    .build();
            assertNotEquals(original, diffId);

            // title이 다른 경우
            CheckIn.DTO diffTitle = CheckIn.DTO.builder()
                    .id(original.getId())
                    .title("다른 제목")
                    .reward(original.getReward())
                    .helperId(original.getHelperId().orElse(null))
                    .helpRegisterId(original.getHelpRegisterId())
                    .placeId(original.getPlaceId())
                    .start(original.getStart())
                    .end(original.getEnd())
                    .status(original.getStatus())
                    .photoPath(original.getPhotoPath().orElse(null))
                    .completed(original.isCompleted())
                    .build();
            assertNotEquals(original, diffTitle);

            // reward가 다른 경우
            CheckIn.DTO diffReward = CheckIn.DTO.builder()
                    .id(original.getId())
                    .title(original.getTitle())
                    .reward(999L)
                    .helperId(original.getHelperId().orElse(null))
                    .helpRegisterId(original.getHelpRegisterId())
                    .placeId(original.getPlaceId())
                    .start(original.getStart())
                    .end(original.getEnd())
                    .status(original.getStatus())
                    .photoPath(original.getPhotoPath().orElse(null))
                    .completed(original.isCompleted())
                    .build();
            assertNotEquals(original, diffReward);

            // helperId가 다른 경우
            CheckIn.DTO diffHelperId = CheckIn.DTO.builder()
                    .id(original.getId())
                    .title(original.getTitle())
                    .reward(original.getReward())
                    .helperId(999L)
                    .helpRegisterId(original.getHelpRegisterId())
                    .placeId(original.getPlaceId())
                    .start(original.getStart())
                    .end(original.getEnd())
                    .status(original.getStatus())
                    .photoPath(original.getPhotoPath().orElse(null))
                    .completed(original.isCompleted())
                    .build();
            assertNotEquals(original, diffHelperId);

            // helpRegisterId가 다른 경우
            CheckIn.DTO diffHelpRegisterId = CheckIn.DTO.builder()
                    .id(original.getId())
                    .title(original.getTitle())
                    .reward(original.getReward())
                    .helperId(original.getHelperId().orElse(null))
                    .helpRegisterId(999L)
                    .placeId(original.getPlaceId())
                    .start(original.getStart())
                    .end(original.getEnd())
                    .status(original.getStatus())
                    .photoPath(original.getPhotoPath().orElse(null))
                    .completed(original.isCompleted())
                    .build();
            assertNotEquals(original, diffHelpRegisterId);

            // placeId가 다른 경우
            CheckIn.DTO diffPlaceId = CheckIn.DTO.builder()
                    .id(original.getId())
                    .title(original.getTitle())
                    .reward(original.getReward())
                    .helperId(original.getHelperId().orElse(null))
                    .helpRegisterId(original.getHelpRegisterId())
                    .placeId("다른 장소 ID")
                    .start(original.getStart())
                    .end(original.getEnd())
                    .status(original.getStatus())
                    .photoPath(original.getPhotoPath().orElse(null))
                    .completed(original.isCompleted())
                    .build();
            assertNotEquals(original, diffPlaceId);

            // start가 다른 경우
            CheckIn.DTO diffStart = CheckIn.DTO.builder()
                    .id(original.getId())
                    .title(original.getTitle())
                    .reward(original.getReward())
                    .helperId(original.getHelperId().orElse(null))
                    .helpRegisterId(original.getHelpRegisterId())
                    .placeId(original.getPlaceId())
                    .start(LocalDateTime.of(2000, 1, 1, 1, 1))
                    .end(original.getEnd())
                    .status(original.getStatus())
                    .photoPath(original.getPhotoPath().orElse(null))
                    .completed(original.isCompleted())
                    .build();
            assertNotEquals(original, diffStart);

            // end가 다른 경우
            CheckIn.DTO diffEnd = CheckIn.DTO.builder()
                    .id(original.getId())
                    .title(original.getTitle())
                    .reward(original.getReward())
                    .helperId(original.getHelperId().orElse(null))
                    .helpRegisterId(original.getHelpRegisterId())
                    .placeId(original.getPlaceId())
                    .start(original.getStart())
                    .end(LocalDateTime.of(2000, 1, 1, 1, 1))
                    .status(original.getStatus())
                    .photoPath(original.getPhotoPath().orElse(null))
                    .completed(original.isCompleted())
                    .build();
            assertNotEquals(original, diffEnd);

            // status가 다른 경우
            CheckIn.DTO diffStatus = CheckIn.DTO.builder()
                    .id(original.getId())
                    .title(original.getTitle())
                    .reward(original.getReward())
                    .helperId(original.getHelperId().orElse(null))
                    .helpRegisterId(original.getHelpRegisterId())
                    .placeId(original.getPlaceId())
                    .start(original.getStart())
                    .end(original.getEnd())
                    .status(new Progress.Started()) // 다른 상태
                    .photoPath(original.getPhotoPath().orElse(null))
                    .completed(original.isCompleted())
                    .build();
            assertNotEquals(original, diffStatus);

            // photoPath가 다른 경우
            CheckIn.DTO diffPhotoPath = CheckIn.DTO.builder()
                    .id(original.getId())
                    .title(original.getTitle())
                    .reward(original.getReward())
                    .helperId(original.getHelperId().orElse(null))
                    .helpRegisterId(original.getHelpRegisterId())
                    .placeId(original.getPlaceId())
                    .start(original.getStart())
                    .end(original.getEnd())
                    .status(original.getStatus())
                    .photoPath("다른 사진 경로")
                    .completed(original.isCompleted())
                    .build();
            assertNotEquals(original, diffPhotoPath);

            // completed가 다른 경우
            CheckIn.DTO diffCompleted = CheckIn.DTO.builder()
                    .id(original.getId())
                    .title(original.getTitle())
                    .reward(original.getReward())
                    .helperId(original.getHelperId().orElse(null))
                    .helpRegisterId(original.getHelpRegisterId())
                    .placeId(original.getPlaceId())
                    .start(original.getStart())
                    .end(original.getEnd())
                    .status(original.getStatus())
                    .photoPath(original.getPhotoPath().orElse(null))
                    .completed(!original.isCompleted())
                    .build();
            assertNotEquals(original, diffCompleted);

        }
    }

    @Nested
    @DisplayName("생성자")
    class constructorTest {

        @Test
        @DisplayName("CheckIn 생성자 테스트 - 빌더를 통한 생성")
        void testCheckInConstructorViaBuilder() {
            // Given
            Long id = 1L;
            HelpDetail helpDetail = mock(HelpDetail.class);
            Progress progress = mock(Progress.class);

            // When
            CheckIn checkIn = CheckIn.builder()
                    .id(id)
                    .helpDetail(helpDetail)
                    .progress(progress)
                    .build();

            // Then
            assertEquals(id, checkIn.getId());
        }

    }

    @Nested
    @DisplayName("HashCode")
    class hashCode {
        @Test
        @DisplayName("HashCode - DTO를 비교한다")
        void hashequals() {
            //Given
            CheckIn.DTO sut = CheckInFixtures.CheckInT.createBasicDTO();
            CheckIn.DTO copy = CheckInFixtures.CheckInT.createBasicDTO();

            //When & Then
            assertEquals(sut.hashCode(), copy.hashCode());
        }

        @Test
        @DisplayName("HashCode - 값이 다르면 비교 실패.")
        void hashtest() {
            //Given
            CheckIn.DTO custome = CheckIn.DTO.builder()
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
            CheckIn.DTO sut = CheckInFixtures.CheckInT.createBasicDTO();

            //When & Then
            assertNotEquals(sut.hashCode(), custome.hashCode());
        }
    }

    @Test
    @DisplayName("CheckIn.DTO getDTO 메서드 테스트")
    void getDTO_Success() {
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
        CheckIn checkIn = CheckIn.builder()
                .id(1L)
                .helpDetail(helpDetail)
                .progress(progress)
                .build();

        // When
        CheckIn.DTO sut = CheckIn.DTO.getDTO(checkIn);
        // Then
        assertEquals(sut.getId(), checkIn.getId());
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
    @DisplayName("getDTO NPE")
    void getDTO_npe() {
        assertThrows(NullPointerException.class, () -> {
            CheckIn.DTO.getDTO(null);
        });
    }

    @Test
    @DisplayName("CheckIn.DTO getRegDTO 메서드 테스트")
    void getDTOForCreation_Success() {
        // Given
        CheckInService.Creation dto = CheckInFixtures.CheckInServiceT.CreationT.create();
        CheckIn checkIn = CheckIn.register(dto);

        // When
        CheckIn.DTO expectedDTO = CheckIn.DTO.getDTOForCreation(checkIn);

        // Then
        assertEquals(expectedDTO.getId(), checkIn.getId());
        assertEquals(expectedDTO.getHelpRegisterId(), dto.getHelpRegisterId());
        assertEquals(expectedDTO.getPlaceId(), dto.getPlaceId());
        assertEquals(expectedDTO.getStart(), dto.getStart());
        assertEquals(expectedDTO.getEnd(), dto.getEnd());
        assertEquals(expectedDTO.getReward(), dto.getReward());
        assertEquals(expectedDTO.getTitle(), CheckInService.CreationInitializer.createTitle(dto.getPlaceName()));
        assertThat(expectedDTO.getHelperId()).isEmpty();
        assertThat(expectedDTO.getPhotoPath()).isEmpty();
        assertEquals(expectedDTO.getStatus(), new Progress.Created());
        assertEquals(false, expectedDTO.isCompleted());
    }

    @Test
    @DisplayName("getDTO NPE")
    void getDTgetDTOForCreation_npe() {
        assertThrows(NullPointerException.class, () -> {
            CheckIn.DTO.getDTOForCreation(null);
        });
    }

    @Test
    @DisplayName("getDTO IllegalState")
    void getDTgetDTOForCreation_IllegalState() {
        //Given
        CheckIn checkIn = CheckInFixtures.CheckInT.create();

        //When & Then
        assertThrows(IllegalStateException.class, () -> {
            CheckIn.DTO.getDTOForCreation(checkIn);
        });
    }


}
