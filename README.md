# 🎡 Fantasy Factory: 통합 테마파크 서비스

## 프로젝트 소개
테마파크 이용객들이 웹을 통해 놀이기구 대기줄을 예약하고 입장권을 구매하며,<br>
해시태그를 활용한 콘텐츠 추천 등의 다양한 기능을 편리하게 이용할 수 있도록 구성된 통합 웹 애플리케이션입니다.

## 목표

**MSA 설계**

- 대규모 시스템 개발을 위해 핵심 도메인별로 분리하여 설계했습니다.

**Kafka 사용**

- 서비스 확장성을위해 kafka를 사용하여 메세지처리기능을 구현했습니다.

**모니터링도구 사용**

- Prometheus를 사용하여 서비스의 메트릭을 수집하고 모니터링할 수 있도록 설정했습니다.
- Grafana를 연동하여 Prometheus에서 수집한 데이터를 시각화하고, 실시간으로 서비스 상태를 모니터링할 수 있는 대시보드를 생성했습니다.
- Grafana에서 CPU 사용량이 **50% 이상**일 때 자동으로 Slack에 알림이 전송되도록 설정했습니다.

**대규모 트래픽 대응**

- Jmeter를 사용하여 대규모 트래픽 시에도 원활하게 실행되는지 부하테스트를 진행했습니다.
- 트래픽이 몰릴경우 동기화문제가 발생하는것을 해결하기위해 Redis 의 메모리 캐싱기법 사용 및 분산락 도입했습니다.

**Swagger API 문서화**

- 작성한 API를 문서화하여 확인이 용이하도록 했습니다.

<br><br>


## 팀원 소개

