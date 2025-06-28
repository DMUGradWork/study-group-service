package com.study_group_service.study_group_service.service.study;

import com.study_group_service.study_group_service.dto.study.StudyRoomCategoryDTO;
import java.util.List;

public interface StudyCategoryService {
    List<StudyRoomCategoryDTO> getStudyCategory();
    StudyRoomCategoryDTO setStudyCategory(StudyRoomCategoryDTO studyRoomCategoryDTO);
    void deleteStudyCategory(Long id);
}
