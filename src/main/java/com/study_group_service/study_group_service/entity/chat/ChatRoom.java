package com.study_group_service.study_group_service.entity.chat;

import com.study_group_service.study_group_service.entity.study.StudyRoom;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChatRoom {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "chat_room_id")
    private Long id;

    @Column(name = "chat_room_name")
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "study_room_id", referencedColumnName = "study_room_id")
    private StudyRoom studyRoom;

    @OneToMany(mappedBy = "chatRoom", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ChatRoomMessages> messages = new ArrayList<>();
}
