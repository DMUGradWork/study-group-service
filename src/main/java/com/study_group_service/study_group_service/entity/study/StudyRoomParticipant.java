package com.study_group_service.study_group_service.domain.study;

import com.study_group_service.study_group_service.domain.user.Users;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StudyRoomParticipant {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private Users user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "study_room_id")
    private StudyRoom studyRoom;

    private LocalDateTime joinedAt;
}
