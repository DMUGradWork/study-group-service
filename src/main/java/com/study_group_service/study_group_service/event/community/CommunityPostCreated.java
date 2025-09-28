package com.study_group_service.study_group_service.event.community;

import java.time.LocalDateTime;
import java.util.UUID;

public record CommunityPostCreated(
    UUID postId,              // 게시글 ID
    UUID authorId,            // 작성자 ID
    String title,             // 게시글 제목
    String content,           // 게시글 내용
    String category,          // 게시글 카테고리
    LocalDateTime createdAt   // 작성 시점
) {}
