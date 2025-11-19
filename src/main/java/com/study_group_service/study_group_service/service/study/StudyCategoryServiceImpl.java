package com.study_group_service.study_group_service.service;

import com.study_group_service.study_group_service.dto.StudyRoomCategoryDTO;
import com.study_group_service.study_group_service.entity.study.StudyRoomCategory;
import com.study_group_service.study_group_service.exception.UserNotFoundException;
import com.study_group_service.study_group_service.mapper.StudyCategoryMapper;
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

    @Override
    @Transactional
    // 스터디 카테고리 생성
    public StudyRoomCategoryDTO setStudyCategory(StudyRoomCategoryDTO studyRoomCategoryDTO) {
        StudyRoomCategory studyRoomCategory = studyCategoryMapper.toEntity(studyRoomCategoryDTO);
        StudyRoomCategory saved = studyCategoryJpaRepository.save(studyRoomCategory);

        return studyCategoryMapper.toDto(saved);
    }


    @Override
    // 스터디 카테고리 조회
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
