package com.membercontext.common.stub.test;

import com.membercontext.common.UUIDTester;
import com.membercontext.common.exception.TestException;
import com.membercontext.common.fixture.Variables;
import com.membercontext.common.fixture.domain.MemberFixture;
import com.membercontext.common.fixture.web.TrackRequestFixture;
import com.membercontext.common.stub.MemberSpringJPARepositoryStub;
import com.membercontext.memberAPI.application.exception.member.MemberException;
import com.membercontext.memberAPI.application.repository.MemberRepository;
import com.membercontext.memberAPI.domain.entity.member.Member;
import com.membercontext.memberAPI.domain.entity.member.MemberService;
import com.membercontext.memberAPI.infrastructure.db.jpa.member.MemberJPARepository;
import com.membercontext.memberAPI.infrastructure.db.jpa.member.MemberSpringJPARepository;
import com.membercontext.memberAPI.infrastructure.encryption.JavaCryptoUtil;
import com.membercontext.memberAPI.web.controller.TrackController;
import org.junit.ClassRule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Spy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;

import static com.membercontext.common.exception.TestException.NOT_FOUND;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;

@SpringBootTest
@Transactional
@Testcontainers
public class MemberSpringJPARespotiroyStubIntegratedTest {

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
    private MemberSpringJPARepository memberSpringJPARepository;

    @Spy
    private MemberSpringJPARepositoryStub stub;


    @Nested
    @DisplayName("save 테스트")
    class save {
        @Test
        @DisplayName("save - 성공.")
        void save_Success() {
            //given
            Member member = MemberFixture.create();

            //when
            Member dbResult = memberSpringJPARepository.save(member);
            Member stubResult = stub.save(member);

            //then
            assertThat(UUIDTester.isUUID(dbResult.getId())).isTrue();
            assertThat(UUIDTester.isUUID(stubResult.getId())).isTrue();
            assertThat(dbResult.getEmail()).isEqualTo(stubResult.getEmail());
            assertThat(dbResult.getName()).isEqualTo(stubResult.getName());
            assertThat(dbResult.getPhone()).isEqualTo(stubResult.getPhone());
            assertThat(dbResult.getAddress()).isEqualTo(stubResult.getAddress());
            assertThat(dbResult.getPoint()).isEqualTo(stubResult.getPoint());
        }

        @Test
        @DisplayName("save - 중복.")
        @Transactional(propagation = Propagation.NEVER)
        void save_duplicated() {
            //todo: @Transactional 붙이면 db 예상과 다르게 동작.
            //given
            Member member = MemberFixture.create();
            memberSpringJPARepository.save(member);
            stub.save(member);

            Member sameMember = MemberFixture.createUpdatingMember("ID", Variables.TEST_EMAIL, "updatedPassword", "updatedname", "010000000", "updatedLoc", false, 10L);

            //when
            Exception dbException = assertThrows(DataIntegrityViolationException.class, () -> memberSpringJPARepository.save(sameMember));
            Exception stubException = assertThrows(Exception.class, () -> stub.save(sameMember));

            //then
            assertEquals(dbException.getClass(), DataIntegrityViolationException.class);
            assertEquals(stubException.getClass(), MemberException.class);

            //after
            memberSpringJPARepository.delete(member);
            stub.delete(member);
        }
    }

    @Nested
    @DisplayName("이외 사용 메서드")
    class Other {

        @Autowired
        private MemberService memberService;

        private MemberService memberService_stub;

        private Member dbMember;
        private Member stubMember;

        @BeforeEach
        void setUp() {
            Member member = MemberFixture.create();
            dbMember = memberSpringJPARepository.save(member);
            stubMember = stub.save(member);

            MemberRepository memberStub = new MemberJPARepository(stub);
            memberService_stub = new MemberService(memberStub, mock(JavaCryptoUtil.class));
        }

        @Test
        @DisplayName("findById - 성공.")
        void findById() {

            //when
            Member dbResult = memberSpringJPARepository.findById(dbMember.getId()).orElseThrow(() -> new TestException(NOT_FOUND));
            Member stubResult = stub.findById(stubMember.getId()).orElseThrow(() -> new TestException(NOT_FOUND));

            //then
            assertThat(UUIDTester.isUUID(dbResult.getId())).isTrue();
            assertThat(UUIDTester.isUUID(stubResult.getId())).isTrue();
            assertThat(dbResult.getEmail()).isEqualTo(stubResult.getEmail());
            assertThat(dbResult.getName()).isEqualTo(stubResult.getName());
            assertThat(dbResult.getPhone()).isEqualTo(stubResult.getPhone());
            assertThat(dbResult.getAddress()).isEqualTo(stubResult.getAddress());
            assertThat(dbResult.getPoint()).isEqualTo(stubResult.getPoint());
        }

