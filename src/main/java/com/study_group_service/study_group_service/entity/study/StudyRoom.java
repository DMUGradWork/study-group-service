package com.study_group_service.study_group_service.entity.study;

import com.study_group_service.study_group_service.entity.chat.ChatRoom;
import com.study_group_service.study_group_service.entity.user.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StudyRoom {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "study_room_id")
    private Long id;

    @Column(unique = true, nullable = false)
    private UUID uuid = UUID.randomUUID();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "study_room_host_id", nullable = true)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "categories_id", nullable = false)
    private StudyRoomCategory studyRoomCategory;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "chat_room_id")
    private ChatRoom chatRoom;

    private LocalDateTime created_at;

    @Column(name = "study_room_name")
    private String name;

    private int peopleCount;

    // 규칙 저장
    private String rules;

    // 공지 내용
    private String notification;
    private String password;
    private String imageUrl;

    // 방 소개
    private String description;

    // 참여자 목록
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "studyRoom", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<StudyRoomParticipant> participants = new ArrayList<>();

    public void updateRules(String rules) {
        this.rules = rules;
    }

    public void updateNotification(String notification) {
        this.notification = notification;
    }

    public void updateChatRoom(ChatRoom chatRoom) {
        this.chatRoom = chatRoom;
    }
}
