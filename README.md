# GrewMeet 스터디 서비스

## 📋 프로젝트 개요

GrewMeet은 스터디 그룹 관리와 커뮤니티 기능을 제공하는 Spring Boot 기반의 웹 서비스입니다. 사용자들이 스터디룸을 생성하고 참여하며, 실시간 채팅과 투표 시스템을 통해 효과적인 스터디를 진행할 수 있도록 지원합니다.

## 🏗️ 기술 스택

- **Backend**: Spring Boot 3.x, Spring Data JPA, Spring Security
- **Database**: MySQL, Redis (채팅용 Pub/Sub)
- **Build Tool**: Gradle
- **Java Version**: 17+
- **Event System**: Spring ApplicationEventPublisher

## 🎯 핵심 기능

### 1. 사용자 관리 (User Management)
- 회원가입/로그인
- 프로필 관리
- 출석 체크 시스템
- 관리자 권한 관리

### 2. 스터디룸 관리 (Study Room Management)
- 스터디룸 생성/삭제
- 참여자 관리
- 규칙 및 공지사항 설정
- 카테고리별 분류

### 3. 실시간 채팅 (Real-time Chat)
- Redis Pub/Sub 기반 실시간 메시징
- 이미지 전송 지원
- 채팅방별 메시지 관리

### 4. 커뮤니티 (Community)
- 게시글 작성/조회/삭제
- 댓글 시스템
- 조회수 추적

### 5. 모임 관리 (Meeting Management)
- 모임 일정 생성
- 찬반 투표 시스템
- 참여자 승인/거부

### 6. 이벤트 시스템 (Event System)
- Spring ApplicationEventPublisher 기반
- 비동기 이벤트 처리
- 시스템 상태 추적

---

## 🚀 API 문서

### 👤 사용자 관리 API (`/api/user`)

#### 사용자 조회
- `GET /api/user` - 모든 사용자 조회
- `GET /api/user/{id}` - ID로 사용자 조회
- `GET /api/user/uuid/{uuid}` - UUID로 사용자 조회
- `GET /api/user/email/{email}` - 이메일로 사용자 조회

#### 사용자 관리
- `POST /api/user` - 회원가입
- `PUT /api/user/uuid/{uuid}/profile` - 프로필 업데이트
- `DELETE /api/user/{id}` - 사용자 삭제 (ID)
- `DELETE /api/user/uuid/{uuid}` - 사용자 삭제 (UUID)

#### 권한 관리
- `PUT /api/user/{email}` - 관리자로 권한 변경

#### 출석 관리
- `POST /api/user/{id}/attendance` - 출석 체크 (ID)
- `POST /api/user/uuid/{uuid}/attendance` - 출석 체크 (UUID)

#### 방 참여 관리
- `POST /api/user/{id}/join-room` - 방 참여 (ID)
- `POST /api/user/uuid/{uuid}/join-room` - 방 참여 (UUID)
- `POST /api/user/{id}/leave-room` - 방 퇴장 (ID)
- `POST /api/user/uuid/{uuid}/leave-room` - 방 퇴장 (UUID)

#### 로그인
- `POST /api/user/login` - 이메일/비밀번호 로그인

### 🏠 스터디룸 관리 API (`/api/study`)

#### 스터디룸 조회
- `GET /api/study` - 모든 스터디룸 조회
- `GET /api/study/{studyName}` - 이름으로 스터디룸 조회
- `GET /api/study/chat/{studyRoomId}` - 채팅방으로 스터디룸 조회

#### 스터디룸 관리
- `POST /api/study` - 스터디룸 생성
- `DELETE /api/study/rooms/{studyRoomId}` - 스터디룸 삭제

#### 참여자 관리
- `POST /api/study/rooms/join` - 스터디룸 참여
- `GET /api/study/{studyRoomId}/users` - 스터디룸 참여자 조회
- `GET /api/study/{userId}/rooms` - 사용자의 스터디룸 목록
- `DELETE /api/study/{studyRoomId}/{userId}` - 참여자 삭제

#### 규칙 및 공지 관리
- `PATCH /api/study/rule/{studyRoomId}` - 스터디룸 규칙 설정
- `DELETE /api/study/rule/{studyRoomId}` - 스터디룸 규칙 삭제
- `PATCH /api/study/notification/{studyRoomId}` - 공지사항 설정
- `DELETE /api/study/notification/{studyRoomId}` - 공지사항 삭제

#### 카테고리 관리
- `GET /api/study/category` - 모든 카테고리 조회
- `POST /api/study/category` - 카테고리 생성
- `DELETE /api/study/category/{categoryId}` - 카테고리 삭제

#### 모임 관리
- `POST /api/study/meeting` - 모임 생성
- `GET /api/study/rooms/{roomId}/meeting` - 방의 모임 조회

#### 투표 시스템
- `POST /api/study/meeting/vote` - 모임 투표
- `GET /api/study/meeting/{meetingId}/votes` - 투표 결과 조회
- `GET /api/study/meeting/{meetingId}/approved` - 투표 승인 여부 확인

#### 파일 업로드
- `POST /api/study/upload-image` - 이미지 업로드

### 💬 커뮤니티 API (`/api/community`)

#### 게시글 관리
- `POST /api/community` - 게시글 작성
- `GET /api/community` - 모든 게시글 조회
- `GET /api/community/{id}` - 게시글 상세 조회
- `PATCH /api/community/{id}/views` - 조회수 증가
- `DELETE /api/community/{id}` - 게시글 삭제

