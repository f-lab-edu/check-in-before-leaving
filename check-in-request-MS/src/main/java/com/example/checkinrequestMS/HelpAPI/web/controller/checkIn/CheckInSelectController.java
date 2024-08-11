package com.example.checkinrequestMS.HelpAPI.web.controller.checkIn;

import com.example.checkinrequestMS.HelpAPI.domain.service.checkIn.CheckInSelectService;
import com.example.checkinrequestMS.HelpAPI.web.dto.checkIn.CheckInDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/help/checkin")
@RequiredArgsConstructor
public class CheckInSelectController {

    private final CheckInSelectService checkInSelectService;

    @GetMapping("/{id}")
    public ResponseEntity<CheckInDTO> selectCheckIn(@PathVariable Long id) {
        return ResponseEntity.ok(CheckInDTO.from(checkInSelectService.selectCheckIn(id)));
    }

}
