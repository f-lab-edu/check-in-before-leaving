package com.company.member.common.stub.test;


import com.company.member.common.UUIDTester;
import com.company.member.common.fixture.domain.MemberFixture;
import com.company.member.common.fixture.domain.dto.TrackFixture;
import com.company.member.common.stub.MemberJPARepositoryStub;
import com.company.member.domain.exceptions.member.MemberException;
import com.company.member.domain.model.member.Member;
import com.company.member.domain.model.member.MemberService;
import com.company.member.domain.model.member.PasswordEncoder;
import com.company.member.infrastructure.adapter.db.jpa.member.MemberJPARepository;
import org.junit.ClassRule;
import org.junit.jupiter.api.*;
import org.mockito.Spy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;

import static com.company.member.domain.exceptions.member.MemberErrorCode.ALREADY_REGISTERED_USER;
import static com.company.member.domain.exceptions.member.MemberErrorCode.NOT_EXITING_USER;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;


@SpringBootTest
@Testcontainers
@Transactional
@Disabled
class MemberJPARepositoryStubIntegratedTest {

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

    @Spy
    private MemberJPARepositoryStub stub;

    @Autowired
    private MemberJPARepository db;

    @Nested
    @DisplayName("findByEmail 테스트")
    class FindByEmailTest {

        @Test
        @DisplayName("이메일로 조회 - 성공.")
        void findByEmail() {
            //given
            Member member = MemberFixture.create();
            db.save(member);
            stub.save(member);

            //when
            Member dbResult = db.findByEmail(member.getEmail());
            Member stubResult = stub.findByEmail(member.getEmail());

            //then
            org.assertj.core.api.Assertions.assertThat(UUIDTester.isUUID(dbResult.getId())).isTrue();
            assertThat(UUIDTester.isUUID(stubResult.getId())).isTrue();
            assertThat(dbResult.getEmail()).isEqualTo(stubResult.getEmail());
            assertThat(dbResult.getName()).isEqualTo(stubResult.getName());
            assertThat(dbResult.getPassword()).isEqualTo(stubResult.getPassword());
            assertThat(dbResult.getPhone()).isEqualTo(stubResult.getPhone());
            assertThat(dbResult.getAddress()).isEqualTo(stubResult.getAddress());
            assertThat(dbResult.isLocationServiceEnabled()).isEqualTo(stubResult.isLocationServiceEnabled());
            assertThat(dbResult.getPoint()).isEqualTo(stubResult.getPoint());
        }

        @Test
        @DisplayName("이메일로 조회 - 존재하지 않는 회원.")
        void findByEmail_NOT_EXISTING_MEMBER() {

            //given
            String email = "NO_EXIST";

            //when
            Exception dbException = assertThrows(MemberException.class, () -> db.findByEmail(email));
            Exception stubException = assertThrows(MemberException.class, () -> stub.findByEmail(email));

            //then
            assertThat(dbException.getClass()).isEqualTo(MemberException.class);
            assertThat(stubException.getClass()).isEqualTo(MemberException.class);
            assertThat(dbException.getMessage()).isEqualTo(NOT_EXITING_USER.getDeatil());
            assertThat(stubException.getMessage()).isEqualTo(NOT_EXITING_USER.getDeatil());


        }
    }

    @Nested
    @DisplayName("findById 테스트")
    class findByIdTest {

        @Test
        @DisplayName("회원 ID로 조회 - 성공.")
        void findById() {
            Member member = MemberFixture.create();
            String dbId = db.save(member);
            String stubId = stub.save(member);

            //when
            Member dbResult = db.findById(dbId);
            Member stubResult = stub.findById(stubId);

            //then
            assertThat(UUIDTester.isUUID(dbResult.getId())).isTrue();
            assertThat(UUIDTester.isUUID(stubResult.getId())).isTrue();
            assertThat(dbResult.getEmail()).isEqualTo(stubResult.getEmail());
            assertThat(dbResult.getName()).isEqualTo(stubResult.getName());
            assertThat(dbResult.getPassword()).isEqualTo(stubResult.getPassword());
            assertThat(dbResult.getPhone()).isEqualTo(stubResult.getPhone());
            assertThat(dbResult.getAddress()).isEqualTo(stubResult.getAddress());
            assertThat(dbResult.isLocationServiceEnabled()).isEqualTo(stubResult.isLocationServiceEnabled());
            assertThat(dbResult.getPoint()).isEqualTo(stubResult.getPoint());
        }

