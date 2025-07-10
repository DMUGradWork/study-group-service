package com.study_group_service.study_group_service.repository.community;

import com.study_group_service.study_group_service.entity.community.CommunityComment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommunityCommentRepository extends JpaRepository<CommunityComment, Long> {
    List<CommunityComment> findByPostId(Long postId);
    int countByPostId(Long postId);
} 