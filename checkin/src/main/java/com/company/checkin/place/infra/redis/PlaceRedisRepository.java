package com.company.checkin.place.infra.redis;

import com.company.checkin.place.domain.PlaceRedis;
import org.springframework.data.repository.CrudRepository;

public interface PlaceRedisRepository extends CrudRepository<PlaceRedis, String> {


}
