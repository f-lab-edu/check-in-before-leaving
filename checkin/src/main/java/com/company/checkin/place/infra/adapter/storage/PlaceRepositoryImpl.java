package com.company.checkin.place.infra.adapter.storage;

import com.company.checkin.place.domain.model.place.place.PlaceRepository;
import com.company.checkin.place.infra.adapter.storage.cache.redis.PlaceRedis;
import com.company.checkin.place.infra.adapter.storage.cache.redis.PlaceRedisRepository;
import com.company.checkin.place.infra.adapter.storage.db.PlaceJPARepository;
import com.company.checkin.place.infra.adapter.rest.kakao.KakaoClient;
import com.company.checkin.place.infra.adapter.rest.kakao.SearchCategory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class PlaceRepositoryImpl implements PlaceRepository {

    private final PlaceRedisRepository placeRedisRepository;
    private final KakaoClient kakaoClient;
    private final PlatformTransactionManager transactionManager;
    private final PlaceJPARepository placeJPARepository;

    public List<PlaceRedis> searchWithKeyword(String keyword, double x, double y, int radius) {

        List<PlaceRedis> list = new ArrayList<>();
        list.addAll(kakaoClient.searchwithkeyword(keyword, x, y));
        list.addAll(placeJPARepository.findAllWithKeywordANDRadius(keyword, x, y, radius));
        saveAll(list);

        return list;
    }

    public List<PlaceRedis> searchWithCategory(SearchCategory category, double x, double y, int radius) {
        //check: SearchCategory을 인자로 사용시 Form에서 커스텀 Validation 사용으로 특정 키워드만 받도록 검증.
        List<PlaceRedis> list = new ArrayList<>();
        list.addAll(kakaoClient.searchWithCategory(category, x, y));
        list.addAll(placeJPARepository.findAllWithCategoryANDRadius(category, x, y, radius));

        saveAll(list);
        return list;
    }

    //check: 이후 트랜잭션 응용 계층 생기면 응용계층으로 이동.-> list만 받아서 도메인 서비스 통해 저장.
    //check: 통합 테스트 필요.
    private void saveAll(List<PlaceRedis> list) {
        TransactionStatus status = null;
        try {
            status = transactionManager.getTransaction(new DefaultTransactionDefinition());

            placeRedisRepository.saveAll(list);

            transactionManager.commit(status);
        } catch (Exception e) {
            transactionAcquiredButNeedToRollback(status);
            throw e;
        }
    }

    private void transactionAcquiredButNeedToRollback(TransactionStatus status) {
        if (status != null) {
            transactionManager.rollback(status);
        }
    }
}
