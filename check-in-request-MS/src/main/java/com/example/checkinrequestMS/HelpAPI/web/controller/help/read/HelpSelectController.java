package com.example.checkinrequestMS.HelpAPI.web.controller.help.read;

import com.example.checkinrequestMS.HelpAPI.application.service.help.read.HelpSelectService;

import com.example.checkinrequestMS.HelpAPI.domain.model.help.Help;
import com.example.checkinrequestMS.HelpAPI.domain.model.help.child.CheckIn;
import com.example.checkinrequestMS.HelpAPI.domain.model.help.child.Etc;
import com.example.checkinrequestMS.HelpAPI.domain.model.help.child.LineUp;
import com.example.checkinrequestMS.HelpAPI.web.controller.dto.response.progress.read.ProgressSelectResponse;
import lombok.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/help/")
@RequiredArgsConstructor
public class HelpSelectController {

    private final HelpSelectService helpSelectService;

    @GetMapping("/{id}")
    public ResponseEntity<HelpSelectResponse> selecHelp(@PathVariable Long id) {
        return ResponseEntity.ok(HelpSelectResponse.from(helpSelectService.selectHelp(id)));
    }

    //Response
    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @AllArgsConstructor(access = AccessLevel.PROTECTED)
    public static abstract class HelpSelectResponse {

        protected Long id;
        protected Long helpRegisterId;
        protected String title;
        protected String placeId;
        protected LocalDateTime start;
        protected LocalDateTime end;
        protected ProgressSelectResponse progressSelectResponse;
        protected Long reward;

        public static HelpSelectResponse from(Help help) {
            if (help instanceof CheckIn) {
                return CheckInSelectResponse.from((CheckIn) help);
            } else if (help instanceof LineUp) {
                return LineUpSelectResponse.from((LineUp) help);
            } else if (help instanceof Etc) {
                return EtcSelectResponse.from((Etc) help);
            }
            throw new IllegalArgumentException("Unknown help type");
        }
    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class CheckInSelectResponse extends HelpSelectResponse {

        @Builder(access = AccessLevel.PROTECTED)
        private CheckInSelectResponse(Long id, Long helpRegisterId, String title, String placeId, LocalDateTime start, LocalDateTime end, ProgressSelectResponse progressSelectResponse, Long reward) {
            super(id, helpRegisterId, title, placeId, start, end, progressSelectResponse, reward);
        }

        public static CheckInSelectResponse from(CheckIn checkIn) {
            return CheckInSelectResponse.builder()
                    .id(checkIn.getId())
                    .helpRegisterId(checkIn.getHelpRegisterId())
                    .title(checkIn.getTitle())
                    .placeId(checkIn.getPlaceId())
                    .start(checkIn.getStart())
                    .end(checkIn.getEnd())
                    .reward(checkIn.getReward())
                    .progressSelectResponse(ProgressSelectResponse.getProgressSelectResponse(checkIn.getProgress()))
                    .build();
        }
    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class EtcSelectResponse extends HelpSelectResponse {

        private String contents;


        @Builder(access = AccessLevel.PROTECTED)
        private EtcSelectResponse(Long id, Long helpRegisterId, String title, String placeId, LocalDateTime start, LocalDateTime end, ProgressSelectResponse progressSelectResponse, Long reward, String contents) {
            super(id, helpRegisterId, title, placeId, start, end, progressSelectResponse, reward);
            this.contents = contents;
        }

        public static EtcSelectResponse from(Etc etc) {
            return EtcSelectResponse.builder()
                    .id(etc.getId())
                    .title(etc.getTitle())
                    .helpRegisterId(etc.getHelpRegisterId())
                    .placeId(etc.getPlaceId())
                    .progressSelectResponse(ProgressSelectResponse.getProgressSelectResponse(etc.getProgress()))
                    .start(etc.getStart())
                    .end(etc.getEnd())
                    .reward(etc.getReward())
                    .contents(etc.getContents())
                    .build();
        }
    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class LineUpSelectResponse extends HelpSelectResponse {

        @Builder(access = AccessLevel.PROTECTED)
        private LineUpSelectResponse(Long id, Long helpRegisterId, String title, String placeId, LocalDateTime start, LocalDateTime end, ProgressSelectResponse progressSelectResponse, Long reward) {
            super(id, helpRegisterId, title, placeId, start, end, progressSelectResponse, reward);
        }

        public static LineUpSelectResponse from(LineUp lineUp) {
            return LineUpSelectResponse.builder()
                    .id(lineUp.getId())
                    .helpRegisterId(lineUp.getHelpRegisterId())
                    .title(lineUp.getTitle())
                    .placeId(lineUp.getPlaceId())
                    .start(lineUp.getStart())
                    .end(lineUp.getEnd())
                    .reward(lineUp.getReward())
                    .progressSelectResponse(ProgressSelectResponse.getProgressSelectResponse(lineUp.getProgress()))
                    .build();
        }
    }
}