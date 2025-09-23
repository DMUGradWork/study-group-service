package com.study_group_service.study_group_service.service.community;

import com.study_group_service.study_group_service.dto.community.CommunityPostDTO;
import com.study_group_service.study_group_service.dto.community.CommunityCommentDTO;
import com.study_group_service.study_group_service.entity.community.CommunityPost;
import com.study_group_service.study_group_service.entity.community.CommunityComment;
import com.study_group_service.study_group_service.repository.community.CommunityPostRepository;
import com.study_group_service.study_group_service.repository.community.CommunityCommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommunityServiceImpl implements CommunityService {
    private final CommunityPostRepository postRepo;
    private final CommunityCommentRepository commentRepo;

    // 게시글
    @Override
    public CommunityPostDTO createPost(CommunityPostDTO dto) {
        CommunityPost post = CommunityPost.builder()
                .title(dto.getTitle())
                .content(dto.getContent())
                .authorId(dto.getAuthorId())
                .authorName(dto.getAuthorName())
                .codeLanguage(dto.getCodeLanguage())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
        post = postRepo.save(post);
        dto.setId(post.getId());
        dto.setCreatedAt(post.getCreatedAt());
        dto.setUpdatedAt(post.getUpdatedAt());
        return dto;
    }

    @Override
    public List<CommunityPostDTO> getAllPosts() {
        return postRepo.findAll().stream().map(post -> {
            int commentsCount = commentRepo.countByPostId(post.getId());
            return CommunityPostDTO.builder()
                .id(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .authorId(post.getAuthorId())
                .authorName(post.getAuthorName())
                .codeLanguage(post.getCodeLanguage())
                .createdAt(post.getCreatedAt())
                .updatedAt(post.getUpdatedAt())
                .views(post.getViews())
                .commentsCount(commentsCount)
                .build();
        }).collect(Collectors.toList());
    }

    @Override
    public CommunityPostDTO getPost(Long id) {
        CommunityPost post = postRepo.findById(id).orElseThrow();
        int commentsCount = commentRepo.countByPostId(post.getId());
        return CommunityPostDTO.builder()
                .id(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .authorId(post.getAuthorId())
                .authorName(post.getAuthorName())
                .codeLanguage(post.getCodeLanguage())
                .createdAt(post.getCreatedAt())
                .updatedAt(post.getUpdatedAt())
                .views(post.getViews())
                .commentsCount(commentsCount)
                .build();
    }

    @Override
    public void deletePost(Long id) {
        postRepo.deleteById(id);
    }

    @Override
    public void increaseViews(Long postId) {
        CommunityPost post = postRepo.findById(postId).orElseThrow();
        post.setViews(post.getViews() + 1);
        postRepo.save(post);
    }

    // 댓글
    @Override
    public CommunityCommentDTO createComment(Long postId, CommunityCommentDTO dto) {
        CommunityPost post = postRepo.findById(postId).orElseThrow();
        CommunityComment comment = CommunityComment.builder()
                .post(post)
                .content(dto.getContent())
                .authorId(dto.getAuthorId())
                .authorName(dto.getAuthorName())
                .codeLanguage(dto.getCodeLanguage())
                .createdAt(LocalDateTime.now())
                .build();
        comment = commentRepo.save(comment);
        return CommunityCommentDTO.builder()
                .id(comment.getId())
                .postId(postId)
                .content(comment.getContent())
                .authorId(comment.getAuthorId())
                .authorName(comment.getAuthorName())
                .codeLanguage(comment.getCodeLanguage())
                .createdAt(comment.getCreatedAt())
                .build();

    }

    @Override
    public List<CommunityCommentDTO> getComments(Long postId) {
        return commentRepo.findByPostId(postId).stream().map(comment -> CommunityCommentDTO.builder()
                .id(comment.getId())
                .postId(postId)
                .content(comment.getContent())
                .authorId(comment.getAuthorId())
                .authorName(comment.getAuthorName())
                .codeLanguage(comment.getCodeLanguage())
                .createdAt(comment.getCreatedAt())
                .build()).collect(Collectors.toList());
    }

    @Override
    public void deleteComment(Long commentId) {
        commentRepo.deleteById(commentId);
    }
} 