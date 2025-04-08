package com.company.checkin.help.infra.adapter.cache;

import com.company.checkin.help.application.help.HelpSelectApplication;
import com.company.checkin.help.domain.model.help.checkin.CheckInService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class CheckInRedisRepository {

    private final CheckInService checkInService;
    private final RedisTemplate<String, Object> redisTemplate;

    private final String CACHE_KEY_STRUCTURE = "checkIn_";
    private final long TIME_TO_LIVE = 10;
    private final TimeUnit TIME_TO_LIVE_TIME_UNIT = TimeUnit.MINUTES;

    public HelpSelectApplication.CheckInSelectDTO get(Long id) {
        return getUsingProbabilityEarlyRecomputation(id);
    }

    private HelpSelectApplication.CheckInSelectDTO getUsingLookAside(Long id) {
        String cacheKey = CACHE_KEY_STRUCTURE + id;

        HelpSelectApplication.CheckInSelectDTO cachedData = (HelpSelectApplication.CheckInSelectDTO) redisTemplate.opsForValue().get(cacheKey);
        if (cachedData != null) {
            return cachedData;
        }

        HelpSelectApplication.CheckInSelectDTO result = HelpSelectApplication.CheckInSelectDTO.from(checkInService.findOne(id));
        redisTemplate.opsForValue().set(cacheKey, result, TIME_TO_LIVE, TIME_TO_LIVE_TIME_UNIT);
        return result;
    }

    private final long CACHE_EXPIRE_AT = 0;
    private final double RECOMPUTATION_PROBABILITY = 30; // 30초 전에는 갱신될 가능성 높다.
    private final Random RANDOM = new Random();

    private HelpSelectApplication.CheckInSelectDTO getUsingProbabilityEarlyRecomputation(Long id) {
        String cacheKey = CACHE_KEY_STRUCTURE + id;

        Long ttlLeft = redisTemplate.getExpire(cacheKey, TimeUnit.SECONDS);
        if (ttlLeft - RANDOM.nextDouble() * RECOMPUTATION_PROBABILITY < CACHE_EXPIRE_AT) {
            HelpSelectApplication.CheckInSelectDTO result = HelpSelectApplication.CheckInSelectDTO.from(checkInService.findOne(id));
            redisTemplate.opsForValue().set(cacheKey, result, TIME_TO_LIVE, TIME_TO_LIVE_TIME_UNIT);
            return result;
        }

        return (HelpSelectApplication.CheckInSelectDTO) redisTemplate.opsForValue().get(cacheKey);
    }
}
