package com.company.checkin.place.infra.adapter.storage.cache.redis;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.IOException;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class PlaceRedisTest {

    @Test
    void testRegisterJsonNode() throws IOException {
        // given
        String json = "{"
                + "\"id\": 12345,"
                + "\"place_name\": \"스타벅스\","
                + "\"address_name\": \"서울시 강남구\","
                + "\"road_address_name\": \"서울시 강남구 테헤란로 123\","
                + "\"category_group_name\": \"카페\","
                + "\"phone\": \"02-1234-5678\","
                + "\"place_url\": \"http://place.map.kakao.com/12345\","
                + "\"x\": 127.0495556,"
                + "\"y\": 37.5046307"
                + "}";
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode document = objectMapper.readTree(json);

        // when
        PlaceRedis placeRedis = PlaceRedis.from(document);

        // then
        assertEquals(Type.API + "." + 12345, placeRedis.getId());
        assertEquals("스타벅스", placeRedis.getPlaceName());
        assertEquals("서울시 강남구", placeRedis.getAddress());
        assertEquals("서울시 강남구 테헤란로 123", placeRedis.getRoadAddress());
        assertEquals("카페", placeRedis.getCategoryName());
        assertEquals("02-1234-5678", placeRedis.getPhone());
        assertEquals("http://place.map.kakao.com/12345", placeRedis.getPlaceUrl());
        assertEquals(127.0495556, placeRedis.getX());
        assertEquals(37.5046307, placeRedis.getY());
    }

    @Test
    void register_DB() {

        PlaceRedis.PlaceRedisProjection placeRedisProjection = mock(PlaceRedis.PlaceRedisProjection.class);
        given(placeRedisProjection.getId()).willReturn(1L);
        given(placeRedisProjection.getPlaceName()).willReturn("placeName");
        given(placeRedisProjection.getAddress()).willReturn("address");
        given(placeRedisProjection.getRoadAddressName()).willReturn("roadAddress");
        given(placeRedisProjection.getCategoryName()).willReturn("categoryName");
        given(placeRedisProjection.getPhone()).willReturn("phone");
        given(placeRedisProjection.getPlaceUrl()).willReturn("placeUrl");
        given(placeRedisProjection.getX()).willReturn(8.0);
        given(placeRedisProjection.getY()).willReturn(9.0);

        List<PlaceRedis.PlaceRedisProjection> list = List.of(placeRedisProjection);

        //when
        List<PlaceRedis> placeRedisList = PlaceRedis.from(list);

        //then
        assertEquals(1, placeRedisList.size());
        assertEquals(Type.DB + "." + placeRedisProjection.getId(), placeRedisList.get(0).getId());
        assertEquals("placeName", placeRedisList.get(0).getPlaceName());
        assertEquals("address", placeRedisList.get(0).getAddress());
        assertEquals("roadAddress", placeRedisList.get(0).getRoadAddress());
        assertEquals("categoryName", placeRedisList.get(0).getCategoryName());
        assertEquals("phone", placeRedisList.get(0).getPhone());
        assertEquals("placeUrl", placeRedisList.get(0).getPlaceUrl());
        assertEquals(8.0, placeRedisList.get(0).getX());
        assertEquals(9.0, placeRedisList.get(0).getY());
    }

    @Nested
    @DisplayName("PlaceRedis 객체 생성 테스트")
    class ConstructorTest {
        static Stream<Arguments> provideNullFieldTestCases() {
            // 기본값 설정
            String id = "id";
            String placeName = "placeName";
            String address = "address";
            String roadAddress = "roadAddress";
            String categoryName = "categoryName";
            String phone = "phone";
            String placeUrl = "placeUrl";
            Double x = 8.0;
            Double y = 9.0;

            return Stream.of(
                    // 첫 번째 인자는 필드 이름(테스트 식별용)
                    Arguments.of("id", null, placeName, address, roadAddress, categoryName, phone, placeUrl, x, y),
                    Arguments.of("placeName", id, null, address, roadAddress, categoryName, phone, placeUrl, x, y),
                    Arguments.of("address", id, placeName, null, roadAddress, categoryName, phone, placeUrl, x, y),
                    Arguments.of("roadAddress", id, placeName, address, null, categoryName, phone, placeUrl, x, y),
                    Arguments.of("categoryName", id, placeName, address, roadAddress, null, phone, placeUrl, x, y),
                    Arguments.of("phone", id, placeName, address, roadAddress, categoryName, null, placeUrl, x, y),
                    Arguments.of("placeUrl", id, placeName, address, roadAddress, categoryName, phone, null, x, y),
                    Arguments.of("x", id, placeName, address, roadAddress, categoryName, phone, placeUrl, null, y),
                    Arguments.of("y", id, placeName, address, roadAddress, categoryName, phone, placeUrl, x, null)
            );
        }

        @ParameterizedTest
        @MethodSource("provideNullFieldTestCases")
        void shouldHandleNullFields(String fieldName, String id, String placeName, String address,
                                    String roadAddress, String categoryName, String phone,
                                    String placeUrl, Double x, Double y) {

            assertThrows(NullPointerException.class, () -> {
                PlaceRedis.builder()
                        .id(id)
                        .placeName(placeName)
                        .address(address)
                        .roadAddress(roadAddress)
                        .categoryName(categoryName)
                        .phone(phone)
                        .placeUrl(placeUrl)
                        .x(x)
                        .y(y)
                        .build();
            });
        }
    }

}