# OpenJDK 17
FROM openjdk:17

# 작업 디렉터리 설정
WORKDIR /app

# 빌드된 유레카 JAR 복사
COPY build/libs/eureka.jar eureka.jar

# 유레카 서버 실행
ENTRYPOINT ["java", "-jar", "eureka.jar"]