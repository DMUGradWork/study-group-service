package com.study_group_service.study_group_service.listener;

import com.study_group_service.study_group_service.event.community.CommunityCommentCreated;
import com.study_group_service.study_group_service.event.community.CommunityPostCreated;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * 커뮤니티 관련 이벤트 리스너
 * 
 * 게시글 생성, 댓글 생성 등의 이벤트를 처리합니다.
 */
@Slf4j
@Component
public class CommunityEventListener {

    /**
     * 커뮤니티 게시글 생성 이벤트 처리
     */
    @EventListener
    public void handleCommunityPostCreated(CommunityPostCreated event) {
        log.info("커뮤니티 게시글 생성 이벤트 처리: postId={}, authorId={}, title={}, category={}, createdAt={}", 
                event.postId(), event.authorId(), event.title(), 
                event.category(), event.createdAt());
        
        // 여기에 게시글 생성 시 필요한 추가 로직을 구현할 수 있습니다
        // 예: 작성자에게 알림, 게시글 목록 업데이트, 통계 업데이트 등
    }

    /**
     * 커뮤니티 댓글 생성 이벤트 처리
     */
    @EventListener
    public void handleCommunityCommentCreated(CommunityCommentCreated event) {
        log.info("커뮤니티 댓글 생성 이벤트 처리: commentId={}, postId={}, authorId={}, parentCommentId={}, createdAt={}", 
                event.commentId(), event.postId(), event.authorId(), 
                event.parentCommentId(), event.createdAt());
        
        // 여기에 댓글 생성 시 필요한 추가 로직을 구현할 수 있습니다
        // 예: 게시글 작성자에게 알림, 댓글 수 업데이트, 실시간 댓글 알림 등
    }
}
