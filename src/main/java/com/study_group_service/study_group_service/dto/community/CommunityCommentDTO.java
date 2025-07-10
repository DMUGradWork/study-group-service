package com.study_group_service.study_group_service.dto.community;

import lombok.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommunityCommentDTO {
    private Long id;
    private Long postId;
    private String content;
    private Long authorId;
    private String authorName;
    private String codeLanguage;
    private LocalDateTime createdAt;
} 