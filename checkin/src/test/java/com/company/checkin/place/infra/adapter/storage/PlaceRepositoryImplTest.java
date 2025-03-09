package com.company.checkin.place.infra.adapter.storage;

import com.company.checkin.fixtures.place.domain.PlaceRedisFixture;
import com.company.checkin.place.infra.adapter.rest.kakao.KakaoClient;
import com.company.checkin.place.infra.adapter.rest.kakao.SearchCategory;
import com.company.checkin.place.infra.adapter.storage.cache.redis.PlaceRedis;
import com.company.checkin.place.infra.adapter.storage.cache.redis.PlaceRedisRepository;
import com.company.checkin.place.infra.adapter.storage.db.PlaceJPARepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionException;
import org.springframework.transaction.TransactionStatus;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PlaceRepositoryImplTest {

    @InjectMocks
    private PlaceRepositoryImpl sut;

    @Mock
    private PlaceJPARepository placeJPARepository;

    @Mock
    private KakaoClient kakaoClient;

    @Spy
    private PlatformTransactionManager transactionManager;

    @Mock
    private PlaceRedisRepository placeRedisRepository;

    @Test
    @DisplayName("키워드로 검색")
    void searchPlaceWithKeyword() {
        //given
        String keyword = "test";
        double x = 0.0;
        double y = 0.0;
        int radius = 0;

        PlaceRedis placeRedisForRedis = PlaceRedisFixture.createPlaceRedisFromAPIWithId(1L);
        given(kakaoClient.searchwithkeyword(keyword, x, y)).willReturn(List.of(placeRedisForRedis));

        PlaceRedis placeRedisForDB = PlaceRedisFixture.createPlaceRedisFromDBWithId(1L);
        given(placeJPARepository.findAllWithKeywordANDRadius(keyword, x, y, radius)).willReturn(List.of(placeRedisForDB));

        //when
        List<PlaceRedis> list = sut.searchWithKeyword(keyword, x, y, radius);

        //then
        assertEquals(2, list.size());
        assertEquals("API.1", list.get(0).getId());
        assertEquals("DB.1", list.get(1).getId());
    }

    @Test
    @DisplayName("카테고리로 검색")
    void searchWithCategory() {
        //given
        SearchCategory category = SearchCategory.FD6;
        double x = 0.0;
        double y = 0.0;
        int radius = 0;

        PlaceRedis placeRedisForRedis = PlaceRedisFixture.createPlaceRedisFromAPIWithId(1L);
        given(kakaoClient.searchWithCategory(category, x, y)).willReturn(List.of(placeRedisForRedis));

        PlaceRedis placeRedisForDB = PlaceRedisFixture.createPlaceRedisFromDBWithId(1L);
        given(placeJPARepository.findAllWithCategoryANDRadius(category, x, y, radius)).willReturn(List.of(placeRedisForDB));

        //when
        List<PlaceRedis> list = sut.searchWithCategory(category, x, y, radius);

        //then
        assertEquals(2, list.size());
        assertEquals("API.1", list.get(0).getId());
        assertEquals("DB.1", list.get(1).getId());
    }

    @Nested
    @DisplayName("키워드 - 트랜잭션 테스트")
    class KeywordTransactionTest {
        @Mock
        private TransactionStatus transactionStatus;

        String keyword = "test";
        double x = 0.0;
        double y = 0.0;
        int radius = 0;

        @BeforeEach
        void setUp() {
            // given
            PlaceRedis placeRedisForAPI = PlaceRedisFixture.createPlaceRedisFromAPIWithId(1L);
            PlaceRedis placeRedisForDB = PlaceRedisFixture.createPlaceRedisFromDBWithId(2L);

            given(kakaoClient.searchwithkeyword(keyword, x, y)).willReturn(List.of(placeRedisForAPI));
            given(placeJPARepository.findAllWithKeywordANDRadius(keyword, x, y, radius)).willReturn(List.of(placeRedisForDB));
        }

        @Test
        @DisplayName("트랜잭션 획득 성공 & 저장 성공")
        void searchWithKeyword_shouldCommitTransaction_whenEverythingSucceeds() {
            when(transactionManager.getTransaction(any())).thenReturn(transactionStatus);

            // when
            List<PlaceRedis> result = sut.searchWithKeyword(keyword, x, y, radius);

            // then
            assertEquals(2, result.size());
            verify(transactionManager).commit(transactionStatus);
            verify(transactionManager, never()).rollback(any());
        }

        @Test
        @DisplayName("트랜잭션 획득 성공 & 저장 실패")
        void searchWithKeyword_shouldRollbackTransaction_whenSaveAllThrowsException() {
            // given
            when(transactionManager.getTransaction(any())).thenReturn(transactionStatus);
            when(placeRedisRepository.saveAll(any())).thenThrow(new RuntimeException("저장 실패"));

            // when & then
            assertThrows(RuntimeException.class, () -> {
                sut.searchWithKeyword(keyword, x, y, radius);
            });

            verify(transactionManager).rollback(transactionStatus);
            verify(transactionManager, never()).commit(any());
        }

        @DisplayName("트랜잭션 획득 실패")
        @Test
        void searchWithKeyword_shouldThrowException_whenTransactionAcquisitionFails() {
            // given
            when(transactionManager.getTransaction(any())).thenThrow(new TransactionException("트랜잭션 획득 실패") {
            });

            // when & then
            assertThrows(TransactionException.class, () -> {
                sut.searchWithKeyword(keyword, x, y, radius);
            });

            verify(transactionManager, never()).commit(any());
            verify(transactionManager, never()).rollback(any());
            verify(placeRedisRepository, never()).saveAll(any());
        }
    }

    @Nested
    @DisplayName("카테고리- 트랜잭션 테스트")
    class CategoryTransactionTest {
        @Mock
        private TransactionStatus transactionStatus;

        SearchCategory category = SearchCategory.FD6;
        double x = 0.0;
        double y = 0.0;
        int radius = 0;

        @BeforeEach
        void setUp() {
            // given
            PlaceRedis placeRedisForAPI = PlaceRedisFixture.createPlaceRedisFromAPIWithId(1L);
            PlaceRedis placeRedisForDB = PlaceRedisFixture.createPlaceRedisFromDBWithId(2L);

            given(kakaoClient.searchWithCategory(category, x, y)).willReturn(List.of(placeRedisForAPI));
            given(placeJPARepository.findAllWithCategoryANDRadius(category, x, y, radius)).willReturn(List.of(placeRedisForDB));
        }

        @Test
        @DisplayName("트랜잭션 획득 성공 & 저장 성공")
        void searchWithKeyword_shouldCommitTransaction_whenEverythingSucceeds() {
            when(transactionManager.getTransaction(any())).thenReturn(transactionStatus);

            // when
            List<PlaceRedis> result = sut.searchWithCategory(category, x, y, radius);

            // then
            assertEquals(2, result.size());
            verify(transactionManager).commit(transactionStatus);
            verify(transactionManager, never()).rollback(any());
        }

        @Test
        @DisplayName("트랜잭션 획득 성공 & 저장 실패")
        void searchWithKeyword_shouldRollbackTransaction_whenSaveAllThrowsException() {
            // given
            when(transactionManager.getTransaction(any())).thenReturn(transactionStatus);
            when(placeRedisRepository.saveAll(any())).thenThrow(new RuntimeException("저장 실패"));

            // when & then
            assertThrows(RuntimeException.class, () -> {
                sut.searchWithCategory(category, x, y, radius);
            });

            verify(transactionManager).rollback(transactionStatus);
            verify(transactionManager, never()).commit(any());
        }

        @Test
        @DisplayName("트랜잭션 획득 실패")
        void searchWithKeyword_shouldThrowException_whenTransactionAcquisitionFails() {
            // given
            when(transactionManager.getTransaction(any())).thenThrow(new TransactionException("트랜잭션 획득 실패") {
            });

            // when & then
            assertThrows(TransactionException.class, () -> {
                sut.searchWithCategory(category, x, y, radius);
            });

            verify(transactionManager, never()).commit(any());
            verify(transactionManager, never()).rollback(any());
            verify(placeRedisRepository, never()).saveAll(any());
        }

    }

}



