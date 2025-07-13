package com.study_group_service.study_group_service.service.study;

import com.study_group_service.study_group_service.entity.study.MeetingVote;
import com.study_group_service.study_group_service.entity.study.StudyRoomMeeting;
import com.study_group_service.study_group_service.entity.study.StudyRoomParticipant;
import com.study_group_service.study_group_service.entity.user.User;
import com.study_group_service.study_group_service.repository.study.MeetingVoteJpaRepository;
import com.study_group_service.study_group_service.repository.study.StudyRoomMeetingJpaRepository;
import com.study_group_service.study_group_service.repository.study.StudyRoomParticipantJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MeetingVoteServiceImpl implements MeetingVoteService {
    private final MeetingVoteJpaRepository voteRepo;
    private final StudyRoomMeetingJpaRepository meetingRepo;
    private final StudyRoomParticipantJpaRepository participantRepo;

    @Override
    public MeetingVote saveVote(Long meetingId, Long userId, String vote) {
        MeetingVote existing = voteRepo.findByMeetingIdAndUserId(meetingId, userId);
        if (existing != null) {
            existing.setVote(vote);
            return voteRepo.save(existing);
        }
        MeetingVote mv = new MeetingVote();
        mv.setMeeting(meetingRepo.findById(meetingId).orElseThrow());
        User user = participantRepo.findByUserId(userId).get(0).getUser();
        mv.setUser(user);
        mv.setVote(vote);
        return voteRepo.save(mv);
    }

    @Override
    public List<MeetingVote> getVotesForMeeting(Long meetingId) {
        return voteRepo.findByMeetingId(meetingId);
    }

    @Override
    public boolean isApproved(Long meetingId, int participantCount) {
        List<MeetingVote> votes = voteRepo.findByMeetingId(meetingId);
        long yesCount = votes.stream().filter(v -> "yes".equals(v.getVote())).count();
        if (yesCount > participantCount / 2) {
            StudyRoomMeeting meeting = meetingRepo.findById(meetingId).orElseThrow();
            List<StudyRoomParticipant> participants = participantRepo.findByStudyRoomId(meeting.getStudyRoom().getId());
            Set<User> users = participants.stream().map(StudyRoomParticipant::getUser).collect(Collectors.toSet());
            meeting.getParticipants().addAll(users);
            meetingRepo.save(meeting);
            return true;
        }
        return false;
    }
} 