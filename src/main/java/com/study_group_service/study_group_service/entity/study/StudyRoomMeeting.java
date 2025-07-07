package com.study_group_service.study_group_service.entity.study;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.study_group_service.study_group_service.entity.user.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "studyRoom"})
@Entity
@Table(
    uniqueConstraints = {
        @UniqueConstraint(columnNames = {"study_room_id"})
    }
)
@Getter
@Setter
public class StudyRoomMeeting {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "study_room_id", nullable = false)
    private StudyRoom studyRoom;

    private LocalDateTime meetingTime;

    private String title;
    private String duration;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "user_meeting",
        joinColumns = @JoinColumn(name = "meeting_id"),
        inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private Set<User> participants = new HashSet<>();
}
