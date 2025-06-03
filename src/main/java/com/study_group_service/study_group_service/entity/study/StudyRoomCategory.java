package com.study_group_service.study_group_service.entity.study;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StudyRoomCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "categories_id")
    private Long id;

    @Column(name = "categories_name")
    private String name;

    @OneToMany(mappedBy = "studyRoomCategory")
    private List<StudyRoom> studyRooms = new ArrayList<>();
}
