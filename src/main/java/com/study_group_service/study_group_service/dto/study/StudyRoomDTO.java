package com.study_group_service.study_group_service.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StudyRoomDTO {
    private Long id;
    private String name;
    private Long studyRoomHostId;
    private Long categoriesId;
    private Long chatId;
    private LocalDateTime createdAt;
    private int peopleCount;
    private String rules;
    private String notification;
}
