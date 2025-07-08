package com.study_group_service.study_group_service.service.study;

import com.study_group_service.study_group_service.entity.study.StudyRoomMeeting;
 
public interface StudyRoomMeetingService {
    StudyRoomMeeting createMeeting(Long studyRoomId, String title, String duration, java.time.LocalDateTime meetingTime);
    StudyRoomMeeting getMeetingByRoomId(Long roomId);
} 