        @Test
        @DisplayName("회원 ID로 조회 - 존재하지 않는 회원.")
        void findById_NOT_EXISTING_MEMBER() {
            //given
            String id = "NO_EXIST";

            //when
            Exception dbException = assertThrows(MemberException.class, () -> db.findById(id));
            Exception stubException = assertThrows(MemberException.class, () -> stub.findById(id));

            //then
            assertThat(dbException.getClass()).isEqualTo(MemberException.class);
            assertThat(stubException.getClass()).isEqualTo(MemberException.class);
            assertThat(dbException.getMessage()).isEqualTo(NOT_EXITING_USER.getDeatil());
            assertThat(stubException.getMessage()).isEqualTo(NOT_EXITING_USER.getDeatil());
        }
    }

    @Nested
    @DisplayName("save 테스트")
    class saveTest {

        @Test
        @DisplayName("회원가입 - 성공.")
        void save() {
            Member member = MemberFixture.create();

            //when
            String dbId = db.save(member);
            String stubId = stub.save(member); //check: 통과 이유?

            //then
            assertThat(UUIDTester.isUUID(dbId)).isTrue();
            assertThat(UUIDTester.isUUID(stubId)).isTrue();
        }

        @Test
        @DisplayName("회원가입 - 이미 가입한 회원.")
        void save_ALREADY_REGISTERED_USER_Exception() {
            Member member = MemberFixture.create();
            db.save(member);
            stub.save(member);

            //when
            Exception dbException = assertThrows(MemberException.class, () -> db.save(member));
            Exception stubException = assertThrows(MemberException.class, () -> stub.save(member));

            //then
            assertThat(dbException.getClass()).isEqualTo(MemberException.class);
            assertThat(stubException.getClass()).isEqualTo(MemberException.class);
            assertThat(dbException.getMessage()).isEqualTo(ALREADY_REGISTERED_USER.getDeatil());
            assertThat(stubException.getMessage()).isEqualTo(ALREADY_REGISTERED_USER.getDeatil());
        }
    }

    @Nested
    @DisplayName("delete 테스트")
    class deleteTest {
        @Test
        @DisplayName("회원 삭제 - 성공.")
        void delete() {
            Member member = MemberFixture.create();
            String dbId = db.save(member);
            String stubId = stub.save(member);

            //when
            db.delete(dbId);
            stub.delete(stubId);

            //then
            Exception dbException = assertThrows(MemberException.class, () -> db.findById(dbId));
            assertThat(dbException.getMessage()).isEqualTo(NOT_EXITING_USER.getDeatil());
            Exception stubException = assertThrows(MemberException.class, () -> stub.findById(stubId));
            assertThat(stubException.getMessage()).isEqualTo(NOT_EXITING_USER.getDeatil());
        }
    }

    @Nested
    @DisplayName("update 테스트")
    @SpringBootTest
    class updateTest {

