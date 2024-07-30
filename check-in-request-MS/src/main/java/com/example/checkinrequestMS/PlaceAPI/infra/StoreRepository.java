package com.example.checkinrequestMS.PlaceAPI.infra;

import com.example.checkinrequestMS.PlaceAPI.domain.Place;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StoreRepository extends JpaRepository<Place, Long> {
}
