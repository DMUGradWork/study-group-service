package com.study_group_service.study_group_service.dto.study;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class StudyRoomDTO {
    private Long id;
    private String name;
    private Long studyRoomHostId;
    private String hostName;
    private Long categoriesId;
    private Long chatId;
    private LocalDateTime created_at;
    private int peopleCount;
    private String rules;
    private String notification;
    private String password;
    private String imageUrl;
    private String description;
}
