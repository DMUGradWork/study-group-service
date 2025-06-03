package com.study_group_service.study_group_service.mapper.study;

import com.study_group_service.study_group_service.dto.study.StudyRoomCategoryDTO;
import com.study_group_service.study_group_service.entity.study.StudyRoomCategory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Component
public class StudyCategoryMapper {

    public StudyRoomCategoryDTO toDto(StudyRoomCategory studyRoomCategory) {
        return StudyRoomCategoryDTO.builder()
                .id(studyRoomCategory.getId())
                .name(studyRoomCategory.getName())
                .build();
    }

    public StudyRoomCategory toEntity(StudyRoomCategoryDTO studyRoomCategoryDTO) {
        return StudyRoomCategory.builder()
                .name(studyRoomCategoryDTO.getName())
                .build();
    }

    public List<StudyRoomCategoryDTO> categoryToDto(List<StudyRoomCategory> studyRoomCategory) {
        return studyRoomCategory.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }
}