#### 댓글 관리
- `POST /api/community/{postId}/comments` - 댓글 작성
- `GET /api/community/{postId}/comments` - 댓글 목록 조회
- `DELETE /api/community/comments/{commentId}` - 댓글 삭제

### 💬 채팅 API (`/api/chat`)

#### 메시지 관리
- `POST /api/chat/send` - 메시지 전송 (Redis Pub/Sub)
- `GET /api/chat/rooms/{chatRoomId}/messages` - 채팅방 메시지 조회
- `GET /api/chat/rooms/{chatRoomId}/all` - 채팅방 모든 메시지 조회

#### 채팅방 관리
- `GET /api/chat` - 모든 채팅방 조회

---

## 🎯 이벤트 시스템

### 📋 구현된 이벤트들

#### 👤 사용자 이벤트
- **UserRegistered**: 사용자 등록 시 발행
- **UserProfileUpdated**: 사용자 프로필 업데이트 시 발행

#### 🏠 스터디룸 이벤트  
- **StudyRoomCreated**: 스터디룸 생성 시 발행
- **StudyRoomJoined**: 스터디룸 참여 시 발행
- **StudyRoomLeft**: 스터디룸 퇴장 시 발행

#### 💬 커뮤니티 이벤트
- **CommunityPostCreated**: 게시글 생성 시 발행
- **CommunityCommentCreated**: 댓글 생성 시 발행

#### 🗳️ 투표 이벤트
- **MeetingVoteCreated**: 투표 생성 시 발행
- **MeetingVoteParticipated**: 투표 참여 시 발행

#### 📅 모임 이벤트
- **StudyMeetingCancelled**: 모임 취소 시 발행
- **StudyMeetingEventParticipationRegistered**: 모임 참여 등록 시 발행
- **StudyMeetingParticipationCancelled**: 모임 참여 취소 시 발행
- **StudyMeetingParticipationCompleted**: 모임 참여 완료 시 발행
- **StudyMeetingRescheduled**: 모임 일정 변경 시 발행

### 📝 이벤트 리스너

각 이벤트는 해당하는 리스너에서 처리됩니다:
- `UserEventListener` - 사용자 관련 이벤트 처리
- `StudyRoomEventListener` - 스터디룸 관련 이벤트 처리  
- `CommunityEventListener` - 커뮤니티 관련 이벤트 처리

---

## 🗄️ 데이터베이스 구조

### 핵심 엔티티

#### User (사용자)
- `id`: 기본키
- `uuid`: 고유 식별자
- `email`: 이메일
- `password`: 비밀번호
- `name`: 이름
- `phone`: 전화번호
- `role`: 역할 (USER, ADMIN)
- `consecutiveAttendance`: 연속 출석일
- `lastAttendanceDate`: 마지막 출석일

#### StudyRoom (스터디룸)
- `id`: 기본키
- `uuid`: 고유 식별자
- `name`: 스터디룸 이름
- `description`: 설명
- `rules`: 규칙
- `notification`: 공지사항
- `password`: 비밀번호
- `peopleCount`: 인원 수
- `imageUrl`: 이미지 URL

#### CommunityPost (커뮤니티 게시글)
- `id`: 기본키
- `uuid`: 고유 식별자
- `title`: 제목
- `content`: 내용
- `authorId`: 작성자 ID
- `authorName`: 작성자 이름
- `codeLanguage`: 프로그래밍 언어
- `views`: 조회수

#### ChatRoom (채팅방)
- `id`: 기본키
- `uuid`: 고유 식별자
- `name`: 채팅방 이름
- `studyRoom`: 연결된 스터디룸

#### StudyRoomMeeting (모임)
- `id`: 기본키
- `title`: 모임 제목
- `duration`: 진행 시간
- `meetingTime`: 모임 시간
- `studyRoom`: 연결된 스터디룸

#### MeetingVote (투표)
- `id`: 기본키
- `meeting`: 연결된 모임
- `user`: 투표한 사용자
- `vote`: 투표 내용 (yes/no)

---

## 🔧 설정 및 실행

### 환경 요구사항
- Java 17+
- MySQL 8.0+
- Redis 6.0+

### 애플리케이션 설정
```yaml
# application.yml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/study_group_db
    username: your_username
    password: your_password
  redis:
    host: localhost
    port: 6379
```

### 실행 방법
```bash
# 프로젝트 빌드
./gradlew build

# 애플리케이션 실행
./gradlew bootRun
```

---

## 📊 주요 기능 상세

### 1. 출석 체크 시스템
- 연속 출석일 추적
- 마지막 출석일 기반 자동 계산
- 출석 이벤트 발행

### 2. 실시간 채팅
- Redis Pub/Sub 기반 실시간 메시징
- 채팅방별 메시지 저장
- 이미지 전송 지원

### 3. 투표 시스템
- 모임 찬반 투표
- 과반수 기반 자동 승인
- 중복 투표 방지

### 4. 파일 업로드
- 이미지 업로드 지원
- 업로드된 파일 URL 반환
- 로컬 파일 시스템 저장

### 5. 이벤트 기반 아키텍처
- Spring ApplicationEventPublisher 활용
- 비동기 이벤트 처리
- 시스템 상태 추적 및 로깅

---