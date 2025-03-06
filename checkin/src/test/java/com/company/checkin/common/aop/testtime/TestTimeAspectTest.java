package com.company.checkin.common.aop.testtime;

import com.company.checkin.common.testContainer.SharedTestContainerMySQL;
import com.company.checkin.fixtures.checkin.help.CheckInFixtures;
import com.company.checkin.help.application.help.checkin.command.CheckInWriteApplication;
import com.company.checkin.help.domain.model.help.checkin.CheckInService;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

@SpringBootTest
@Testcontainers
@Import({TestTimeAspect.class})
class TestTimeAspectTest {

    private static final String SCHEMA_NAME = "time_aspect_ingegrated_test";

    @DynamicPropertySource
    static void setUp(DynamicPropertyRegistry registry) {
        SharedTestContainerMySQL container = SharedTestContainerMySQL.getInstance();
        container.setUpSchema(registry, SCHEMA_NAME);
    }

    @AfterAll
    static void tearDown() {
        SharedTestContainerMySQL container = SharedTestContainerMySQL.getInstance();
        container.removeSchema(container, SCHEMA_NAME);
    }

    @Autowired
    private CheckInWriteApplication sut;

    @SpyBean
    private TestTimeAspect testTimeAspect;

    @Test
    void log() throws Throwable {

        //given
        CheckInService.Creation dto = CheckInFixtures.CheckInServiceT.CreationT.create();

        //when
        sut.register(dto);

        verify(testTimeAspect).log(any());
    }
}