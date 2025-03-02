package com.company.checkin.common.aop.testTime;

import com.company.checkin.help.application.help.checkin.command.CheckInWriteApplication;
import com.company.checkin.fixtures.checkin.help.CheckInFixtures;
import com.company.checkin.help.domain.model.help.checkin.CheckInService;
import org.junit.ClassRule;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

@SpringBootTest
@Testcontainers
@Import({TestTimeAspect.class})
@Disabled
class TestTimeAspectTest {

    //check: testContainer 사용해 도커 백그라운드에 있어야 테스트 성공.
    private static final String USERNAME = "root";
    private static final String PASSWORD = "password";
    private static final String DATABASE_NAME = "mysql_testcontainer";

    @ClassRule
    @Container
    public static MySQLContainer<?> mySQLContainer = new MySQLContainer<>("mysql:8.0")
            .withUsername(USERNAME)
            .withPassword(PASSWORD)
            .withDatabaseName(DATABASE_NAME);

    @DynamicPropertySource
    public static void overrideProps(DynamicPropertyRegistry dynamicPropertyRegistry) {

        dynamicPropertyRegistry.add("spring.datasource.url", () -> mySQLContainer.getJdbcUrl());
        dynamicPropertyRegistry.add("spring.datasource.username", () -> USERNAME);
        dynamicPropertyRegistry.add("spring.datasource.password", () -> PASSWORD);
        dynamicPropertyRegistry.add("spring.jpa.hibernate.ddl-auto", () -> "create");

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