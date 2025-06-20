package com.study_group_service.study_group_service.controller;

import com.study_group_service.study_group_service.config.redis.RedisPublisher;
import com.study_group_service.study_group_service.dto.chat.ChatRoomDTO;
import com.study_group_service.study_group_service.dto.chat.ChatRoomMessageDTO;
import com.study_group_service.study_group_service.service.chat.ChatMessageService;
import com.study_group_service.study_group_service.service.chat.ChatRoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@RequestMapping("/chat")
@RestController
public class ChatController {

    private final RedisPublisher redisPublisher;
    private final ChatMessageService chatMessageService;
    private final ChatRoomService chatRoomService;

    // 메시지 전송 (Redis Pub/Sub)
    /** 성공 **/
    @PostMapping("/send")
    public ResponseEntity<Void> sendMessage(@RequestBody ChatRoomMessageDTO dto) {
        ChatRoomMessageDTO messageToSend = dto.toBuilder()
                .sentAt(LocalDateTime.now())
                .build();

        redisPublisher.publish("chatroom", messageToSend);
        return ResponseEntity.ok().build();
    }

    // 채팅방 ID로 메시지 전체 조회
    /** 성공 **/
    @GetMapping("/rooms/{chatRoomId}/messages")
    public ResponseEntity<Optional<ChatRoomMessageDTO>> getMessagesByRoomId(@PathVariable Long chatRoomId) {
        Optional<ChatRoomMessageDTO> messages = chatMessageService.getChatMessagesByRoomId(chatRoomId);
        return ResponseEntity.ok(messages);
    }

    // 모든 채팅방 찾기
    /** 성공 **/
    @GetMapping
    public ResponseEntity<List<ChatRoomDTO>> getChatRooms() {
        chatRoomService.getChatRooms();
        return ResponseEntity.ok(chatRoomService.getChatRooms());
    }

    // 모든 전송 메세지 조회할지 고민 -> 다수 데이터가 쌓일 시 소모가 너무 클 것 같음.

}
