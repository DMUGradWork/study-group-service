package com.study_group_service.study_group_service.event.meeting;

import java.util.UUID;

public record StudyMeetingCancelled(
    UUID studyGroupId,
    UUID meetingId
) {}
