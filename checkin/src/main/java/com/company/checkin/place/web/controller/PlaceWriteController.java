package com.company.checkin.place.web.controller;


import com.company.checkin.place.domain.model.place.place.Place;
import com.company.checkin.place.domain.model.place.place.PlaceService;
import com.company.checkin.place.web.controller.place.dto.PlaceRegisterForm;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/place")
@RequiredArgsConstructor
public class PlaceWriteController {

    private final PlaceService placeService;

    private static final String PLACE_REGISTERED = "장소가 등록 되었습니다.";

    @PostMapping
    public ResponseEntity<String> registerPlace(@Validated @RequestBody PlaceRegisterForm form) {
        placeService.register(Place.register(form));
        return ResponseEntity.ok(PLACE_REGISTERED);
    }
}
