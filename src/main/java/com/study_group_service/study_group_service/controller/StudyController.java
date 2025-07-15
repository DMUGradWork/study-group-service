package com.study_group_service.study_group_service.controller;

import com.study_group_service.study_group_service.dto.study.StudyRoomCategoryDTO;
import com.study_group_service.study_group_service.dto.study.StudyRoomDTO;
import com.study_group_service.study_group_service.dto.study.StudyRoomParticipantDTO;
import com.study_group_service.study_group_service.entity.study.MeetingVote;
import com.study_group_service.study_group_service.entity.study.StudyRoomMeeting;
import com.study_group_service.study_group_service.service.study.MeetingVoteService;
import com.study_group_service.study_group_service.service.study.StudyCategoryService;
import com.study_group_service.study_group_service.service.study.StudyRoomService;
import com.study_group_service.study_group_service.service.study.StudyRoomMeetingService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.util.StringUtils;
import org.springframework.http.HttpStatus;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/study")
public class StudyController {

    private final StudyRoomService studyRoomService;
    private final StudyCategoryService studyCategoryService;
    private final StudyRoomMeetingService studyRoomMeetingService;
    private final MeetingVoteService meetingVoteService;

    // 모든 스터디룸 조회
    /** 성공 **/
    @GetMapping()
    public ResponseEntity<List<StudyRoomDTO>> getStudyRooms() {
        studyRoomService.getStudyRooms();
        return ResponseEntity.ok(studyRoomService.getStudyRooms());
    }

    // 특정 이름(name)을 가진 스터디룸 검색
    /** 성공 **/
    @GetMapping("/{studyName}")
    public ResponseEntity<StudyRoomDTO> getStudyRoom(@PathVariable String studyName) {
        studyRoomService.getStudyRoom(studyName);
        return ResponseEntity.ok(studyRoomService.getStudyRoom(studyName));
    }

    // 스터디룸 생성
    /** 성공 **/
    @PostMapping()
    public ResponseEntity<StudyRoomDTO> setStudyRoom(@RequestBody StudyRoomDTO studyRoomDTO) {
        return ResponseEntity.ok(studyRoomService.setStudyRoom(studyRoomDTO));
    }

    // 스터디룸 삭제
    /** 성공 **/
    @DeleteMapping("/rooms/{studyRoomId}")
    public ResponseEntity<Void> deleteStudyRoom(@PathVariable Long studyRoomId) {
        studyRoomService.deleteStudyRoom(studyRoomId);
        return ResponseEntity.noContent().build();
    }

    // 스터디룸 참가
    /** 성공 **/
    @PostMapping("/rooms/join")
    public ResponseEntity<Void> joinStudyRoom(@RequestBody StudyRoomParticipantDTO dto) {
        studyRoomService.joinStudyRoom(dto);
        return ResponseEntity.ok().build();
    }

    //==================================================================================//

    // 모든 카테고리 조회
    /** 성공 **/
    @GetMapping("/category")
    public ResponseEntity<List<StudyRoomCategoryDTO>> getStudyCategory() {
        studyCategoryService.getStudyCategory();
        return ResponseEntity.ok(studyCategoryService.getStudyCategory());
    }

    // 카테고리 생성
    /** 성공 **/
    @PostMapping("/category")
    public ResponseEntity<StudyRoomCategoryDTO> setStudyCategory(@RequestBody StudyRoomCategoryDTO studyRoomCategoryDTO) {
        StudyRoomCategoryDTO saved = studyCategoryService.setStudyCategory(studyRoomCategoryDTO);
        return ResponseEntity.ok(saved);
    }

    // 카테고리 삭제
    /** 성공 **/
    @DeleteMapping("/category/{categoryId}")
    public ResponseEntity<Void> deleteStudyCategory(@PathVariable Long categoryId) {
        studyCategoryService.deleteStudyCategory(categoryId);
        return ResponseEntity.noContent().build();
    }

    //==================================================================================//

    // 스터디룸 규칙 설정 및 수정 (기본 값 NULL)
    /** 성공 **/
    @PatchMapping("/rule/{studyRoomId}")
    public ResponseEntity<StudyRoomDTO> setStudyRoomRule(@PathVariable Long studyRoomId, @RequestBody StudyRoomDTO studyRoomDTO) {
        StudyRoomDTO updatedStudyRoomDTO = studyRoomDTO.toBuilder().id(studyRoomId).build();
        return ResponseEntity.ok(studyRoomService.setStudyRoomRules(updatedStudyRoomDTO));
    }

    // 스터디룸 규칙 삭제
    /** 성공 **/
    @DeleteMapping("/rule/{studyRoomId}")
    public ResponseEntity<Void> deleteStudyRoomRule(@PathVariable Long studyRoomId) {
        studyRoomService.deleteRules(studyRoomId);
        return ResponseEntity.noContent().build();
    }

    //==================================================================================//

