package com.example.checkinrequestMS.PlaceAPI.infra.redis;

import com.example.checkinrequestMS.PlaceAPI.domain.PlaceRedis;
import org.springframework.data.repository.CrudRepository;

public interface PlaceRedisRepository extends CrudRepository<PlaceRedis, String> {


}
