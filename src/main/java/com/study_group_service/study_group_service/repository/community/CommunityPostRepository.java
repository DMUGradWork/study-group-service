package com.study_group_service.study_group_service.repository.community;

import com.study_group_service.study_group_service.entity.community.CommunityPost;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommunityPostRepository extends JpaRepository<CommunityPost, Long> {
} 