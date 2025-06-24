package com.study_group_service.study_group_service.controller;

import com.study_group_service.study_group_service.dto.study.StudyRoomCategoryDTO;
import com.study_group_service.study_group_service.dto.study.StudyRoomDTO;
import com.study_group_service.study_group_service.dto.study.StudyRoomParticipantDTO;
import com.study_group_service.study_group_service.service.study.StudyCategoryService;
import com.study_group_service.study_group_service.service.study.StudyRoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/study")
public class StudyController {

    private final StudyRoomService studyRoomService;
    private final StudyCategoryService studyCategoryService;

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

    // 스터디 모임 참석 여부 설정 -> 데이팅이랑 어떻게 연결할지 로직 확인

    // 유저의 스터디 그룹 참여 횟수 조회 -> 데이팅이랑 어떻게 연결할지 로직 확인

}
