package com.study_group_service.study_group_service.service.study;

import com.study_group_service.study_group_service.dto.study.StudyRoomCategoryDTO;
import com.study_group_service.study_group_service.entity.study.StudyRoomCategory;
import com.study_group_service.study_group_service.exception.user.UserNotFoundException;
import com.study_group_service.study_group_service.mapper.study.StudyCategoryMapper;
import com.study_group_service.study_group_service.message.ErrorMessage;
import com.study_group_service.study_group_service.repository.study.StudyCategoryJpaRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StudyCategoryServiceImpl implements StudyCategoryService {

    private final StudyCategoryJpaRepository studyCategoryJpaRepository;
    private final ErrorMessage errorMessage;
    private final StudyCategoryMapper studyCategoryMapper;

    // 스터디 카테고리 생성
    @Override
    @Transactional
    public StudyRoomCategoryDTO setStudyCategory(StudyRoomCategoryDTO studyRoomCategoryDTO) {
        StudyRoomCategory studyRoomCategory = studyCategoryMapper.toEntity(studyRoomCategoryDTO);
        StudyRoomCategory saved = studyCategoryJpaRepository.save(studyRoomCategory);

        return studyCategoryMapper.toDto(saved);
    }

    // 스터디 카테고리 조회
    @Override
    @Transactional
    public List<StudyRoomCategoryDTO> getStudyCategory() {
        List<StudyRoomCategory> categories = studyCategoryJpaRepository.findAll();

        if (categories.isEmpty()) {
            throw new UserNotFoundException(errorMessage.showNoCategoryMessage());
        }

        return studyCategoryMapper.categoryToDto(categories);
    }

    // 스터디 카테고리 삭제
    @Override
    @Transactional
    public void deleteStudyCategory(Long id) {
        StudyRoomCategory category = studyCategoryJpaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(errorMessage.showNoCategoryMessage()));

        studyCategoryJpaRepository.delete(category);
    }
}
