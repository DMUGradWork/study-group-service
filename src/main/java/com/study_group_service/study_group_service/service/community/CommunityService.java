package com.study_group_service.study_group_service.service.community;

import com.study_group_service.study_group_service.dto.community.CommunityPostDTO;
import com.study_group_service.study_group_service.dto.community.CommunityCommentDTO;

import java.util.List;

public interface CommunityService {
    // 게시글
    CommunityPostDTO createPost(CommunityPostDTO dto);
    List<CommunityPostDTO> getAllPosts();
    CommunityPostDTO getPost(Long id);
    void deletePost(Long id);

    // 조회수 증가
    void increaseViews(Long postId);

    // 댓글
    CommunityCommentDTO createComment(Long postId, CommunityCommentDTO dto);
    List<CommunityCommentDTO> getComments(Long postId);
    void deleteComment(Long commentId);
} 