package com.study_group_service.study_group_service.service.community;

import com.study_group_service.study_group_service.dto.community.CommunityPostDTO;
import com.study_group_service.study_group_service.dto.community.CommunityCommentDTO;
import com.study_group_service.study_group_service.entity.community.CommunityPost;
import com.study_group_service.study_group_service.entity.community.CommunityComment;
import com.study_group_service.study_group_service.event.community.CommunityCommentCreated;
import com.study_group_service.study_group_service.event.community.CommunityPostCreated;
import com.study_group_service.study_group_service.repository.community.CommunityPostRepository;
import com.study_group_service.study_group_service.repository.community.CommunityCommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommunityServiceImpl implements CommunityService {
    private final CommunityPostRepository postRepo;
    private final CommunityCommentRepository commentRepo;
    private final ApplicationEventPublisher eventPublisher;

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
        
        // 커뮤니티 게시글 생성 이벤트 발행
        CommunityPostCreated postCreatedEvent = new CommunityPostCreated(
                post.getUuid(),
                UUID.fromString(post.getAuthorId().toString()),
                post.getTitle(),
                post.getContent(),
                post.getCodeLanguage(),
                post.getCreatedAt()
        );
        eventPublisher.publishEvent(postCreatedEvent);
        
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
    public CommunityPostDTO getPostByUuid(UUID uuid) {
        CommunityPost post = postRepo.findByUuid(uuid).orElseThrow();
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
    public void deletePostByUuid(UUID uuid) {
        CommunityPost post = postRepo.findByUuid(uuid).orElseThrow();
        postRepo.deleteById(post.getId());
    }

    @Override
    public void increaseViews(Long postId) {
        CommunityPost post = postRepo.findById(postId).orElseThrow();
        post.setViews(post.getViews() + 1);
        postRepo.save(post);
    }

    @Override
    public void increaseViewsByUuid(UUID postUuid) {
        CommunityPost post = postRepo.findByUuid(postUuid).orElseThrow();
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
        
        // 커뮤니티 댓글 생성 이벤트 발행
        CommunityCommentCreated commentCreatedEvent = new CommunityCommentCreated(
                comment.getUuid(),
                post.getUuid(),
                UUID.fromString(comment.getAuthorId().toString()), // Long을 UUID로 변환
                comment.getContent(),
                null, // 부모 댓글 ID (대댓글이 아닌 경우)
                comment.getCreatedAt()
        );
        eventPublisher.publishEvent(commentCreatedEvent);
        
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
    public CommunityCommentDTO createCommentByUuid(UUID postUuid, CommunityCommentDTO dto) {
        CommunityPost post = postRepo.findByUuid(postUuid).orElseThrow();
        CommunityComment comment = CommunityComment.builder()
                .post(post)
                .content(dto.getContent())
                .authorId(dto.getAuthorId())
                .authorName(dto.getAuthorName())
                .codeLanguage(dto.getCodeLanguage())
                .createdAt(LocalDateTime.now())
                .build();
        comment = commentRepo.save(comment);
        
        // 커뮤니티 댓글 생성 이벤트 발행
        CommunityCommentCreated commentCreatedEvent = new CommunityCommentCreated(
                comment.getUuid(),
                post.getUuid(),
                UUID.fromString(comment.getAuthorId().toString()), // Long을 UUID로 변환
                comment.getContent(),
                null, // 부모 댓글 ID (대댓글이 아닌 경우)
                comment.getCreatedAt()
        );
        eventPublisher.publishEvent(commentCreatedEvent);
        
        return CommunityCommentDTO.builder()
                .id(comment.getId())
                .postId(post.getId())
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
    public List<CommunityCommentDTO> getCommentsByUuid(UUID postUuid) {
        CommunityPost post = postRepo.findByUuid(postUuid).orElseThrow();
        return commentRepo.findByPostId(post.getId()).stream().map(comment -> CommunityCommentDTO.builder()
                .id(comment.getId())
                .postId(post.getId())
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

    @Override
    public void deleteCommentByUuid(UUID commentUuid) {
        CommunityComment comment = commentRepo.findByUuid(commentUuid).orElseThrow();
        commentRepo.deleteById(comment.getId());
    }
} 