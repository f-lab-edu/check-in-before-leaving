package com.company.checkin.place.infra.adapter.rest.kakao;

import com.company.checkin.place.domain.exceptions.place.PlaceException;
import com.company.checkin.place.infra.adapter.storage.cache.redis.PlaceRedis;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class KakaoClientTest {

    @Mock
    private KakaoStoreAPI kakaoAPI;

    @InjectMocks
    private KakaoClient kakaoClient;

    @Test
    @DisplayName("JSON 처리 오류 발생 시 예외 처리")
    void toPlaceRedis_jsonProcessingError() {
        // Given
        String invalidJson = "invalid json";
        when(kakaoAPI.getStoreInfo(any(), any(), anyDouble(), anyDouble()))

                .thenReturn(invalidJson);

        // When & Then
        assertThrows(PlaceException.class, () -> {
            kakaoClient.searchwithkeyword("카페", 127.1086228, 37.4012191);
        });
    }

    @Test
    @DisplayName("빈 결과 처리")
    void toPlaceRedis_emptyResult() {
        // Given
        String emptyResponse = "[]";
        when(kakaoAPI.getStoreInfo(any(), any(), anyDouble(), anyDouble()))
                .thenReturn(emptyResponse);

        // When
        List<PlaceRedis> result = kakaoClient.searchwithkeyword("존재하지않는장소", 127.1086228, 37.4012191);

        // Then
        assertTrue(result.isEmpty());
    }
}
