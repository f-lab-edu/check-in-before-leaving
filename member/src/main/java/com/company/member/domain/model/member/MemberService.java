package com.company.member.domain.model.member;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.company.member.domain.exceptions.member.MemberErrorCode;
import com.company.member.domain.exceptions.member.MemberException;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

import static com.company.member.domain.exceptions.member.MemberErrorCode.NOT_EXITING_USER;

@Service
@RequiredArgsConstructor
public class MemberService {

    // Member와 같은 패키지에 있어야 함.
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    public String signUp(Member member) {
        String encryptedPassword = passwordEncoder.encrypt(member.getPassword());
        member.encryptPassword(encryptedPassword);
        return memberRepository.save(member);
    }

    public Member update(Member updatingMember) {
        Member member = memberRepository.findById(updatingMember.getId());
        return memberRepository.update(updatingMember);
    }

    public void delete(String id) {
        memberRepository.delete(id);
    }

    //Read
    public Member findOneMember(String id) {
        Member member = memberRepository.findById(id);
        if (member == null) {
            throw new MemberException(NOT_EXITING_USER);
        }
        return member;
    }

    //LogIn
    public Member logIn(String email, String password) {
        Member member = memberRepository.findByEmail(email);
        if (passwordEncoder.checkPassword(password, member.getPassword())) {
            return member;
        } else {
            throw new MemberException(MemberErrorCode.INVALID_PASSWORD);
        }
    }

    //Track
    public void startLocationTracking(String memberId, Track request) {
        Member member = memberRepository.findById(memberId);
        member.startLocationTracking(request);
    }

    public void enablePushAlarm(String token, String memberId) {
        Member member = memberRepository.findById(memberId);
        member.enablePushAlarm(token);
    }

    //Push Alarm
    public List<String> getNearByMemberTokens(double x, double y, int radius) {
        List<Member> membersToPush = memberRepository.findNearByMember(x, y, radius);
        if (membersToPush.isEmpty()) {
            return null;
        }
        return membersToPush.stream()
                .map(member -> member.getMemberLocation().getFcmToken())
                .toList();
    }

    public static final String EMAIL_FORMAT_VALIDATION_MESSAGE = "이메일 형식에 맞게 입력해주세요";
    public static final String EMAIL_VALIDATION_MESSAGE = "이메일을 입력해주세요";
    public static final String PASSWORD_VALIDATION_MESSAGE = "비밀번호를 입력해주세요";
    public static final String NAME_VALIDATION_MESSAGE = "이름을 입력해주세요";
    public static final String PHONE_VALIDATION_MESSAGE = "휴대폰 번호를 입력해주세요";
    public static final String ADDRESS_VALIDATION_MESSAGE = "동을 입력해주세요";
    public static final String LOCATION_SERVICE_VALIDATION_MESSAGE = "위치 서비스 사용 여부를 입력해주세요";

    @Getter
    @Builder(access = AccessLevel.PROTECTED)
    @RequiredArgsConstructor(access = AccessLevel.PROTECTED)
    public static class SignUp {

        @Email(message = EMAIL_FORMAT_VALIDATION_MESSAGE)
        @NotBlank(message = EMAIL_VALIDATION_MESSAGE)
        private final String email;

        @NotBlank(message = PASSWORD_VALIDATION_MESSAGE)
        private final String password;

        @NotBlank(message = NAME_VALIDATION_MESSAGE)
        private final String name;

        @NotBlank(message = PHONE_VALIDATION_MESSAGE)
        private final String phone;

        @NotBlank(message = ADDRESS_VALIDATION_MESSAGE)
        private final String address;

        @NotNull(message = LOCATION_SERVICE_VALIDATION_MESSAGE)
        private final Boolean isLocationServiceEnabled;

        private final long point;
    }

    @Getter
    @Builder(access = AccessLevel.PROTECTED)
    @RequiredArgsConstructor(access = AccessLevel.PROTECTED)
    public static class Update {

        public static final String MEMBER_ID_VALIDATION_MESSAGE = "회원번호를 입력해주세요";
        public static final String POINT_UPDATE_VALIDATION_MESSAGE = "현재 포인트를 입력해주세요";

