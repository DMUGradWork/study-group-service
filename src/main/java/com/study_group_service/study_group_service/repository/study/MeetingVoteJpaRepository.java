package com.study_group_service.study_group_service.repository.study;

import com.study_group_service.study_group_service.entity.study.MeetingVote;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MeetingVoteJpaRepository extends JpaRepository<MeetingVote, Long> {
    List<MeetingVote> findByMeetingId(Long meetingId);
    MeetingVote findByMeetingIdAndUserId(Long meetingId, Long userId);
} 