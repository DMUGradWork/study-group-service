package com.study_group_service.study_group_service.controller;

import com.study_group_service.study_group_service.dto.community.CommunityPostDTO;
import com.study_group_service.study_group_service.dto.community.CommunityCommentDTO;
import com.study_group_service.study_group_service.service.community.CommunityService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/community")
@RequiredArgsConstructor
public class CommunityController {
    private final CommunityService communityService;

    // 게시글
    @PostMapping
    public ResponseEntity<CommunityPostDTO> createPost(@RequestBody CommunityPostDTO dto) {
        return ResponseEntity.ok(communityService.createPost(dto));
    }

    @GetMapping
    public ResponseEntity<List<CommunityPostDTO>> getAllPosts() {
        return ResponseEntity.ok(communityService.getAllPosts());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CommunityPostDTO> getPost(@PathVariable Long id) {
        return ResponseEntity.ok(communityService.getPost(id));
    }

    @PatchMapping("/{id}/views")
    public ResponseEntity<Void> increaseViews(@PathVariable Long id) {
        communityService.increaseViews(id);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable Long id) {
        communityService.deletePost(id);
        return ResponseEntity.ok().build();
    }

    // 댓글
    @PostMapping("/{postId}/comments")
    public ResponseEntity<CommunityCommentDTO> createComment(@PathVariable Long postId, @RequestBody CommunityCommentDTO dto) {
        return ResponseEntity.ok(communityService.createComment(postId, dto));
    }

    @GetMapping("/{postId}/comments")
    public ResponseEntity<List<CommunityCommentDTO>> getComments(@PathVariable Long postId) {
        return ResponseEntity.ok(communityService.getComments(postId));
    }

    @DeleteMapping("/comments/{commentId}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long commentId) {
        communityService.deleteComment(commentId);
        return ResponseEntity.ok().build();
    }
} 