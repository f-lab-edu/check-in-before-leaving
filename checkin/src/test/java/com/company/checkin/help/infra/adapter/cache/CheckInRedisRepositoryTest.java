package com.company.checkin.help.infra.adapter.cache;

import com.company.checkin.fixtures.checkin.help.CheckInFixtures;
import com.company.checkin.help.application.help.HelpSelectApplication;
import com.company.checkin.help.domain.model.help.checkin.CheckIn;
import com.company.checkin.help.domain.model.help.checkin.CheckInService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

class CheckInRedisRepositoryTest {

    private CheckInRedisRepository checkInRedisRepository;

    @Mock
    private CheckInService checkInService;

    @Mock
    private RedisTemplate<String, Object> redisTemplate;

    @Mock
    private ValueOperations<String, Object> valueOperations;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        when(redisTemplate.opsForValue()).thenReturn(valueOperations);
        checkInRedisRepository = new CheckInRedisRepository(checkInService, redisTemplate);
    }

    @Test
    @DisplayName("PER - 캐시 히트")
    void get_ProbabilityRecomputation_ValidTTL() {
        // Given
        long id = 1L;
        long secondsLeft = 5000L;
        String cacheKey = "checkIn_" + id;
        CheckIn checkIn = CheckInFixtures.CheckInT.create();
        CheckIn.DTO response = CheckIn.DTO.getDTO(checkIn);
        HelpSelectApplication.CheckInSelectDTO finalResponse = HelpSelectApplication.CheckInSelectDTO.from(response);
        when(valueOperations.get(cacheKey)).thenReturn(finalResponse);
        when(redisTemplate.getExpire(cacheKey, TimeUnit.SECONDS)).thenReturn(secondsLeft);


        // When
        HelpSelectApplication.CheckInSelectDTO result = checkInRedisRepository.get(id);

        // Then
        assertNotNull(result);
        assertEquals(finalResponse.getId(), result.getId());
        assertEquals(finalResponse.getHelpRegisterId(), result.getHelpRegisterId());
        assertEquals(finalResponse.getTitle(), result.getTitle());
        assertEquals(finalResponse.getPlaceId(), result.getPlaceId());
        assertEquals(finalResponse.getStart(), result.getStart());
        assertEquals(finalResponse.getEnd(), result.getEnd());
        assertEquals(finalResponse.getReward(), result.getReward());
        assertEquals(finalResponse.getHelperId(), result.getHelperId());
        assertEquals(finalResponse.getPhotoPath(), result.getPhotoPath());
        assertEquals(finalResponse.isCompleted(), result.isCompleted());
    }

    @Test
    @DisplayName("PER - 캐시 미스, 갱신")
    void get_ProbabilityRecomputation_ExpiredTTL() {
        // Given
        long id = 1L;
        long secondsLeft = 0L;
        String cacheKey = "checkIn_" + id;

        when(redisTemplate.getExpire(cacheKey, TimeUnit.SECONDS)).thenReturn(secondsLeft);
        CheckIn checkIn = CheckInFixtures.CheckInT.create();
        CheckIn.DTO response = CheckIn.DTO.getDTO(checkIn);
        when(checkInService.findOne(id)).thenReturn(response);

        // When
        HelpSelectApplication.CheckInSelectDTO result = checkInRedisRepository.get(id);

        // Then
        HelpSelectApplication.CheckInSelectDTO responseResult = HelpSelectApplication.CheckInSelectDTO.from(response);
        assertNotNull(result);
        assertEquals(responseResult.getId(), result.getId());
        assertEquals(responseResult.getHelpRegisterId(), result.getHelpRegisterId());
        assertEquals(responseResult.getTitle(), result.getTitle());
        assertEquals(responseResult.getPlaceId(), result.getPlaceId());
        assertEquals(responseResult.getStart(), result.getStart());
        assertEquals(responseResult.getEnd(), result.getEnd());
        assertEquals(responseResult.getReward(), result.getReward());
        assertEquals(responseResult.getHelperId(), result.getHelperId());
        assertEquals(responseResult.getPhotoPath(), result.getPhotoPath());
        assertEquals(responseResult.isCompleted(), result.isCompleted());

    }
}