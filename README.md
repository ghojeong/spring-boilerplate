# Spring Boilerplate

스프링 서버를 구축을 위한 보일러 플레이트이다.

## 필요한 환경

- IDE: Intellij
- Docker 가 설치되고 실행되어 있어야 한다.
- `brew install mysql-client`
- `cd docker && sh docker-start.sh` 로 필요한 컨테이너를 활성화한다.
- `cd docker && sh initdb.sh` 로 DB 스키마를 정의하고 더미 데이터를 넣는다.
- DB 를 직접 만들 경우 다음 쿼리를 날리자. `CREATE DATABASE mariadb CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;`
- Spring 프로그램을 실행하고 나면 `localhost:8080/actuator` 를 통해 상세정보를 확인할 수 있다.

## 디렉토리 구조

- controller
    - Controller 객체들을 모은 디렉토리
    - ui 는 테스트가 불필요해야하는 Layer 이다.
    - 따라서 ui 안의 객체들은 조건문과 반복문이 있어서는 안된다.
    - 만약 조건문과 반복문과 같은 로직이 있다면 Service 로 옮길 수 있도록 한다.
- domain
    - DB 와 소통하며 비즈니스 로직을 가지는 객체들을 모은 디렉토리
    - application 에 로직이 있다면, 최대한 domain 으로 옮겨올 수 있도록 해야한다.
    - 단위 테스트가 가능한 Layer 이다.
- dto
    - Layer 사이의 소통을 위한 변환을 담당하는 객체들을 모은 디렉토리
    - 주로 JSON Request 와 Response 를 변환하기 위해 쓰인다.
    - 변환 로직과 validation 로직 외에 테스트가 필요한 비즈니스 로직을 담아서는 안된다.
- exception
    - RuntimeException 을 상속받은 커스템 예외들을 모은 디렉토리
    - exception 안에 정의된 예외는 반드시 ExceptionHandler 를 통해 예외처리가 되어야 한다.
    - exception 안에 정의된 예외로 인해 500 에러가 발생해서는 안된다.
- repository
    - `@Repository` 어노테이션이 붙은 객체들을 모은 디렉토리
    - Repository 는 DAO 를 의미하지 않는다.
    - Entity 를 조회 및 조작할 수 있는 추상화 된 인터페이스를 의미한다.
- service
    - `@Service` 어노테이션이 붙은 객체들을 모은 디렉토리
    - 유의미한 테스트가 가능한 마지막 Layer 이다.
    - 테스트가 필요한 로직이 Controller 에 있다면 Service 로 옮겨야 한다.
- util
    - 전역적으로 쓰이는 로직, 혹은 공통값들을 정의한 객체들을 모은 디렉토리
    - util 에 있는 객체 중, 외부 라이브러리에 의존도가 낮은 객체는 테스트 코드를 작성할 수 있다.

## 스크립트

```sh
# 빌드
./gradlew build

# production 프로필로 실행
java -jar -Dspring.profiles.active=production build/libs/application.jar

# API 문서 생성(http://localhost:8080/docs/index.html)
./gradlew createDocument

# 도커 컨테이너 생성
cd ./docker && sh docker-start.sh

# test 용도로 실행
# http://localhost:8080/h2-console 를 통해 콘솔 화면에 접근 가능
./gradlew bootRun --args='--spring.profiles.active=test'

# stage 용도로 실행
java -jar -Dspring.profiles.active=stage build/libs/application.jar

# 스프링 서버를 도커 컨테이너로 빌드
sh build-docker.sh
```

## 더미 데이터

- `src/main/resources/schema.sql` 에서 스키마를 정의한다.
- `src/main/resources/data.sql` 스키마에 맞는 더미 데이터를 제공한다.

## Production 실행시 필요한 환경변수

```sh
export SPRING_DATASOURCE_URL="jdbc:mariadb://localhost:12345/mariadb?autoReconnect=true&useUnicode=true&characterEncoding=UTF8MB4"
export SPRING_DATASOURCE_USERNAME="manager"
export SPRING_DATASOURCE_PASSWORD="managerPassword"
```

## Reference

- [Spring REST Docs 공식 문서](https://godekdls.github.io/Spring%20REST%20Docs/contents/)
- [우아한 형제들 기술 블로그: REST Docs 적용](https://techblog.woowahan.com/2597)
- [velog 블로그: Spring REST Docs 적용해보기](https://velog.io/@tmdgh0221/Spring-Rest-Docs-%EC%A0%81%EC%9A%A9%ED%95%B4%EB%B3%B4%EA%B8%B0#request-and-response-body)
- [velog 블로그: Spring REST Docs 살펴볼만한 기능들](https://velog.io/@dae-hwa/Spring-REST-Docs-%EC%82%B4%ED%8E%B4%EB%B3%BC%EB%A7%8C%ED%95%9C-%EA%B8%B0%EB%8A%A5%EB%93%A4)
- [스프링 시큐리티 - JSON으로 로그인처리 하기](https://ttl-blog.tistory.com/104)
- [Spring Security 에서 JWT 를 통한 인증/인가 수행하기](https://ppaksang.tistory.com/12)
- [REST API 로그인 만들기](https://willbfine.tistory.com/568)
- [log4j2 를 활용한 로깅 전략](https://kth990303.tistory.com/369)
- [Interceptor로 Request, Response body json 값 로깅하기](https://xzio.tistory.com/1999)
