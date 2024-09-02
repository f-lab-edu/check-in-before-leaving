package com.example.checkinrequestMS.PlaceAPI.web.controller;


import com.example.checkinrequestMS.PlaceAPI.domain.Place;
import com.example.checkinrequestMS.PlaceAPI.domain.service.PlaceWriteService;
import com.example.checkinrequestMS.PlaceAPI.web.form.PlaceRegisterForm;
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

    private final PlaceWriteService placeCRUDService;

    private static final String PLACE_REGISTERED = "장소가 등록 되었습니다.";
    @PostMapping
    public ResponseEntity<String> registerPlace(@Validated @RequestBody PlaceRegisterForm form){
        placeCRUDService.registerPlace(Place.from(form));
        return ResponseEntity.ok(PLACE_REGISTERED);
    }
}
