package com.membercontext.memberAPI.infrastructure.db.jpa.member;

import com.membercontext.common.UUIDTester;
import com.membercontext.common.fixture.domain.MemberFixture;
import com.membercontext.common.fixture.web.TrackRequestFixture;
import com.membercontext.common.stub.MemberSpringJPARepositoryStub;
import com.membercontext.memberAPI.application.exception.member.MemberException;
import com.membercontext.memberAPI.domain.entity.member.Member;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static com.membercontext.memberAPI.application.exception.member.MemberErrorCode.ALREADY_REGISTERED_USER;
import static com.membercontext.memberAPI.application.exception.member.MemberErrorCode.NOT_EXITING_USER;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class MemberJPARepositoryTest {

    @InjectMocks
    private MemberJPARepository sut;

    @Spy
    private MemberSpringJPARepositoryStub memberSpringJPARepository;

    @Nested
    @DisplayName("findByEmail 테스트")
    class FindByEmailTest {

        @Test
        @DisplayName("이메일로 조회 - 성공.")
        void findByEmail() {
            //given
            Member member = MemberFixture.create();
            Member saved = memberSpringJPARepository.save(member);

            //when
            Member returned = sut.findByEmail(member.getEmail());

            //then
            assertThat(returned.getId()).isEqualTo(saved.getId());
            assertThat(returned.getEmail()).isEqualTo(member.getEmail());
            assertThat(returned.getName()).isEqualTo(member.getName());
            assertThat(returned.getPassword()).isEqualTo(member.getPassword());
            assertThat(returned.getPhone()).isEqualTo(member.getPhone());
            assertThat(returned.getAddress()).isEqualTo(member.getAddress());
            assertThat(returned.isLocationServiceEnabled()).isEqualTo(member.isLocationServiceEnabled());
            assertThat(returned.getPoint()).isEqualTo(member.getPoint());
        }

        @Test
        @DisplayName("이메일로 조회 - 존재하지 않는 회원.")
        void findByEmail_NOT_EXISTING_MEMBER() {
            //given
            Member member = MemberFixture.create();

            //when
            Exception exception = assertThrows(MemberException.class, () -> sut.findByEmail(member.getEmail()));

            //then
            assertEquals(MemberException.class, exception.getClass());
            assertEquals(NOT_EXITING_USER.getDeatil(), exception.getMessage());
        }
    }

    @Nested
    @DisplayName("findById 테스트")
    class findByIdTest {

        @Test
        @DisplayName("회원 ID로 조회 - 성공.")
        void findById() {
            //given
            Member member = MemberFixture.create();
            Member saved = memberSpringJPARepository.save(member);

            //when
            Member returned = sut.findById(saved.getId());

            //then
            assertThat(returned.getId()).isEqualTo(saved.getId());
            assertThat(returned.getEmail()).isEqualTo(member.getEmail());
            assertThat(returned.getName()).isEqualTo(member.getName());
            assertThat(returned.getPassword()).isEqualTo(member.getPassword());
            assertThat(returned.getPhone()).isEqualTo(member.getPhone());
            assertThat(returned.getAddress()).isEqualTo(member.getAddress());
            assertThat(returned.isLocationServiceEnabled()).isEqualTo(member.isLocationServiceEnabled());
            assertThat(returned.getPoint()).isEqualTo(member.getPoint());
        }

        @Test
        @DisplayName("회원 ID로 조회 - 존재하지 않는 회원.")
        void findById_NOT_EXISTING_MEMBER() {
            //given
            Member member = MemberFixture.create();

            //when
            Exception exception = assertThrows(MemberException.class, () -> sut.findById(member.getId()));

            //then
            assertThat(exception.getClass()).isEqualTo(MemberException.class);
            assertThat(exception.getMessage()).isEqualTo(NOT_EXITING_USER.getDeatil());
        }
    }

    @Nested
    @DisplayName("save 테스트")
    class saveTest {

        @Test
        @DisplayName("회원가입 - 성공.")
        void save() {
            //given
            Member member = MemberFixture.create();

            //when
            String id = sut.save(member);

            //then
            assertNull(member.getId());
            assertNotNull(id);
            assertThat(UUIDTester.isUUID(id)).isTrue();
        }

        @Test
        @DisplayName("회원가입 - 이미 가입한 회원.")
        void save_ALREADY_REGISTERED_USER_Exception() {

            //given
            Member member = MemberFixture.create();
            sut.save(member);

            //when
            Exception exception = assertThrows(MemberException.class, () -> sut.save(member));

            //then
            assertThat(exception.getClass()).isEqualTo(MemberException.class);
            assertThat(exception.getMessage()).isEqualTo(ALREADY_REGISTERED_USER.getDeatil());
        }
    }

    @Nested
    @DisplayName("delete 테스트")
    class deleteTest {
        @Test
        @DisplayName("회원 삭제 - 성공.")
        void delete() {
            //todo: deleteById로 변경 고려.
            //given
            Member member = MemberFixture.create();
            Member saved = memberSpringJPARepository.save(member);

            //when
            sut.delete(saved.getId());

            //then
            Exception exception = assertThrows(MemberException.class, () -> sut.findById(member.getId()));
            assertEquals(NOT_EXITING_USER.getDeatil(), exception.getMessage());
        }
    }

    @Nested
    @DisplayName("update 테스트")
    class updateTest {

        @Test
        @DisplayName("회원 수정 - 성공.")
        void update() {
            //given
            Member originalMember = MemberFixture.create();
            Member saved = memberSpringJPARepository.save(originalMember);
            Member updatingMember = MemberFixture.createUpdatingMember(saved.getId(), "updated@update.com", "updatedName", "updatedPassword", "01000000000", "updatedAddress", false, 10L);

            //when
            Member result = sut.update(updatingMember);

            //then
            assertEquals(result.getId(), updatingMember.getId());
            assertEquals(result.getEmail(), updatingMember.getEmail());
            assertEquals(result.getPassword(), updatingMember.getPassword());
            assertEquals(result.getName(), updatingMember.getName());
            assertEquals(result.getPhone(), updatingMember.getPhone());
            assertEquals(result.getAddress(), updatingMember.getAddress());
            assertEquals(result.isLocationServiceEnabled(), updatingMember.isLocationServiceEnabled());
            assertEquals(result.getPoint(), updatingMember.getPoint());
        }
    }

    @Nested
    @DisplayName("findNearByMember 테스트")
    class findNearByMemberTest {

        @Test
        @DisplayName("주변 회원 조회 - 성공.")
        void findNearByMember() {
            //given
            double x = 0;
            double y = 0;
            int radius = 500; // 500km

            Member memberAt0 = MemberFixture.create();
            memberAt0.startLocationTracking(TrackRequestFixture.createRequestWithDifferentLocation(x, y));

            Member memberNear = MemberFixture.createMemberWithDifferentEmail("nearMember@test.com");
            memberNear.startLocationTracking(TrackRequestFixture.createRequestWithDifferentLocation(0, 0.002));

            memberSpringJPARepository.save(memberAt0);
            memberNear = memberSpringJPARepository.save(memberNear);

            //when
            List<Member> list = sut.findNearByMember(x, y, radius);

            //then
            assertEquals(2, list.size());
            System.out.println(list.get(0).getName());
            System.out.println(list.get(1).getName());
        }

        @Test
        @DisplayName("주변 회원 조회 - 위치값 없는 회원.")
        void findNearByMember_farAway() { //없는 경우
            //given
            double x = 0;
            double y = 0;
            int radius = 1;
            Member memberAt0 = MemberFixture.create();
            memberSpringJPARepository.save(memberAt0);

            List<Member> list = sut.findNearByMember(x, y, radius);

            System.out.println(list.size());
        }


        @Test
        @DisplayName("주변 회원 조회 - 실패.")
        void findNearByMember_nearBy() {
            //given
            double x = 0;
            double y = 0;
            int radius = 1;

            Member memberAt0 = MemberFixture.create();
            memberAt0.startLocationTracking(TrackRequestFixture.createRequestWithDifferentLocation(x, y));

            Member memberFar = MemberFixture.createMemberWithDifferentEmail("farMember@test.com");
            memberFar.startLocationTracking(TrackRequestFixture.createRequestWithDifferentLocation(300, 300));

            memberSpringJPARepository.save(memberAt0);
            memberFar = memberSpringJPARepository.save(memberFar);

            //when
            List<Member> list = sut.findNearByMember(x, y, radius);

            //then
            assertEquals(1, list.size());
            System.out.println(list.get(0).getName());
        }

    }

}