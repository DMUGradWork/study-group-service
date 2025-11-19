# 멀티 스테이지 빌드
FROM gradle:8.5-jdk21 AS builder

# 작업 디렉토리 설정
WORKDIR /app

# Gradle 파일들 복사
COPY build.gradle settings.gradle ./
COPY gradle/ gradle/

# 의존성 다운로드 (캐시 최적화)
RUN gradle dependencies --no-daemon

# 소스 코드 복사
COPY src/ src/

# 애플리케이션 빌드
RUN gradle build --no-daemon -x test

# 실행 스테이지
FROM eclipse-temurin:21-jre-alpine

# 작업 디렉토리 설정
WORKDIR /app

# 빌드된 JAR 파일 복사
COPY --from=builder /app/build/libs/*.jar app.jar

# 포트 노출
EXPOSE 8080

# 애플리케이션 실행
ENTRYPOINT ["java", "-jar", "app.jar"]
