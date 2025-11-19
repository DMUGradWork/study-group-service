package com.study_group_service.study_group_service.domain.chat;

import com.study_group_service.study_group_service.domain.study.StudyRoom;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChatRoom {
    @Id
    @GeneratedValue
    @Column(name = "chat_room_id")
    private Long id;

    @Column(name = "chat_room_name")
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "study_room_id", referencedColumnName = "study_room_id")
    private StudyRoom studyRoom;
}
