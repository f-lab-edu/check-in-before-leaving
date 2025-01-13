package com.example.checkinrequestMS.fixtures;

import com.example.checkinrequestMS.HelpAPI.domain.model.help.HelpDetail;
import com.example.checkinrequestMS.HelpAPI.domain.model.help.Progress;
import com.example.checkinrequestMS.HelpAPI.domain.model.help.child.CheckIn;
import com.example.checkinrequestMS.HelpAPI.infra.db.entity.child.CheckInEntity;
import com.navercorp.fixturemonkey.FixtureMonkey;
import com.navercorp.fixturemonkey.api.introspector.ConstructorPropertiesArbitraryIntrospector;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Optional;

public class FixtureMonkeyTest {

    @Test
    void test() {
        FixtureMonkey sut = FixtureMonkey.builder()
                .objectIntrospector(new ConstructorPropertiesArbitraryIntrospector())
                .defaultNotNull(true)
                .build();

        HelpDetail helpDetailRandom = sut.giveMeOne(HelpDetail.class);

        HelpDetail helpDetail1 = sut.giveMeBuilder(HelpDetail.class)
                .set("helpRegisterId", 1L)
                .set("title", "title")
                .set("start", LocalDateTime.now())
                .set("end", LocalDateTime.now().plusMinutes(10))
                .set("placeId", "placeId")
                .set("reward", 100L).sample();
        Progress progress = sut.giveMeBuilder(Progress.class)
                .set("status", Progress.ProgressStatus.CREATED)
                .set("helperId", Optional.of(1L))
                .set("photoPath", Optional.of("photoPath"))
                .set("completed", false).sample();


    }
}