    // 스터디룸 공지 설정 및 수정 (기본 값 NULL)
    /** 성공 **/
    @PatchMapping("/notification/{studyRoomId}")
    public ResponseEntity<StudyRoomDTO> setStudyRoomNotification(@PathVariable Long studyRoomId, @RequestBody StudyRoomDTO studyRoomDTO) {
        StudyRoomDTO updatedStudyRoomDTO = studyRoomDTO.toBuilder().id(studyRoomId).build();
        return ResponseEntity.ok(studyRoomService.setStudyRoomNotification(updatedStudyRoomDTO));
    }

    // 스터디룸 공지사항 삭제
    /** 성공 **/
    @DeleteMapping("/notification/{studyRoomId}")
    public ResponseEntity<Void> deleteStudyRoomNotification(@PathVariable Long studyRoomId) {
        studyRoomService.deleteNotification(studyRoomId);
        return ResponseEntity.noContent().build();
    }

    //==================================================================================//

    // 특정(id) 스터디룸 참여 유저 조회 studyRoom -> users
    /** 성공 **/
    @GetMapping("/{studyRoomId}/users")
    public ResponseEntity<List<StudyRoomParticipantDTO>> getStudyRoomParticipants(@PathVariable Long studyRoomId) {
        List<StudyRoomParticipantDTO> participants = studyRoomService.findAllByStudyRoomId(studyRoomId);
        return ResponseEntity.ok(participants);
    }


    // 유저가 참가한 모든 스터디룸 리스트 조회 user -> studyRoom
    /** 성공 **/
    @GetMapping("/{userId}/rooms")
    public ResponseEntity<List<StudyRoomDTO>> getStudyRoomsByUser(@PathVariable Long userId) {
        return ResponseEntity.ok(studyRoomService.findStudyRoomsByUser(userId));
    }

    // 참가 유저 삭제
    /** 성공 **/
    @DeleteMapping("/{studyRoomId}/{userId}")
    public ResponseEntity<Void> deleteStudyRoomUser(@PathVariable Long studyRoomId, @PathVariable Long userId) {
        studyRoomService.deleteStudyRoomParticipants(studyRoomId, userId);
        return ResponseEntity.noContent().build();
    }

    //==================================================================================//

    // 스터디 그룹 찬반 투표 -> 데이팅 투표 로직 보고 동일 로직 예정

    // 스터디 그룹 채팅방 찾기
    /** 성공 **/
    @GetMapping("/chat/{studyRoomId}")
    public ResponseEntity<StudyRoomDTO> findByChatRoom (@PathVariable Long studyRoomId) {
        return ResponseEntity.ok(studyRoomService.findByChatRoomId(studyRoomId));
    }

    @PostMapping("/meeting")
    public StudyRoomMeeting createMeeting(@RequestBody Map<String, String> request) {
        Long studyRoomId = Long.parseLong(request.get("studyRoomId"));
        String title = request.get("title");
        String duration = request.get("duration");
        String meetingTimeStr = request.get("meetingTime");
        LocalDateTime meetingTime = LocalDateTime.parse(meetingTimeStr, DateTimeFormatter.ofPattern("yyyy-MM-dd-HH:mm"));
        return studyRoomMeetingService.createMeeting(studyRoomId, title, duration, meetingTime);
    }

    @PostMapping("/meeting/vote")
    public MeetingVote voteMeeting(@RequestBody Map<String, String> request) {
        Long meetingId = Long.parseLong(request.get("meetingId"));
        Long userId = Long.parseLong(request.get("userId"));
        String vote = request.get("vote");
        return meetingVoteService.saveVote(meetingId, userId, vote);
    }

    @GetMapping("/meeting/{meetingId}/votes")
    public List<MeetingVote> getVotes(@PathVariable Long meetingId) {
        return meetingVoteService.getVotesForMeeting(meetingId);
    }

    @GetMapping("/meeting/{meetingId}/approved")
    public boolean checkApproved(@PathVariable Long meetingId, @RequestParam int participantCount) {
        return meetingVoteService.isApproved(meetingId, participantCount);
    }

    @GetMapping("/rooms/{roomId}/meeting")
    public StudyRoomMeeting getMeetingByRoom(@PathVariable Long roomId) {
        return studyRoomMeetingService.getMeetingByRoomId(roomId);
    }

    @PostMapping("/upload-image")
    public ResponseEntity<?> uploadImage(@RequestParam("file") MultipartFile file, HttpServletRequest request) {
        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body("No file provided");
        }
        String uploadDir = System.getProperty("user.dir") + "/uploads/";
        File dir = new File(uploadDir);
        if (!dir.exists()) dir.mkdirs();
        String originalFilename = StringUtils.cleanPath(file.getOriginalFilename());
        String fileName = System.currentTimeMillis() + "_" + originalFilename;
        File dest = new File(uploadDir + fileName);
        try {
            file.transferTo(dest);
            String host = request.getServerName();
            int port = request.getServerPort();
            String url = "http://" + host + ":" + port + "/uploads/" + fileName;
            HashMap<String, String> result = new HashMap<>();
            result.put("url", url);
            return ResponseEntity.ok(result);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("File upload failed");
        }
    }
}
