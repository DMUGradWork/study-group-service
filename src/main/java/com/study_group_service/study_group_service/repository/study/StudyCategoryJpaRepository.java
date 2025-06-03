package com.study_group_service.study_group_service.repository.study;

import com.study_group_service.study_group_service.entity.study.StudyRoomCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudyCategoryJpaRepository extends JpaRepository<StudyRoomCategory, Long> {
}
