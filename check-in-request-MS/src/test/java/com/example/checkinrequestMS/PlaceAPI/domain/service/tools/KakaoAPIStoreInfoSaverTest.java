package com.example.checkinrequestMS.PlaceAPI.domain.service.tools;

import com.example.checkinrequestMS.PlaceAPI.domain.Place;
import com.example.checkinrequestMS.PlaceAPI.infra.PlaceJPARepository;
import com.example.checkinrequestMS.PlaceAPI.web.restAPI.KakaoStoreAPIRequest;
import com.example.checkinrequestMS.PlaceAPI.web.restAPI.SearchType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class KakaoAPIStoreInfoSaverTest {

    @InjectMocks
    KakaoAPIStoreInfoSaver sut;

    @Mock
    private PlaceJPARepository storeRepository;

    @Mock
    private KakaoStoreAPIRequest kakaoAPIRequest;

    private String response;

    @BeforeEach
    void setUp() {
        response =
                "[{\"address_name\":\"서울 중구 명동2가 25-36\",\"category_group_code\":\"FD6\",\"category_group_name\":\"음식점\",\"category_name\":\"음식점 > 분식\",\"distance\":\"0\",\"id\":\"10332413\",\"phone\":\"02-776-5348\",\"place_name\":\"명동교자 본점\",\"place_url\":\"http://place.map.kakao.com/10332413\",\"road_address_name\":\"서울 중구 명동10길 29\",\"x\":\"126.98561429978552\",\"y\":\"37.56255453417897\"}," +
                 "{\"address_name\":\"서울 중구 명동2가 4-2\",\"category_group_code\":\"FD6\",\"category_group_name\":\"음식점\",\"category_name\":\"음식점 > 한식\",\"distance\":\"42\",\"id\":\"26853115\",\"phone\":\"02-3789-9292\",\"place_name\":\"순남시래기 명동직영점\",\"place_url\":\"http://place.map.kakao.com/26853115\",\"road_address_name\":\"서울 중구 명동10길 35-20\",\"x\":\"126.985784007806\",\"y\":\"37.5629131513515\"}]";
    }

    @Test
    @DisplayName("API에서 받은 정보 모두 저장")
    void add_all_from_API() {
        //given
        List<Place> placesFromDB = mock();

        given(kakaoAPIRequest.getStoreInfo(any(SearchType.class), anyString(), anyDouble(), anyDouble(), anyInt())).willReturn(response);
        given(storeRepository.getStoresByNameAndRadius(anyDouble(), anyDouble(), anyInt())).willReturn(Optional.of(placesFromDB));

        //when
        sut.balanceKeyWordSearch("맛집", 126.98561429978552, 37.56255453417897, 50);

        //then
        verify(storeRepository, times(2)).save(any(Place.class));
    }
    @Test
    @DisplayName("API에서 받은 정보 중 1개 없어서 저장")
    void add_only_one_from_API() {
        //given
        Place place1 = mock();
        given(place1.getPlaceName()).willReturn("명동교자 본점");
        List<Place> placesFromDB = spy(Arrays.asList(place1));

        given(kakaoAPIRequest.getStoreInfo(any(SearchType.class), anyString(), anyDouble(), anyDouble(), anyInt())).willReturn(response);
        given(storeRepository.getStoresByNameAndRadius(anyDouble(), anyDouble(), anyInt())).willReturn(Optional.of(placesFromDB));

        //when
        sut.balanceKeyWordSearch("맛집", 126.98561429978552, 37.56255453417897, 50);

        //then
        verify(storeRepository, times(1)).save(any(Place.class));
    }
    @Test
    @DisplayName("API에서 받은 정보가 DB에 이미 있는 경우 저장하지 않음")
    void add_none_from_API(){
        //given
        Place place1 = mock();
        given(place1.getPlaceName()).willReturn("명동교자 본점");
        Place place2 = mock();
        given(place2.getPlaceName()).willReturn("순남시래기 명동직영점");
        List<Place> placesFromDB = spy(Arrays.asList(place1, place2));

        given(kakaoAPIRequest.getStoreInfo(any(SearchType.class), anyString(), anyDouble(), anyDouble(), anyInt())).willReturn(response);
        given(storeRepository.getStoresByNameAndRadius(anyDouble(), anyDouble(), anyInt())).willReturn(Optional.of(placesFromDB));

        //when
        sut.balanceKeyWordSearch("맛집", 126.98561429978552, 37.56255453417897, 50);

        //then
        verify(storeRepository, times(0)).save(any(Place.class));
    }
}