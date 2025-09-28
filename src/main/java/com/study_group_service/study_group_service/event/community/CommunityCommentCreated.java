package com.study_group_service.study_group_service.event.community;

import java.time.LocalDateTime;
import java.util.UUID;

public record CommunityCommentCreated(
    UUID commentId,           // 댓글 ID
    UUID postId,              // 게시글 ID
    UUID authorId,            // 댓글 작성자 ID
    String content,           // 댓글 내용
    UUID parentCommentId,     // 부모 댓글 ID (대댓글인 경우)
    LocalDateTime createdAt   // 작성 시점
) {}