        @NotNull(message = MEMBER_ID_VALIDATION_MESSAGE)
        private final String id;

        @Email(message = EMAIL_FORMAT_VALIDATION_MESSAGE)
        @NotBlank(message = EMAIL_VALIDATION_MESSAGE)
        private final String email;

        @NotBlank(message = PASSWORD_VALIDATION_MESSAGE)
        private final String password;

        @NotBlank(message = NAME_VALIDATION_MESSAGE)
        private final String name;

        @NotBlank(message = PHONE_VALIDATION_MESSAGE)
        private final String phone;

        @NotBlank(message = ADDRESS_VALIDATION_MESSAGE)
        private final String address;

        @NotNull(message = LOCATION_SERVICE_VALIDATION_MESSAGE)
        private final Boolean isLocationServiceEnabled;

        @NotNull(message = POINT_UPDATE_VALIDATION_MESSAGE)
        private final Long point;
    }

    @Getter
    @RequiredArgsConstructor(access = AccessLevel.PROTECTED)
    @Builder(access = AccessLevel.PROTECTED)
    public static class LogIn {

        public static final String LOG_IN_EMAIL_VALIDATION_MESSAGE = "이메일이 없습니다.";
        public static final String LOG_IN_PASSWORD_VALIDATION_MESSAGE = "비밀번호가 없습니다.";

        @NotBlank(message = LOG_IN_EMAIL_VALIDATION_MESSAGE)
        private final String email;

        @NotBlank(message = LOG_IN_PASSWORD_VALIDATION_MESSAGE)
        private final String password;
    }

    @Getter
    @RequiredArgsConstructor(access = AccessLevel.PROTECTED)
    @Builder(access = AccessLevel.PROTECTED)
    public static class Track {

        public static final String TRACK_LATITUDE_NOT_FOUND = "위도가 없습니다.";
        public static final String TRACK_LONGITUDE_NOT_FOUND = "경도가 없습니다.";
        public static final String TRACK_TIMESTAMP_NOT_FOUND = "시간이 없습니다.";

        @NotNull(message = TRACK_LATITUDE_NOT_FOUND)
        private final Double latitude;

        @NotNull(message = TRACK_LONGITUDE_NOT_FOUND)
        private final Double longitude;

        @NotNull(message = TRACK_TIMESTAMP_NOT_FOUND)
        private final LocalDateTime timestamp;
    }

    @Getter
    @Builder(access = AccessLevel.PROTECTED)
    public static class FCMToken {

        public static final String FCM_TOKEN_NOT_FOUND = "FCM 토큰이 없습니다.";

        @NotBlank(message = FCM_TOKEN_NOT_FOUND)
        private final String token;

        @JsonCreator
        public FCMToken(String token) {
            this.token = token;
        }
    }

    @Getter
    @RequiredArgsConstructor(access = AccessLevel.PROTECTED)
    @Builder(access = AccessLevel.PROTECTED)
    public static class Alarm {

        public static final String ALARM_X_NOT_FOUND = "X 좌표(Longitude, 경도)가 없습니다.";
        public static final String ALARM_Y_NOT_FOUND = "Y 좌표(Latitude, 위도)가 없습니다.";
        public static final String ALARM_TITLE_NOT_FOUND = "알람 제목이 없습니다.";
        public static final String ALARM_MESSAGE_NOT_FOUND = "알람 내용이 없습니다.";

        @NotNull(message = ALARM_X_NOT_FOUND)
        private final Double x;

        @NotNull(message = ALARM_Y_NOT_FOUND)
        private final Double y;

        @NotBlank(message = ALARM_TITLE_NOT_FOUND)
        private final String title;

        @NotBlank(message = ALARM_MESSAGE_NOT_FOUND)
        private final String message;

        //test
        public static Alarm createForTest() {
            return Alarm.builder()
                    .x(1.0)
                    .y(1.0)
                    .title("title")
                    .message("message")
                    .build();
        }
    }
}
