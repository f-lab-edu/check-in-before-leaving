package com.example.checkinrequestMS.HelpAPI.web.controller.help.lineUp;

import com.example.checkinrequestMS.HelpAPI.domain.service.LineUp.LineUpSelectService;
import com.example.checkinrequestMS.HelpAPI.web.dto.help.lineUp.LineUpDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/help/lineUp")
@RequiredArgsConstructor
public class LineUpSelectController {

    private final LineUpSelectService lineUpSelectService;

    @GetMapping("/{id}")
    public ResponseEntity<LineUpDTO> selectLineUp(@PathVariable Long id) {
        return ResponseEntity.ok(LineUpDTO.from(lineUpSelectService.selectLineUp(id)));
    }
}
