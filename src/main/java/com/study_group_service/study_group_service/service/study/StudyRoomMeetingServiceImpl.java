package com.study_group_service.study_group_service.service.study;

import com.study_group_service.study_group_service.entity.study.StudyRoom;
import com.study_group_service.study_group_service.entity.study.StudyRoomMeeting;
import com.study_group_service.study_group_service.repository.study.StudyRoomJpaRepository;
import com.study_group_service.study_group_service.repository.study.StudyRoomMeetingJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class StudyRoomMeetingServiceImpl implements StudyRoomMeetingService {

    private final StudyRoomMeetingJpaRepository meetingRepository;
    private final StudyRoomJpaRepository studyRoomRepository;

    @Autowired
    public StudyRoomMeetingServiceImpl(StudyRoomMeetingJpaRepository meetingRepository, StudyRoomJpaRepository studyRoomRepository) {
        this.meetingRepository = meetingRepository;
        this.studyRoomRepository = studyRoomRepository;
    }

    @Override
    public StudyRoomMeeting createMeeting(Long studyRoomId, String title, String duration, LocalDateTime meetingTime) {
        StudyRoom studyRoom = studyRoomRepository.findById(studyRoomId)
                .orElseThrow(() -> new IllegalArgumentException("스터디룸을 찾을 수 없습니다."));
        // 한 방에 하나의 일정만 허용
        Optional<StudyRoomMeeting> existing = meetingRepository.findAll().stream().filter(m -> m.getStudyRoom().getId().equals(studyRoomId)).findFirst();
        if (existing.isPresent()) {
            throw new IllegalStateException("이미 이 방에는 일정이 존재합니다.");
        }
        StudyRoomMeeting meeting = new StudyRoomMeeting();
        meeting.setStudyRoom(studyRoom);
        meeting.setMeetingTime(meetingTime);
        meeting.setTitle(title);
        meeting.setDuration(duration);
        return meetingRepository.save(meeting);
    }

    @Override
    public StudyRoomMeeting getMeetingByRoomId(Long roomId) {
        return meetingRepository.findAll().stream()
            .filter(m -> m.getStudyRoom().getId().equals(roomId))
            .findFirst()
            .orElse(null);
    }
} 