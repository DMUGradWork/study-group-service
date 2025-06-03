package com.study_group_service.study_group_service.entity.study;

import com.study_group_service.study_group_service.entity.chat.ChatRoom;
import com.study_group_service.study_group_service.entity.user.Users;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StudyRoom {
    @Id
    @GeneratedValue
    @Column(name = "study_room_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "study_room_host_id", nullable = true)
    private Users user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "categories_id", nullable = false)
    private StudyRoomCategory studyRoomCategory;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chat_room_id")
    private ChatRoom chatRoom;

    private LocalDateTime created_at;

    @Column(name = "study_room_name")
    private String name;

    // 최대 10명
    private int peopleCount;

    // 규칙 저장
    private String rules;

    // 공지 내용
    private String notification;

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
