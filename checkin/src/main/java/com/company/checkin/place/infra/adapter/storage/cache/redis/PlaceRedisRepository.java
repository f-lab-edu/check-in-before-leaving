package com.company.checkin.place.infra.adapter.storage.cache.redis;

import org.springframework.data.repository.CrudRepository;

public interface PlaceRedisRepository extends CrudRepository<PlaceRedis, String> {

}
