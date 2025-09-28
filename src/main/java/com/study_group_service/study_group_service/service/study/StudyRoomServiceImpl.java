package com.study_group_service.study_group_service.service.study;

import com.study_group_service.study_group_service.dto.study.StudyRoomDTO;
import com.study_group_service.study_group_service.dto.study.StudyRoomParticipantDTO;
import com.study_group_service.study_group_service.entity.chat.ChatRoom;
import com.study_group_service.study_group_service.entity.study.StudyRoomCategory;
import com.study_group_service.study_group_service.entity.study.StudyRoom;
import com.study_group_service.study_group_service.entity.study.StudyRoomParticipant;
import com.study_group_service.study_group_service.entity.user.User;
import com.study_group_service.study_group_service.event.study.StudyRoomCreated;
import com.study_group_service.study_group_service.event.study.StudyRoomJoined;
import com.study_group_service.study_group_service.event.study.StudyRoomLeft;
import com.study_group_service.study_group_service.exception.study.StudyRoomNotFoundException;
import com.study_group_service.study_group_service.exception.user.AlreadyUserException;
import com.study_group_service.study_group_service.exception.user.UserNotFoundException;
import com.study_group_service.study_group_service.mapper.study.StudyRoomMapper;
import com.study_group_service.study_group_service.mapper.study.StudyRoomParticipantMapper;
import com.study_group_service.study_group_service.message.ErrorMessage;
import com.study_group_service.study_group_service.repository.chat.ChatRoomJpaRepository;
import com.study_group_service.study_group_service.repository.study.StudyCategoryJpaRepository;
import com.study_group_service.study_group_service.repository.study.StudyRoomParticipantJpaRepository;
import com.study_group_service.study_group_service.repository.study.StudyRoomJpaRepository;
import com.study_group_service.study_group_service.repository.user.UserJpaRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class StudyRoomServiceImpl implements StudyRoomService {
    private final StudyRoomJpaRepository studyRoomJpaRepository;
    private final ChatRoomJpaRepository chatRoomJpaRepository;
    private final UserJpaRepository userJpaRepository;
    private final StudyCategoryJpaRepository studyCategoryJpaRepository;
    private final ErrorMessage errorMessage;
    private final StudyRoomMapper studyRoomMapper;
    private final StudyRoomParticipantMapper studyRoomParticipantMapper;
    private final StudyRoomParticipantJpaRepository studyRoomParticipantJpaRepository;
    private final ApplicationEventPublisher eventPublisher;

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
                .orElseThrow(() -> new StudyRoomNotFoundException());
        return studyRoomMapper.toDto(studyRoom);
    }

    // UUID로 스터디룸 검색
    @Override
    @Transactional
    public StudyRoomDTO getStudyRoomByUuid(UUID uuid) {
        StudyRoom studyRoom = studyRoomJpaRepository.findByUuid(uuid)
                .orElseThrow(() -> new StudyRoomNotFoundException());
        return studyRoomMapper.toDto(studyRoom);
    }

    // 스터디룸의 채팅룸(ID) 조회
    @Override
    @Transactional
    public StudyRoomDTO findByChatRoomId(Long chatRoomId) {
        StudyRoom room = studyRoomJpaRepository.findByChatRoomId(chatRoomId)
                .orElseThrow(() -> new StudyRoomNotFoundException());
        return studyRoomMapper.toDto(room);
    }

    // UUID로 스터디룸의 채팅룸 조회
    @Override
    @Transactional
    public StudyRoomDTO findByChatRoomUuid(UUID chatRoomUuid) {
        StudyRoom room = studyRoomJpaRepository.findByChatRoomUuid(chatRoomUuid)
                .orElseThrow(() -> new StudyRoomNotFoundException());
        return studyRoomMapper.toDto(room);
    }

    // 스터디룸 + 채팅방 생성
    @Override
    @Transactional
    public StudyRoomDTO setStudyRoom(StudyRoomDTO studyRoomDTO) {
        User user = userJpaRepository.findById(studyRoomDTO.getStudyRoomHostId())
                .orElseThrow(() -> new IllegalArgumentException(errorMessage.showNoUserMessage()));
        StudyRoomCategory category = studyCategoryJpaRepository.findById(studyRoomDTO.getCategoriesId())
                .orElseThrow(() -> new IllegalArgumentException(errorMessage.showNoCategoryMessage()));

        StudyRoom studyRoom = studyRoomMapper.toEntity(studyRoomDTO, user, category);
        studyRoomJpaRepository.save(studyRoom);

        // 방장 자동 참여
        StudyRoomParticipant hostParticipant = StudyRoomParticipant.builder()
                .studyRoom(studyRoom)
                .user(user)
                .joinedAt(LocalDateTime.now())
                .build();
        studyRoomParticipantJpaRepository.save(hostParticipant);

        ChatRoom chatRoom = ChatRoom.builder()
                .name(studyRoom.getName())
                .studyRoom(studyRoom)
                .build();
        chatRoomJpaRepository.save(chatRoom);

        studyRoom.updateChatRoom(chatRoom);
        StudyRoom savedStudyRoom = studyRoomJpaRepository.save(studyRoom);

        // 스터디룸 생성 이벤트 발행
        StudyRoomCreated studyRoomCreatedEvent = new StudyRoomCreated(
                savedStudyRoom.getUuid(),
                user.getUuid(),
                savedStudyRoom.getName(),
                savedStudyRoom.getDescription(),
                LocalDateTime.now()
        );
        eventPublisher.publishEvent(studyRoomCreatedEvent);

        return studyRoomMapper.toDto(savedStudyRoom);
    }

    // 스터디룸 삭제
    @Override
    @Transactional
    public void deleteStudyRoom(Long id) {
        StudyRoom studyRoom = studyRoomJpaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(errorMessage.showNoStudyRoomMessage() + " " + id));

        // 연관된 ChatRoom 삭제
        if (studyRoom.getChatRoom() != null) {
            chatRoomJpaRepository.delete(studyRoom.getChatRoom());
        }

        // StudyRoom 삭제
        studyRoomJpaRepository.deleteById(id);
    }

    // UUID로 스터디룸 삭제
    @Override
    @Transactional
    public void deleteStudyRoomByUuid(UUID uuid) {
        StudyRoom studyRoom = studyRoomJpaRepository.findByUuid(uuid)
                .orElseThrow(() -> new EntityNotFoundException(errorMessage.showNoStudyRoomMessage() + " " + uuid));

        // 연관된 ChatRoom 삭제
        if (studyRoom.getChatRoom() != null) {
            chatRoomJpaRepository.delete(studyRoom.getChatRoom());
        }

        // StudyRoom 삭제
        studyRoomJpaRepository.deleteById(studyRoom.getId());
    }


    // 스터디룸 규칙 설정
    @Override
    @Transactional
    public StudyRoomDTO setStudyRoomRules(StudyRoomDTO studyRoomDTO) {
        Long roomId = studyRoomDTO.getId();
        String rules = studyRoomDTO.getRules();

        StudyRoom studyRoom = studyRoomJpaRepository.findById(roomId)
                .orElseThrow(() -> new EntityNotFoundException(errorMessage.showNoStudyRoomMessage() + " " + roomId));

        studyRoom.updateRules(rules);

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
                .orElseThrow(() -> new EntityNotFoundException(errorMessage.showNoStudyRoomMessage() + " " + roomId));

        studyRoom.updateNotification(notification);

        StudyRoom saved = studyRoomJpaRepository.save(studyRoom);
        return studyRoomMapper.toDto(saved);
    }

    // 스터디룸 공지사항 삭제
    @Override
    @Transactional
    public void deleteNotification(Long id) {
        StudyRoom room = studyRoomJpaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(errorMessage.showNoStudyRoomMessage() + " " + id));

        room.updateNotification(null);
        studyRoomJpaRepository.save(room);
    }

    // UUID로 스터디룸 공지사항 삭제
    @Override
    @Transactional
    public void deleteNotificationByUuid(UUID uuid) {
        StudyRoom room = studyRoomJpaRepository.findByUuid(uuid)
                .orElseThrow(() -> new EntityNotFoundException(errorMessage.showNoStudyRoomMessage() + " " + uuid));

        room.updateNotification(null);
        studyRoomJpaRepository.save(room);
    }

    // 스터디룸 규칙 삭제
    @Override
    @Transactional
    public void deleteRules(Long id) {
        StudyRoom room = studyRoomJpaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(errorMessage.showNoStudyRoomMessage() + " " + id));

        room.updateRules(null);
        studyRoomJpaRepository.save(room);
    }

    // UUID로 스터디룸 규칙 삭제
    @Override
    @Transactional
    public void deleteRulesByUuid(UUID uuid) {
        StudyRoom room = studyRoomJpaRepository.findByUuid(uuid)
                .orElseThrow(() -> new EntityNotFoundException(errorMessage.showNoStudyRoomMessage() + " " + uuid));

        room.updateRules(null);
        studyRoomJpaRepository.save(room);
    }

    // 참가 유저 삭제
    @Override
    @Transactional
    public void deleteStudyRoomParticipants(Long studyRoomId, Long userId) {
        Optional<StudyRoomParticipant> participantOpt = studyRoomParticipantJpaRepository
                .findByUserIdAndStudyRoomId(userId, studyRoomId);
        
        if (participantOpt.isPresent()) {
            StudyRoomParticipant participant = participantOpt.get();
            StudyRoom studyRoom = participant.getStudyRoom();
            User user = participant.getUser();
            
            studyRoomParticipantJpaRepository.delete(participant);
            
            // 스터디룸 퇴장 이벤트 발행
            StudyRoomLeft studyRoomLeftEvent = new StudyRoomLeft(
                    studyRoom.getUuid(),
                    user.getUuid(),
                    "USER_LEFT", // 퇴장 이유
                    LocalDateTime.now()
            );
            eventPublisher.publishEvent(studyRoomLeftEvent);
        }
    }

    // UUID로 참가 유저 삭제
    @Override
    @Transactional
    public void deleteStudyRoomParticipantsByUuid(UUID studyRoomUuid, UUID userUuid) {
        StudyRoom studyRoom = studyRoomJpaRepository.findByUuid(studyRoomUuid)
                .orElseThrow(() -> new EntityNotFoundException(errorMessage.showNoStudyRoomMessage() + " " + studyRoomUuid));
        
        User user = userJpaRepository.findByUuid(userUuid)
                .orElseThrow(() -> new UserNotFoundException(errorMessage.showNoUserMessage()));
        
        Optional<StudyRoomParticipant> participantOpt = studyRoomParticipantJpaRepository
                .findByUserIdAndStudyRoomId(user.getId(), studyRoom.getId());
        
        if (participantOpt.isPresent()) {
            StudyRoomParticipant participant = participantOpt.get();
            studyRoomParticipantJpaRepository.delete(participant);
            
            // 스터디룸 퇴장 이벤트 발행
            StudyRoomLeft studyRoomLeftEvent = new StudyRoomLeft(
                    studyRoom.getUuid(),
                    user.getUuid(),
                    "USER_LEFT", // 퇴장 이유
                    LocalDateTime.now()
            );
            eventPublisher.publishEvent(studyRoomLeftEvent);
        }
    }

    // 유저 -> 스터디룸 리스트 조회
    @Override
    @Transactional
    public List<StudyRoomDTO> findStudyRoomsByUser(Long userId) {
        List<StudyRoomParticipant> participants = studyRoomParticipantJpaRepository.findAllByUserId(userId);

        return studyRoomMapper.toDto(participants);
    }

    // UUID로 유저 -> 스터디룸 리스트 조회
    @Override
    @Transactional
    public List<StudyRoomDTO> findStudyRoomsByUserUuid(UUID userUuid) {
        User user = userJpaRepository.findByUuid(userUuid)
                .orElseThrow(() -> new UserNotFoundException(errorMessage.showNoUserMessage()));
        
        List<StudyRoomParticipant> participants = studyRoomParticipantJpaRepository.findAllByUserId(user.getId());

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

    // UUID로 스터디룸 참가 인원 조회
    @Override
    @Transactional
    public List<StudyRoomParticipantDTO> findAllByStudyRoomUuid(UUID studyRoomUuid) {
        StudyRoom studyRoom = studyRoomJpaRepository.findByUuid(studyRoomUuid)
                .orElseThrow(() -> new StudyRoomNotFoundException());
        
        List<StudyRoomParticipant> participants = studyRoomParticipantJpaRepository.findAllByStudyRoomId(studyRoom.getId());

        return participants.stream()
                .map(studyRoomParticipantMapper::toDto)
                .toList();
    }

    // 스터디룸 참가
    @Override
    @Transactional
    public void joinStudyRoom(StudyRoomParticipantDTO dto) {
        if (studyRoomParticipantJpaRepository.existsByUserIdAndStudyRoomId(dto.getUserId(), dto.getStudyRoomId())) {
            throw new AlreadyUserException(errorMessage.showAlreadyUserInStudyMessage());
        }

        StudyRoom room = studyRoomJpaRepository.findById(dto.getStudyRoomId())
                .orElseThrow(() -> new StudyRoomNotFoundException());

        User user = userJpaRepository.findById(dto.getUserId())
                .orElseThrow(() -> new UserNotFoundException(errorMessage.showNoUserMessage()));

        StudyRoomParticipant participant = StudyRoomParticipant.builder()
                .studyRoom(room)
                .user(user)
                .joinedAt(LocalDateTime.now())
                .build();

        StudyRoomParticipant savedParticipant = studyRoomParticipantJpaRepository.save(participant);

        // 스터디룸 참가 이벤트 발행
        StudyRoomJoined studyRoomJoinedEvent = new StudyRoomJoined(
                room.getUuid(),
                user.getUuid(),
                "MEMBER", // 기본 역할
                LocalDateTime.now()
        );
        eventPublisher.publishEvent(studyRoomJoinedEvent);
    }


}
