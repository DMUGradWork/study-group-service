package com.study_group_service.study_group_service.service.community;

import com.study_group_service.study_group_service.dto.community.CommunityPostDTO;
import com.study_group_service.study_group_service.dto.community.CommunityCommentDTO;

import java.util.List;
import java.util.UUID;

public interface CommunityService {
    // 게시글
    CommunityPostDTO createPost(CommunityPostDTO dto);
    List<CommunityPostDTO> getAllPosts();
    CommunityPostDTO getPost(Long id);
    CommunityPostDTO getPostByUuid(UUID uuid);
    void deletePost(Long id);
    void deletePostByUuid(UUID uuid);

    // 조회수 증가
    void increaseViews(Long postId);
    void increaseViewsByUuid(UUID postUuid);

    // 댓글
    CommunityCommentDTO createComment(Long postId, CommunityCommentDTO dto);
    CommunityCommentDTO createCommentByUuid(UUID postUuid, CommunityCommentDTO dto);
    List<CommunityCommentDTO> getComments(Long postId);
    List<CommunityCommentDTO> getCommentsByUuid(UUID postUuid);
    void deleteComment(Long commentId);
    void deleteCommentByUuid(UUID commentUuid);
} 