| [김도원](https://github.com/dowon0113) | [문고은](https://github.com/moongzz) | [신다은](https://github.com/devdaeun) | [김대중](https://github.com/djmachine) |
| --- | --- | --- | --- |
| <a href="https://github.com/dowon0113"><img height="150px" width="130px" src="https://github.com/user-attachments/assets/6d3e3b18-3e88-4f19-889b-2ec0ce2cfcb1"/></a> | <a href="https://github.com/moongzz"><img height="150px" width="130px" src="https://github.com/user-attachments/assets/14b98138-b68b-44e7-85b2-1b8ac2640e81"/></a> | <a href="https://github.com/devdaeun"><img height="150px" width="130px" src=""/></a> | <a href="https://github.com/djmachine"><img height="150px" width="130px" src=""/></a> | 
| 상품, 티켓팅 | 인증/인가, 유저, 슬랙 | 테마파크, 대기열, 해시태그 | 주문, 결제 | 

<br><br>

## 프로젝트 일정 및 진행

### 4.3 ~ 4.4
- 주제 및 목표 선정
- 기능 정리
- API 명세서, 인프라 설계도, 테이블 명세서, ERD, 애그리거트 구조도 작성

### 4.7 ~ 4.17
- MVP 개발

### 4.18 ~ 4.21
- 통합 테스트
- 트러블 슈팅

### 4.22 ~ 4.25
- 1차 성능 테스트
- 고도화 (Redis, Kafka 적용)
- 2차 성능 테스트

### 4.26 ~ 4.29
- 리팩톤

### 4.30
- 2차 통합 테스트


<br><br>

## 개발 환경

본 프로젝트는 Java와 Spring Boot 기반으로 개발되었으며, MSA 구조에서 각 서비스 간 유기적인 연동을 위해 Eureka 기반의 서비스 레지스트리와 FeignClient를 활용한 REST 통신을 v1에 구성했습니다. 서비스 확장성을 고려하여 v2에는 Redis 캐싱을 도입하였으며, v3에는 Kafka 기반 비동기 메세지 처리를 구현하였습니다. 인증과 인가는 Spring Security와 JWT를 통해 stateless 기반의 보안 처리를 구현했으며, 데이터 처리에는 Spring Data JPA와 QueryDSL을 적용하여 복잡한 조건의 데이터 조회도 유연하게 처리할 수 있도록 했습니다.
각 도메인 별 데이터 독립성을 고려하여 서비스별 DB 분리를 지향했으나, 학습 환경의 제약으로 인해 하나의 데이터베이스 내에서 스키마를 분리하는 방식으로 논리적인 격리를 구현하였습니다.

로컬에서도 전체 MSA 환경을 손쉽게 구동할 수 있도록 Docker와 Docker Compose를 활용하여 서비스 실행 환경을 통합 구성했습니다. 이를 통해 개발자 간 환경 차이를 줄이고, 테스트 및 배포 전 과정을 효율적으로 수행할 수 있도록 했습니다.

### Stacks
<div>
<img src="https://github.com/user-attachments/assets/30dd8d49-88e3-4e47-9526-532bcd2fa3af" width="700">
</div>

<br><br>

## 프로젝트 실행 방법
### 1. 로컬 실행
```
# 1. Git 저장소 클론 및 이동

# 2. 의존성 설치 및 빌드
./gradlew clean build -x test

# 3. 서비스 실행 (순서대로 실행)
# Eureka (서비스 디스커버리)
cd eureka
java -jar build/libs/eureka.jar

# 4. Gateway 실행
cd ../gateway
java -jar build/libs/gateway.jar

# 5. 나머지 서비스 실행 (예: auth, user 등)
cd ../auth
java -jar build/libs/auth.jar
# ... (필요한 다른 서비스도 동일하게 실행)

```

### 2. Docker 실행
```
# 1. Git 저장소 클론 및 이동
git clone https://github.com/numberOnethemepark/themepark
cd themepark

# 2. 환경 변수 설정 (.env 파일 생성)
# 로컬 실행과 동일

# 3. 배포 스크립트 실행 또는 Docker Compose 실행
# 배포 스크립트 사용 시:
chmod +x deploy.sh
./deploy.sh

# 또는, Docker Compose로 직접 실행
docker-compose up -d

# 4. 실행 상태 확인 (예: Eureka는 http://localhost:8761, Gateway는 http://localhost:8080)
docker ps

```
<br><br>

## 기술적 의사결정
<details>
<summary>인증/인가 방식</summary>
HTTP는 Stateless 통신이기 때문에 로그인 상태를 유지하려면 별도의 인증 방식이 필요했습니다. Session 방식과 JWT 방식을 비교하고 프로젝트에 적합한 방식을 선택했습니다.

**Session 방식과 JWT 방식 비교**
- Session 방식은 서버 측에서 세션을 관리하므로 보안성이 높고 세션을 쉽게 무효화할 수 있다는 장점이 있습니다.
그러나 유저 수가 많아질수록 세션 객체가 증가하여 서버의 메모리 부하가 커질 수 있습니다.
- JWT는 클라이언트 측에서 토큰을 저장하기 때문에 서버의 부하를 줄일 수 있다는 장점이 있습니다.
하지만 토큰이 탈취될 경우 보안 문제가 존재하며, 한 번 발급된 토큰은 서버에서 임의로 폐기하기 어렵다는 단점도 있습니다.

**JWT 선택 이유**
MSA와 같은 분산 환경에서의 확장성과 서버 메모리 사용 최적화를 고려하여 JWT 방식을 채택했습니다.
또한 보안 강화를 위해 Access Token의 유효 기간을 짧게 설정하고, Refresh Token을 활용해 토큰 재발급이 가능하도록 구현했습니다.
</details>
<details>
<summary>Kafka 기반 비동기 전환</summary>
**동기 방식과 비동기 방식 비교**
기존 Feign client기반의 동기 방식의 경우 요청을 보낸 쪽에서 응답이 올 때까지 대기해야 하므로 서버 간 의존도가 높고 처리 지연 시 전체 흐름에 영향이 간다는 단점이 존재했습니다.

이를 해결하기 위해 비동기 방식으로 전환하였습니다.
그 과정에서 RebbitMQ는 단기적인  메세지 전달에 적합하므로 대규모 데이터 처리와 로그 수집, 스트리밍 처리에 더 강점을 가지는 kafka를 도입하였습니다.

**메세지 유실 방지**
1. 수동 ACK 설정

```java
factory.getContainerProperties().setAckMode(ContainerProperties.AckMode.MANUAL_IMMEDIATE);
```

- KafkaListener가 메세지를 consume할 때 자동으로 commit하지 않고, ack.acknowledge()를 직접 호출해야만 offset이 커밋됩니다.
- 예외가 발생하거나 ack.acknowledge()가 호출되지 않으면 Kafka는 메세지를 다시 전달합니다.
- 즉, 메세지를 정상 처리했을 경우에만 커밋되므로 실패 시 다시 처리가 가능하며, 메세지 유실 방지가 됩니다.
2. 재시도 + Dead Letter Topic 발행

```java
DeadLetterPublishingRecoverer recoverer = new DeadLetterPublishingRecoverer(dlqKafkaTemplate);

DefaultErrorHandler errorHandler = new DefaultErrorHandler(
        recoverer,
        new FixedBackOff(1000L, 3L)
);
```

- 메세지 처리 실패 시 1초 간격으로 최대 3번 시도 후 실패 DeadLetterPublishingRecoverer가 작동 하여 Dead Letter Topic를 발행합니다.
- 문제가 된 메세지를 격리 시켜 정상 메시지 흐름에 방해되지 않으며, 오류 메세지를 한 토픽에 모아 조회, 분석, 모니터링이 유용합니다.
- 추후 운영 시스템과 연동해서 Slack 알림 메세지 전송, DLT 메세지 수 카운팅, 재처리 전용 KafkaListener를 구현한다면 운영 자동화를 이루어 효율적이고 안정적인 시스템을 기대할 수 있습니다.
</details>

<br><br>

## 트러블 슈팅
### [JWT 관리 전략 전환](https://github.com/numberOnethemepark/themepark/wiki/JWT-%EA%B4%80%EB%A6%AC-%EC%A0%84%EB%9E%B5-%EC%A0%84%ED%99%98)
### [비관적 락 적용](https://github.com/numberOnethemepark/themepark/wiki/%EB%B9%84%EA%B4%80%EC%A0%81-%EB%9D%BD-%EC%A0%81%EC%9A%A9)
### [상품 조회 테스트 에러](https://github.com/numberOnethemepark/themepark/wiki/%ED%8A%B8%EB%9F%AC%EB%B8%94-%EC%8A%88%ED%8C%85-2)
### [상품 등록 시 재고 저장 구조 개선](https://github.com/numberOnethemepark/themepark/wiki/%ED%8A%B8%EB%9F%AC%EB%B8%94-%EC%8A%88%ED%8C%851)
<br><br>

## 주요 기능
### 티켓팅

>상품 도메인은 일반 입장권, 연회원권, 이벤트 입장권으로 관리되며 이벤트 입장권의 경우 티켓팅 기능이 포함되는 상품입니다. <br>
Redisson의 **분산 락을 통해 동시성이 제어**되며 선착순 티켓팅이 이뤄집니다.<br>
**Kafka 기반의 비동기 메시지**를 통해 순차적으로 티켓 수량의 감소 및 복구를 처리하였으며, <br>
Dead Letter Topic을 발행하여 정상적으로 처리되지 않은 메세지를 따로 보관하여 **재시도 및 오류 대응**이 용이하도록 구성했으며, 수동 ACK 설정을 통해 **메세지 유실을 방지**하였습니다.

### 테마파크
>각 테마파크는 놀이기구,관광명소 여부에 따라 ENUM Type으로 관리할 수 있도록 하였고,<br>
해시태그와의 중간 테이블을 구현하여 하나의 테마파크에 여러개의 해시태그를 추가할 수 있도록 구현하였습니다.

### 해시태그
>해시태그를 이용하여 사용자가 원하는 테마파크를 검색할 수 있도록 구현하였습니다.

### 대기열
>**대규모 트래픽**을 고려하여 redis cache 를 적용하였고,
**동시성 문제**를 해결하기위해 분산락을 도입하여 대기 신청기능을 구현하였습니다.<br>
대기 호출 시 사용자에게 슬랙 알림이 전달되도록 하기위해서 대기열 서비스에서 
**kafka**를 활용하여 슬랙서비스로  정보를 전달하도록 구현하였습니다.

### 주문
>주문 생성 과정에서 오류가 발생할 경우를 대비하여 **Saga 패턴**을 도입해 **보상 트랜잭션**이 
실행되도록 설계하였습니다.<br>
상품 조회 빈도가 많을 것으로 예상되어 **Redis를 활용한 캐싱 처리**를 통해 응답 속도를 
개선하였습니다.<br>
해당 상품이 **재고가 있는 이벤트 상품**인 경우에는, **Kafka 기반의 비동기 메시지 처리**를 통해 
재고 감소 요청을 수행함으로써 서비스의 부하를 효과적으로 분산시키고 있습니다.

### 결제
>외부 결제 API인 TossPayment 와 연동하여, 사용자가 안전하고 간편하게 결제 할 수 있는 
기능을 구현하였습니다. <br> 결제 과정에서는 결제 승인 여부에 따른 후속 처리가 가능하도록 
구성하였습니다.

### 회원
>서비스 이용을 위해 계정을 생성하며, 사용자는 부여된 권한에 따라 일반 사용자와 관리자로 구분됩니다.<br> 각 역할에 따라 접근 가능한 기능이 제한되며, JWT를 통해 사용자의 인증 여부를 검증합니다.


## 개발 산출물
<details>
<summary>API 구현</summary>
<div>
<img src="https://github.com/user-attachments/assets/f6a6ab2e-7516-4d92-b0f3-54415a178a69" width="500">
</div>
</details>
<details>
<summary>인프라 설계도</summary>
<div>
<img src="https://github.com/user-attachments/assets/1a6bdf00-b0ac-48a0-a6ef-f07bba1099f9" width="500">
</div>
</details>
<details>
<summary>ERD</summary>
</details>
<details>
<summary>Flow Chart</summary>
</details>

<br><br>
