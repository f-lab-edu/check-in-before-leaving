package com.example.checkinrequestMS.PlaceAPI.infra;

import com.example.checkinrequestMS.PlaceAPI.domain.Place;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface PlaceRepository extends JpaRepository<Place, Long> {
    @Query(value = "SELECT * FROM place p WHERE (POWER(:x - p.x, 2) + POWER(:y - p.y, 2)) <= POWER(:radius/POWER(10,5), 2)", nativeQuery = true)
    Optional<List<Place>> getStoresByNameAndRadius(double x, double y, int radius);
}//이름은 아직 미구현.
