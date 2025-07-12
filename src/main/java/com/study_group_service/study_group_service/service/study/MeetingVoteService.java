package com.study_group_service.study_group_service.service.study;

import com.study_group_service.study_group_service.entity.study.MeetingVote;

import java.util.List;
 
public interface MeetingVoteService {
    MeetingVote saveVote(Long meetingId, Long userId, String vote);
    List<MeetingVote> getVotesForMeeting(Long meetingId);
    boolean isApproved(Long meetingId, int participantCount);
} 