        @Test
        @DisplayName("회원 수정 - 성공.")
        void update() {
            Member member = MemberFixture.create();
            String dbId = db.save(member);
            String stubId = stub.save(member);

            //when
            Member dbUpdatingMember = MemberFixture.createUpdatingMember(dbId, "updated@update.com", "updatedName", "updatedPassword", "01000000000", "updatedAddress", false, 10L);
            Member stubUpdatingMember = MemberFixture.createUpdatingMember(stubId, "updated@update.com", "updatedName", "updatedPassword", "01000000000", "updatedAddress", false, 10L);
            Member dbResult = db.update(dbUpdatingMember);
            Member stubResult = stub.update(stubUpdatingMember);

            //then
            assertThat(dbId).isEqualTo(dbResult.getId());
            assertThat(stubId).isEqualTo(stubResult.getId());
            assertThat(dbResult.getEmail()).isEqualTo(stubResult.getEmail());
            assertThat(dbResult.getName()).isEqualTo(stubResult.getName());
            assertThat(dbResult.getPhone()).isEqualTo(stubResult.getPhone());
            assertThat(dbResult.getAddress()).isEqualTo(stubResult.getAddress());
            assertThat(dbResult.getPoint()).isEqualTo(stubResult.getPoint());
        }
    }

    @Nested
    @DisplayName("findNearByMember 테스트")
    class findNearByMemberTest {

        @Autowired
        private MemberService memberService;

        private MemberService memberService_stub;

        @BeforeEach
        void setUp() {
            memberService_stub = new MemberService(stub, mock(PasswordEncoder.class));
        }

        @Test
        @DisplayName("findNearByMember - 가까이 있는 맴버.")
        void findNearByMember() {
            //given
            Member member = MemberFixture.create();
            MemberService.Track targetLocation = TrackFixture.createRequestWithDifferentLocation(0, 0);
            String id_db = db.save(member);
            String id_stub = stub.save(member);
            memberService.startLocationTracking(id_db, targetLocation);
            memberService_stub.startLocationTracking(id_stub, targetLocation);


            Member memberNearBy = MemberFixture.createMemberWithDifferentEmail("memberNearBy@test.com");
            MemberService.Track request = TrackFixture.createRequestWithDifferentLocation(0, 0.002);

            String nearById_db = db.save(memberNearBy);
            String nearByid_stub = stub.save(memberNearBy);
            memberService.startLocationTracking(nearById_db, request);
            memberService_stub.startLocationTracking(nearByid_stub, targetLocation);

            //when
            List<Member> stubResult = stub.findNearByMember(0, 0, 500);
            List<Member> dbResult = db.findNearByMember(0, 0, 500);

            assertEquals(2, stubResult.size());
            assertEquals(2, dbResult.size());
        }

        @Test
        @DisplayName("findNearByMember - 위치 값 없는 맴버")
        void findNearByMember_NoLocation() {
            Member member = MemberFixture.create();
            db.save(member);
            stub.save(member);

            //when
            List<Member> stubResult = stub.findNearByMember(0, 0, 10);
            List<Member> dbResult = db.findNearByMember(0, 0, 10);

            assertEquals(dbResult.size(), stubResult.size());
            assertEquals(0, stubResult.size());
            assertEquals(0, dbResult.size());
        }

        @Test
        @DisplayName("findNearByMember - 떨어져 있는 맴버.")
        void findNearByMember_OtherFar() {
            //given
            Member member = MemberFixture.create();
            MemberService.Track targetLocation = TrackFixture.createRequestWithDifferentLocation(0, 0);
            String id_db = db.save(member);
            String id_stub = stub.save(member);

            memberService.startLocationTracking(id_db, targetLocation);
            memberService_stub.startLocationTracking(id_stub, targetLocation);

            Member memberFar = MemberFixture.createMemberWithDifferentEmail("memberFar@test.com");
            MemberService.Track request = TrackFixture.createRequestWithDifferentLocation(400, 400);
            String farId_db = db.save(memberFar);
            String farId_stub = stub.save(memberFar);

            memberService.startLocationTracking(farId_db, request);
            memberService_stub.startLocationTracking(farId_stub, request);

            //when
            List<Member> stubResult = stub.findNearByMember(0, 0, 10);
            List<Member> dbResult = db.findNearByMember(0, 0, 10);

            assertEquals(1, stubResult.size());
            assertEquals(1, dbResult.size());
        }

    }

}