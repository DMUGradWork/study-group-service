package com.study_group_service.study_group_service.event.meeting;

import java.util.UUID;

public record StudyMeetingParticipationCancelled(
    UUID studyGroupId,
    UUID meetingId,
    UUID userId
) {}
