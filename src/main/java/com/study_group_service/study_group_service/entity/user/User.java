package com.study_group_service.study_group_service.entity.user;

import com.study_group_service.study_group_service.enums.Role;
import com.study_group_service.study_group_service.entity.study.StudyRoomMeeting;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    private String email;
    private String password;
    private String name;
    private String phone;
    private LocalDateTime created_at = LocalDateTime.now();
    private Long consecutiveAttendance;
    private Long roomCount;
    
    private UUID uuid;

    private LocalDate lastAttendanceDate;

    @Enumerated(EnumType.ORDINAL)
    private Role role = Role.USER;

    @ManyToMany(mappedBy = "participants")
    private Set<StudyRoomMeeting> meetings = new HashSet<>();

    public void promoteToAdmin() {
        this.role = Role.ADMIN;
    }

    public void updateDays() {
        this.consecutiveAttendance++;
    }

    public void checkAttendance(LocalDate today) {
        if (lastAttendanceDate == null || !lastAttendanceDate.plusDays(1).equals(today)) {
            consecutiveAttendance = 0L;
        } else {
            consecutiveAttendance++;
        }
        lastAttendanceDate = today;
    }
}
