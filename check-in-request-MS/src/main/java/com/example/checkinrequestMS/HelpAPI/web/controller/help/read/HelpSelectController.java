package com.example.checkinrequestMS.HelpAPI.web.controller.help.read;

import com.example.checkinrequestMS.HelpAPI.application.exceptions.help.HelpException;
import com.example.checkinrequestMS.HelpAPI.application.service.help.read.HelpSelectService;

import com.example.checkinrequestMS.HelpAPI.domain.model.help.Help;
import com.example.checkinrequestMS.HelpAPI.domain.model.help.Progress;
import com.example.checkinrequestMS.HelpAPI.domain.model.help.child.CheckIn;
import com.example.checkinrequestMS.HelpAPI.domain.model.help.child.Etc;
import com.example.checkinrequestMS.HelpAPI.domain.model.help.child.LineUp;
import com.example.checkinrequestMS.HelpAPI.web.controller.progress.wrtie.ProgressWriteController;
import lombok.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

import static com.example.checkinrequestMS.HelpAPI.application.exceptions.help.HelpErrorCode.HELP_RESPONSE_CREATION_ERROR;

@RestController
@RequestMapping("/help/")
@RequiredArgsConstructor
public class HelpSelectController {

    private final HelpSelectService helpSelectService;

    @GetMapping("/{id}")
    public ResponseEntity<HelpSelectResponse> selectHelp(@PathVariable Long id) {
        return ResponseEntity.ok(HelpSelectResponseFactory.from(helpSelectService.selectHelp(id)));
    }

    // Factory
    public static class HelpSelectResponseFactory {
        static final List<HelpSelectResponse> responses = List.of(
                new CheckInSelectResponse(),
                new EtcSelectResponse(),
                new LineUpSelectResponse()
        );

        public static HelpSelectResponse from(Help help) {
            return responses.stream()
                    .filter(response -> response.canHandle(help))
                    .findFirst()
                    .map(response -> response.createResponse(help))
                    .orElseThrow(() -> new HelpException(HELP_RESPONSE_CREATION_ERROR));
        }
    }

    //Responses
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
        protected ProgressWriteController.ProgressSelectResponse progressSelectResponse;
        protected Long reward;

        public abstract boolean canHandle(Help help);

        public abstract HelpSelectResponse createResponse(Help help);
    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class CheckInSelectResponse extends HelpSelectResponse {

        @Builder(access = AccessLevel.PRIVATE)
        private CheckInSelectResponse(Long id, Long helpRegisterId, String title, String placeId, LocalDateTime start, LocalDateTime end, ProgressWriteController.ProgressSelectResponse progressSelectResponse, Long reward) {
            super(id, helpRegisterId, title, placeId, start, end, progressSelectResponse, reward);
        }

        @Override
        public boolean canHandle(Help help) {
            return help instanceof CheckIn;
        }

        @Override
        public CheckInSelectResponse createResponse(Help help) {
            CheckIn checkIn = (CheckIn) help;
            return CheckInSelectResponse.builder()
                    .id(checkIn.getId())
                    .helpRegisterId(checkIn.getHelpRegisterId())
                    .title(checkIn.getTitle())
                    .placeId(checkIn.getPlaceId())
                    .start(checkIn.getStart())
                    .end(checkIn.getEnd())
                    .reward(checkIn.getReward())
                    .progressSelectResponse(ProgressWriteController.ProgressSelectResponse.getProgressSelectResponse(checkIn.getProgress()))
                    .build();
        }

        //Test
        public static CheckInSelectResponse createForTest() {
            return CheckInSelectResponse.builder()
                    .id(1L)
                    .helpRegisterId(1L)
                    .title("title")
                    .placeId("placeId")
                    .start(LocalDateTime.now())
                    .end(LocalDateTime.now())
                    .progressSelectResponse(ProgressWriteController.ProgressSelectResponse.getProgressSelectResponse(Progress.DEFAULT))
                    .reward(100L)
                    .build();
        }
    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class EtcSelectResponse extends HelpSelectResponse {

        private String contents;

        @Builder(access = AccessLevel.PRIVATE)
        private EtcSelectResponse(Long id, Long helpRegisterId, String title, String placeId, LocalDateTime start, LocalDateTime end, ProgressWriteController.ProgressSelectResponse progressSelectResponse, Long reward, String contents) {
            super(id, helpRegisterId, title, placeId, start, end, progressSelectResponse, reward);
            this.contents = contents;
        }

        @Override
        public boolean canHandle(Help help) {
            return help instanceof Etc;
        }

        @Override
        public EtcSelectResponse createResponse(Help help) {
            Etc etc = (Etc) help;
            return EtcSelectResponse.builder()
                    .id(etc.getId())
                    .helpRegisterId(etc.getHelpRegisterId())
                    .title(etc.getTitle())
                    .placeId(etc.getPlaceId())
                    .start(etc.getStart())
                    .end(etc.getEnd())
                    .reward(etc.getReward())
                    .progressSelectResponse(ProgressWriteController.ProgressSelectResponse.getProgressSelectResponse(etc.getProgress()))
                    .contents(etc.getContents())
                    .build();
        }

        //Test
        public static EtcSelectResponse createForTest() {
            return EtcSelectResponse.builder()
                    .id(1L)
                    .helpRegisterId(1L)
                    .title("title")
                    .placeId("placeId")
                    .start(LocalDateTime.now())
                    .end(LocalDateTime.now())
                    .progressSelectResponse(ProgressWriteController.ProgressSelectResponse.getProgressSelectResponse(Progress.DEFAULT))
                    .reward(100L)
                    .contents("contents")
                    .build();
        }
    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class LineUpSelectResponse extends HelpSelectResponse {

        @Builder(access = AccessLevel.PRIVATE)
        private LineUpSelectResponse(Long id, Long helpRegisterId, String title, String placeId, LocalDateTime start, LocalDateTime end, ProgressWriteController.ProgressSelectResponse progressSelectResponse, Long reward) {
            super(id, helpRegisterId, title, placeId, start, end, progressSelectResponse, reward);
        }

        @Override
        public boolean canHandle(Help help) {
            return help instanceof LineUp;
        }

        @Override
        public LineUpSelectResponse createResponse(Help help) {
            return LineUpSelectResponse.builder()
                    .id(help.getId())
                    .helpRegisterId(help.getHelpRegisterId())
                    .title(help.getTitle())
                    .placeId(help.getPlaceId())
                    .start(help.getStart())
                    .end(help.getEnd())
                    .reward(help.getReward())
                    .progressSelectResponse(ProgressWriteController.ProgressSelectResponse.getProgressSelectResponse(help.getProgress()))
                    .build();
        }

        //Test
        public static LineUpSelectResponse createForTest() {
            return LineUpSelectResponse.builder()
                    .id(1L)
                    .helpRegisterId(1L)
                    .title("title")
                    .placeId("placeId")
                    .start(LocalDateTime.now())
                    .end(LocalDateTime.now())
                    .progressSelectResponse(ProgressWriteController.ProgressSelectResponse.getProgressSelectResponse(Progress.DEFAULT))
                    .reward(100L)
                    .build();
        }
    }

}