        @Test
        @DisplayName("findByEmail - 성공.")
        void findByEmail() {

            //when
            Member dbResult = memberSpringJPARepository.findByEmail(dbMember.getEmail()).orElseThrow(() -> new TestException(NOT_FOUND));
            Member stubResult = stub.findByEmail(stubMember.getEmail()).orElseThrow(() -> new TestException(NOT_FOUND));

            //then
            assertThat(UUIDTester.isUUID(dbResult.getId())).isTrue();
            assertThat(UUIDTester.isUUID(stubResult.getId())).isTrue();
            assertThat(dbResult.getEmail()).isEqualTo(stubResult.getEmail());
            assertThat(dbResult.getName()).isEqualTo(stubResult.getName());
            assertThat(dbResult.getPhone()).isEqualTo(stubResult.getPhone());
            assertThat(dbResult.getAddress()).isEqualTo(stubResult.getAddress());
            assertThat(dbResult.getPoint()).isEqualTo(stubResult.getPoint());
        }

        @Test
        @DisplayName("delete - 성공")
        void delete() {

            //when
            memberSpringJPARepository.delete(dbMember);
            stub.delete(stubMember);

            //then
            assertThat(memberSpringJPARepository.findById(dbMember.getId()).isEmpty()).isTrue();
            assertThat(stub.findById(stubMember.getId()).isEmpty()).isTrue();
        }

        @Test
        @DisplayName("delete - 중복")
        void delete_duplicated() {
            //given
            memberSpringJPARepository.delete(dbMember);
            stub.delete(stubMember);

            //when
            memberSpringJPARepository.delete(dbMember);
            stub.delete(stubMember);
        }

        @Test
        @DisplayName("findNearByMember - 가까이 있는 맴버.")
        void findNearByMember() {
            //given
            TrackController.TrackRequest targetLocation = TrackRequestFixture.createRequestWithDifferentLocation(0, 0);
            memberService.startLocationTracking(dbMember.getId(), targetLocation);
            memberService_stub.startLocationTracking(stubMember.getId(), targetLocation);

            Member memberNearBy = MemberFixture.createMemberWithDifferentEmail("memberNearBy@test.com");
            TrackController.TrackRequest request = TrackRequestFixture.createRequestWithDifferentLocation(0, 0.002);
            Member memberNearBy_stub = stub.save(memberNearBy);
            Member memberNearBy_db = memberSpringJPARepository.save(memberNearBy);

            memberService_stub.startLocationTracking(memberNearBy_stub.getId(), request);
            memberService.startLocationTracking(memberNearBy_db.getId(), request);

            //when
            List<Member> stubResult = stub.findNearByMember(0, 0, 500);
            List<Member> dbResult = memberSpringJPARepository.findNearByMember(0, 0, 500);

            assertEquals(dbResult.size(), stubResult.size());
            assertEquals(2, stubResult.size());
            assertEquals(2, dbResult.size());
        }

        @Test
        @DisplayName("findNearByMember - 위치 값 없는 맴버")
        void findNearByMember_NoLocation() {
            //when
            List<Member> stubResult = stub.findNearByMember(0, 0, 10);
            List<Member> dbResult = memberSpringJPARepository.findNearByMember(0, 0, 10);

            assertEquals(dbResult.size(), stubResult.size());
            assertEquals(0, stubResult.size());
            assertEquals(0, dbResult.size());
        }


        @Test
        @DisplayName("findNearByMember - 떨어져 있는 맴버.")
        void findNearByMember_OtherFar() {
            TrackController.TrackRequest targetLocation = TrackRequestFixture.createRequestWithDifferentLocation(0, 0);
            memberService.startLocationTracking(dbMember.getId(), targetLocation);
            memberService_stub.startLocationTracking(stubMember.getId(), targetLocation);

            //given
            Member memberFar = MemberFixture.createMemberWithDifferentEmail("MemberFar@test.com");
            TrackController.TrackRequest request = TrackRequestFixture.createRequestWithDifferentLocation(400, 400);
            Member memberFar_stub = stub.save(memberFar);
            Member memberFar_db = memberSpringJPARepository.save(memberFar);

            memberService_stub.startLocationTracking(memberFar_stub.getId(), request);
            memberService.startLocationTracking(memberFar_db.getId(), request);

            //when
            List<Member> stubResult = stub.findNearByMember(0, 0, 10);
            List<Member> dbResult = memberSpringJPARepository.findNearByMember(0, 0, 10);

            assertEquals(dbResult.size(), stubResult.size());
            assertEquals(1, stubResult.size());
            assertEquals(1, dbResult.size());
        }
    }
}
