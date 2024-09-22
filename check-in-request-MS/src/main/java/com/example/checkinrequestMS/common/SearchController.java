package com.example.checkinrequestMS.common;

import com.example.checkinrequestMS.HelpAPI.application.service.help.read.HelpSelectService;
import com.example.checkinrequestMS.HelpAPI.domain.model.help.Help;
import com.example.checkinrequestMS.HelpAPI.domain.model.help.ProgressVO.Progress;
import com.example.checkinrequestMS.HelpAPI.infra.db.entity.ProgressValue;
import com.example.checkinrequestMS.PlaceAPI.domain.PlaceRedis;
import com.example.checkinrequestMS.PlaceAPI.domain.service.PlaceSearchService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class SearchController {

    private final PlaceSearchService searchPlaceService;

    private final HelpSelectService helpSelectService;

    @GetMapping("/search")
    public ResponseEntity<List<PlaceSearchDTO>> searchByKeyword(@RequestParam("keyword") String keyword,
                                                                @RequestParam("x") double x,
                                                                @RequestParam("y") double y,
                                                                @RequestParam("radius") int radius) {
        //Place 찾기
        List<PlaceRedis> list = searchPlaceService.searchPlaceWithKeyword(keyword, x, y, radius);
        List<String> placeIds = list.stream().map(PlaceRedis::getId).toList();

        //Help 찾기
        List<Help> helps = helpSelectService.selectAllHelp(placeIds);

        return ResponseEntity.ok().body(
                list.stream().map(placeRedis -> {
                    List<HelpSearchDTO> helpSearchDTOList = helps.stream().filter(help -> help.getPlaceId().equals(placeRedis.getId())).map(HelpSearchDTO::new).toList();
                    return new PlaceSearchDTO(placeRedis, helpSearchDTOList);
                }).toList());
    }

    @Getter
    class PlaceSearchDTO {
        private String id;
        private String placeName;
        private String address;
        private String roadAddressName;
        private String category;
        private String phone;
        private String placeUrl;
        private List<HelpSearchDTO> helpSearchDTO;
        private double x;
        private double y;

        private PlaceSearchDTO(PlaceRedis placeRedis, List<HelpSearchDTO> helpSearchDTO) {
            this.id = placeRedis.getId();
            this.placeName = placeRedis.getPlaceName();
            this.address = placeRedis.getAddress();
            this.roadAddressName = placeRedis.getRoadAddress();
            this.category = placeRedis.getCategoryName();
            this.phone = placeRedis.getPhone();
            this.placeUrl = placeRedis.getPlaceUrl();
            this.helpSearchDTO = helpSearchDTO;
            this.x = placeRedis.getX();
            this.y = placeRedis.getY();
        }

        public String toString() {
            return "PlaceSearchDTO{" +
                    "id='" + id + '\'' +
                    ", placeName='" + placeName + '\'' +
                    ", address='" + address + '\'' +
                    ", roadAddressName='" + roadAddressName + '\'' +
                    ", category='" + category + '\'' +
                    ", phone='" + phone + '\'' +
                    ", placeUrl='" + placeUrl + '\'' +
                    ", helpSearchDTO=" + helpSearchDTO.toString() +
                    ", x=" + x +
                    ", y=" + y +
                    '}';
        }

    }

    @Getter
    class HelpSearchDTO {
        private Long id;
        private Long helpRegisterId;
        private String title;
        private LocalDateTime start;
        private LocalDateTime end;
        private Long reward;
        private String progress;

        private HelpSearchDTO(Help help) {
            this.id = help.getId();
            this.helpRegisterId = help.getHelpRegisterId();
            this.title = help.getTitle();
            this.start = help.getStart();
            this.end = help.getEnd();
            this.reward = help.getReward();
            this.progress = help.getProgress().getClass().getSimpleName();
        }

        @Override
        public String toString() {
            return "HelpSearchDTO{" +
                    "id=" + id +
                    ", helpRegisterId=" + helpRegisterId +
                    ", title='" + title + '\'' +
                    ", start=" + start +
                    ", end=" + end +
                    ", reward=" + reward +
                    ", progress=" + progress +
                    '}';
        }
    }
}
