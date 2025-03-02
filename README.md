# 🎉 check-in-before-leaving
요즘 핫한 장소들이 어디에 있는지 알아보고 그 곳에서 다양한 요청들을 할 수 있는 서비스입니다. 
  - "체크인"요청을 통해 근처에 계시는 사용자에게 현재 혼잡도나 줄에 대한 사진 요청을 할 수 있습니다.
  - 줄서기 요청을 통해 대리 줄서기를 요청할 수 있습니다. 
  - 기타 요청을 통해 당근 알바의 단기 알바처럼 "픽업"이나 "배달"등 건당으로 커스텀한 요청을 할 수 있습니다. 
### 사용 기술 및 환경
Spring boot, Gradle, JPA, MySQL, Redis, Kafka, Docker, NHN Cloud, Spring Webflux

### 주요 관심사

- [기능별 Use Case](https://github.com/f-lab-edu/check-in-before-leaving/wiki/%08Use-Case)

### 프로젝트 진행 하며 고민한 이슈들
1. 불완전한 상태의 객체 사용 지양 및 자바에서 null 관리.
- [코드 리뷰: 불완전한 객체에서 나타난 문제들](https://computingsteps.tistory.com/45)
- [객체는 불완전하게 사용되면 안된다.](https://computingsteps.tistory.com/41)
- [안정성을 더하는 불변객체](https://computingsteps.tistory.com/42) 
2. 레이어드 아키텍처를 이용해 관심사 분리를 통한 시스템 유연성 및 유지보수성 향상.
 - [JPA는 레이어드 아키텍처에서 어디에 속할까?](https://computingsteps.tistory.com/50)
 - [레이어드 아키텍처 (Layered Architecture) feat. DDD](https://computingsteps.tistory.com/47)
3. 도메인 주도 개발(DDD)의 원칙을 적용해 비즈니스 로직 변경에 유연한 도메인 설계. 
- [DDD 도메인 모델링 개념 다시 잡기](https://computingsteps.tistory.com/57)
- [DDD관점에서의 레이어드 아키텍처](https://computingsteps.tistory.com/49)
4. 상속을 사용시 생길수 있는 문제들.
- [결국 상속 구조를 포기 하기까지. (상속 구조 사용시 주의점)](https://computingsteps.tistory.com/53)
5. 테스트 - 런던파 vs. 고전파 문제.
- [Stub 사용기 (런던파 vs. 고전파)](https://computingsteps.tistory.com/52)
- [나에게 맞는 Fixture 생성법을 찾는 여정](https://computingsteps.tistory.com/51)
7. AOP를 이용한 로그인 인증.   
- [AOP를 이용한 인증 구축](https://computingsteps.tistory.com/54)
8. K6를 이용한 성능 테스트.
- [서버 성능을 테스트하기 위한 부하테스트](https://computingsteps.tistory.com/58)
9. 성능 향상을 위한 캐싱 도입기.
- [캐싱을 도입했다가 철회한 경우들 - 첫번째 경우](https://computingsteps.tistory.com/44)
- [응답속도 향상을 위한 캐싱 적용기 (+ 언제 캐싱을 쓰는게 좋을까?)](https://computingsteps.tistory.com/56)
10. 분산 아키텍처에 적합한 트랜잭션.
- [캐싱을 도입했다가 철회한 경우들 - 두번째 경우](https://computingsteps.tistory.com/44)
- [카프카를 통한 Pub/Sub 구현 및 분산 트랜잭션 사용기](https://computingsteps.tistory.com/59)

  
<br><br>
### 서버 설계도
![서버](https://github.com/user-attachments/assets/837eef39-a7f8-4e51-a091-7ee49c91bf2b)

<br><br>
### ERD
<p align="center">
<img src="https://github.com/user-attachments/assets/dd58736e-24a7-4c3c-b4f6-f28b48c2c8b2" width="550" height="580"/>
</p>

### 메인 도메인 클래스 다이어그램 (Check-In-Request-MS)
<br>
<div align="center">
<table>
  <tr>
    <td align="left">
    <img src="https://github.com/user-attachments/assets/62c38fdb-4120-4669-8160-1cba9f8026f8" width="550" height="600"/>
    </td>

  </tr>
  <tr>
    <td align="left">
    <img src="https://github.com/user-attachments/assets/6f242639-78ff-4e8b-afe5-f23d2264b1d5" width="350" height="500"/>
    </td>
  </tr>
</table>
</div>








