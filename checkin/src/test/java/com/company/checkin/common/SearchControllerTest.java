//package com.example.checkinrequestMS.common;
//
//import com.example.checkinrequestMS.HelpAPI.application.service.help.read.HelpSelectService;
//import com.example.checkinrequestMS.HelpAPI.domain.model.help.Progress;
//import com.example.checkinrequestMS.HelpAPI.domain.model.help.child.CheckIn;
//import com.example.checkinrequestMS.PlaceAPI.domain.PlaceRedis;
//import com.example.checkinrequestMS.PlaceAPI.domain.service.PlaceSearchService;
//import com.example.checkinrequestMS.fixtures.HelpAPI.domain.model.help.child.CheckInFixture;
//import com.example.checkinrequestMS.fixtures.PlaceAPI.domain.PlaceRedisFixture;
//
//import org.junit.jupiter.api.Test;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.http.MediaType;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.ResultActions;
//
//import java.util.List;
//
//import static org.mockito.BDDMockito.given;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//@WebMvcTest(SearchController.class)
//class SearchControllerTest {
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @MockBean
//    private PlaceSearchService searchPlaceService;
//
//    @MockBean
//    private HelpSelectService helpSelectService;
//
//
//    @Test
//    void searchWithKeyword() throws Exception {
//        //given
//        String keyword = "맛집";
//        double x = 126.98561429978552;
//        double y = 37.56255453417897;
//        int radius = 50;
//
//        PlaceRedis redis1 = PlaceRedisFixture.createPlaceRedisFromAPIWithId(1L);
//        CheckIn checkIn1 = CheckInFixture.createCheckInWithIdAndPlaceId(Progress.DEFAULT, 1L, redis1.getId());
//        given(searchPlaceService.searchPlaceWithKeyword(keyword, x, y, radius)).willReturn(List.of(redis1));
//        given(helpSelectService.selectAllHelp(List.of(redis1.getId()))).willReturn(List.of(checkIn1));
//
//        //when
//        ResultActions result = mockMvc.perform(get("/search")
//                .param("keyword", keyword)
//                .param("x", String.valueOf(x))
//                .param("y", String.valueOf(y))
//                .param("radius", String.valueOf(radius))
//                .contentType(MediaType.APPLICATION_JSON));
//
//        result.andExpect(status().isOk())
//                .andExpect(jsonPath("$[0].id").value(redis1.getId()))
//                .andExpect(jsonPath("$[0].placeName").value(redis1.getPlaceName()))
//                .andExpect(jsonPath("$[0].address").value(redis1.getAddress()))
//                .andExpect(jsonPath("$[0].roadAddressName").value(redis1.getRoadAddress()))
//                .andExpect(jsonPath("$[0].category").value(redis1.getCategoryName()))
//                .andExpect(jsonPath("$[0].phone").value(redis1.getPhone()))
//                .andExpect(jsonPath("$[0].placeUrl").value(redis1.getPlaceUrl()))
//                .andExpect(jsonPath("$[0].x").value(redis1.getX()))
//                .andExpect(jsonPath("$[0].y").value(redis1.getY()))
//                .andExpect(jsonPath("$[0].helpSearchDTO[0].id").value(checkIn1.getId()))
//                .andExpect(jsonPath("$[0].helpSearchDTO[0].title").value(checkIn1.getTitle()))
//                .andExpect(jsonPath("$[0].helpSearchDTO[0].start").value(checkIn1.getStart().toString()))
//                .andExpect(jsonPath("$[0].helpSearchDTO[0].end").value(checkIn1.getEnd().toString()))
//                .andExpect(jsonPath("$[0].helpSearchDTO[0].reward").value(checkIn1.getReward()))
//                .andExpect(jsonPath("$[0].helpSearchDTO[0].progress").value(checkIn1.getProgress().getClass().getSimpleName()));
//    }
//
//}