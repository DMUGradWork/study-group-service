package com.study_group_service.study_group_service.domain.study;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StudyRoomCategory {
    @Id @GeneratedValue
    @Column(name = "categories_id")
    private Long id;

    @Column(name = "categories_name")
    private String name;
}
