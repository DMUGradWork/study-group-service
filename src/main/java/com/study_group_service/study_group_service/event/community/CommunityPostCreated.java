package com.study_group_service.study_group_service.event.community;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * 커뮤니티 게시글 생성 이벤트
 *
 * 커뮤니티에 새로운 게시글이 작성되었을 때 발생하는 이벤트입니다.
 * 이 이벤트는 게시글 생성을 다른 서비스들에게 알리고,
 * 관련된 알림과 처리를 수행할 수 있도록 합니다.
 *
 * 주요 활용 사례:
 * - 게시글 작성 알림 발송
 * - 카테고리별 게시글 통계 업데이트
 * - 작성자 활동 이력 추적
 * - 게시글 검색 인덱스 업데이트
 */
public record CommunityPostCreated(
    UUID postId,              // 게시글 ID
    UUID authorId,            // 작성자 ID
    String title,             // 게시글 제목
    String content,           // 게시글 내용
    String category,          // 게시글 카테고리
    LocalDateTime createdAt   // 작성 시점
) {}
