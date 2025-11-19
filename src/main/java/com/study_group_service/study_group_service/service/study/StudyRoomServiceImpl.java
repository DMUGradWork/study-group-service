package com.study_group_service.study_group_service.service;

import com.study_group_service.study_group_service.dto.StudyRoomDTO;
import com.study_group_service.study_group_service.dto.StudyRoomParticipantDTO;
import com.study_group_service.study_group_service.entity.chat.ChatRoom;
import com.study_group_service.study_group_service.entity.study.StudyRoomCategory;
import com.study_group_service.study_group_service.entity.study.StudyRoom;
import com.study_group_service.study_group_service.entity.study.StudyRoomParticipant;
import com.study_group_service.study_group_service.entity.user.Users;
import com.study_group_service.study_group_service.exception.StudyRoomNotFoundException;
import com.study_group_service.study_group_service.exception.UserNotFoundException;
import com.study_group_service.study_group_service.mapper.StudyRoomMapper;
import com.study_group_service.study_group_service.mapper.StudyRoomParticipantMapper;
import com.study_group_service.study_group_service.message.ErrorMessage;
import com.study_group_service.study_group_service.repository.*;
import com.study_group_service.study_group_service.repository.study.StudyCategoryJpaRepository;
import com.study_group_service.study_group_service.repository.study.StudyRoomParticipantJpaRepository;
import com.study_group_service.study_group_service.repository.study.StudyRoomJpaRepository;
import com.study_group_service.study_group_service.repository.users.UsersJpaRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StudyRoomServiceImpl implements StudyRoomService {
    private final StudyRoomJpaRepository studyRoomJpaRepository;
    private final ChatRoomJpaRepository chatRoomJpaRepository;
    private final UsersJpaRepository usersJpaRepository;
    private final StudyCategoryJpaRepository studyCategoryJpaRepository;
    private final ErrorMessage errorMessage;
    private final StudyRoomMapper studyRoomMapper;
    private final StudyRoomParticipantMapper studyRoomParticipantMapper;
    private final StudyRoomParticipantJpaRepository studyRoomParticipantJpaRepository;

    // 모든 스터디 조회
    @Override
    @Transactional
    public List<StudyRoomDTO> getStudyRooms() {
        List<StudyRoom> studyRooms = studyRoomJpaRepository.findAll();

        if (studyRooms.isEmpty()) {
            throw new UserNotFoundException(errorMessage.showNoUserMessage());
        }

        return studyRoomMapper.studyRoomFromDto(studyRooms);
    }

    // 특정 이름을 가진 스터디룸 검색
    @Override
    @Transactional
    public StudyRoomDTO getStudyRoom(String name) {
        StudyRoom studyRoom = studyRoomJpaRepository.findByName(name)
                .orElseThrow(() -> new StudyRoomNotFoundException(errorMessage.showNoStudyRoomMessage()));

        return studyRoomMapper.toDto(studyRoom);
    }

    // 스터디룸의 채팅룸(ID) 조회
    @Override
    @Transactional
    public StudyRoomDTO findByChatRoomId(Long chatRoomId) {
        StudyRoom room = studyRoomJpaRepository.findByChatRoomId(chatRoomId)
                .orElseThrow(() -> new StudyRoomNotFoundException(errorMessage.showNoStudyRoomMessage()));

        return studyRoomMapper.toDto(room);
    }

    // 스터디룸 + 채팅방 생성
    @Override
    @Transactional
    public StudyRoomDTO setStudyRoom(StudyRoomDTO studyRoomDTO) {
        Users user = usersJpaRepository.findById(studyRoomDTO.getStudyRoomHostId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 유저입니다."));
        StudyRoomCategory category = studyCategoryJpaRepository.findById(studyRoomDTO.getCategoriesId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 카테고리입니다."));

        StudyRoom studyRoom = studyRoomMapper.toEntity(studyRoomDTO, user, category);
        studyRoomJpaRepository.save(studyRoom);

        ChatRoom chatRoom = ChatRoom.builder()
                .name(studyRoom.getName())
                .studyRoom(studyRoom)
                .build();
        chatRoomJpaRepository.save(chatRoom);

        studyRoom.setChatRoom(chatRoom);
        studyRoomJpaRepository.save(studyRoom);

        return studyRoomMapper.toDto(studyRoom);
    }

    // 스터디룸 삭제
    @Override
    @Transactional
    public void deleteStudyRoom(Long id) {
        StudyRoom room = studyRoomJpaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("스터디룸이 존재하지 않습니다. ID: " + id));

        studyRoomJpaRepository.deleteById(room);
    }

    // 스터디룸 규칙 설정
    @Override
    @Transactional
    public StudyRoomDTO setStudyRoomRules(StudyRoomDTO studyRoomDTO) {
        Long roomId = studyRoomDTO.getId();
        String rules = studyRoomDTO.getRules();

        StudyRoom studyRoom = studyRoomJpaRepository.findById(roomId)
                .orElseThrow(() -> new EntityNotFoundException("스터디룸을 찾을 수 없습니다. ID: " + roomId));

        studyRoom.setRules(rules);

        StudyRoom saved = studyRoomJpaRepository.save(studyRoom);
        return studyRoomMapper.toDto(saved);
    }

    // 스터디룸 공지사항 설정
    @Override
    @Transactional
    public StudyRoomDTO setStudyRoomNotification(StudyRoomDTO studyRoomDTO) {
        Long roomId = studyRoomDTO.getId();
        String notification = studyRoomDTO.getNotification();

        StudyRoom studyRoom = studyRoomJpaRepository.findById(roomId)
                .orElseThrow(() -> new EntityNotFoundException("스터디룸을 찾을 수 없습니다. ID: " + roomId));

        studyRoom.setNotification(notification);

        StudyRoom saved = studyRoomJpaRepository.save(studyRoom);
        return studyRoomMapper.toDto(saved);
    }

    // 스터디룸 공지사항 삭제
    @Override
    @Transactional
    public void deleteNotification(Long id) {
        StudyRoom room = studyRoomJpaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("스터디룸이 존재하지 않습니다. ID: " + id));

        room.setNotification(null);
        studyRoomJpaRepository.save(room);
    }

    // 스터디룸 규칙 삭제
    @Override
    @Transactional
    public void deleteRules(Long id) {
        StudyRoom room = studyRoomJpaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("스터디룸이 존재하지 않습니다. ID: " + id));

        room.setRules(null);
        studyRoomJpaRepository.save(room);
    }

    // 참가 유저 삭제
    @Override
    @Transactional
    public void deleteStudyRoomParticipants(Long studyRoomId, Long userId) {
        StudyRoomParticipant studyRoomParticipant = studyRoomParticipantJpaRepository
                .findByUserIdAndStudyRoomId(studyRoomId, userId)
                .orElseThrow(() -> new EntityNotFoundException(
                        "해당 유저는 이 스터디룸에 참가하고 있지 않습니다. roomId: " + studyRoomId + ", userId: " + userId));

        studyRoomParticipantJpaRepository.delete(studyRoomParticipant);
    }

    // 유저 -> 스터디룸 리스트 조회
    @Override
    @Transactional
    public List<StudyRoomDTO> findStudyRoomsByUser(Long userId) {
        List<StudyRoomParticipant> participants = studyRoomParticipantJpaRepository.findAllByUserId(userId);

        return studyRoomMapper.toDto(participants);
    }

    // 스터디룸 참가 인원(id) 조회
    @Override
    @Transactional
    public List<StudyRoomParticipantDTO> findAllByStudyRoomId(Long studyRoomId) {
        List<StudyRoomParticipant> participants = studyRoomParticipantJpaRepository.findAllByStudyRoomId(studyRoomId);

        return participants.stream()
                .map(studyRoomParticipantMapper::toDto)
                .toList();
    }

    // 스터디룸 참가
    @Override
    @Transactional
    public void joinStudyRoom(StudyRoomParticipantDTO dto) {
        if (studyRoomParticipantJpaRepository.existsByUserIdAndStudyRoomId(dto.getUserId(), dto.getStudyRoomId())) {
            throw new IllegalStateException("이미 이 스터디방에 참여한 유저입니다.");
        }

        StudyRoom room = studyRoomJpaRepository.findById(dto.getStudyRoomId())
                .orElseThrow(() -> new EntityNotFoundException("스터디룸이 존재하지 않습니다."));

        Users user = usersJpaRepository.findById(dto.getUserId())
                .orElseThrow(() -> new EntityNotFoundException("유저가 존재하지 않습니다."));

        StudyRoomParticipant participant = StudyRoomParticipant.builder()
                .studyRoom(room)
                .user(user)
                .joinedAt(LocalDateTime.now())
                .build();

        studyRoomParticipantJpaRepository.save(participant);
    }


}
