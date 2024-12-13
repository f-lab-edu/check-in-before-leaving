package com.membercontext.memberAPI.application.aop.testTime;

import com.membercontext.common.fixture.domain.MemberFixture;
import com.membercontext.memberAPI.application.service.MemberWriteSerivces.Impl.MemberWriteApplication;
import com.membercontext.memberAPI.domain.entity.member.Member;
import org.junit.ClassRule;
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
class LogAspectTest_Integrated {

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
    private MemberWriteApplication sut;

    @SpyBean
    private TestTimeAspect loggingAspect;

    @Test
    void log() throws Throwable {

        //given
        Member member = MemberFixture.createMemberWithId("UUID");

        //when
        sut.signUp(member);

        verify(loggingAspect).log(any());
    }
}