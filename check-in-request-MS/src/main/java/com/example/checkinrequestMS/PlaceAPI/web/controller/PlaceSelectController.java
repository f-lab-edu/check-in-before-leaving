package com.example.checkinrequestMS.PlaceAPI.web.controller;

import com.example.checkinrequestMS.PlaceAPI.domain.service.PlaceSelectService;
import com.example.checkinrequestMS.PlaceAPI.web.dto.PlaceDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/place")
@RequiredArgsConstructor
public class PlaceSelectController {

    private final PlaceSelectService placeSelectService;

    @GetMapping("/{name}")
    public ResponseEntity<PlaceDTO> selectPlaceDetail(@PathVariable String name) {
        return ResponseEntity.ok(PlaceDTO.from(placeSelectService.selectPlaceDetail(name)));
    }
}
