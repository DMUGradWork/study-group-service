package com.study_group_service.study_group_service.domain.study;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class StudyRoomMeeting {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "study_room_id", nullable = false)
    private StudyRoom studyRoom;

    private LocalDateTime meetingTime;  // 모임 시간
}
