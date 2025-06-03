package com.study_group_service.study_group_service.service.chat;

import com.study_group_service.study_group_service.dto.chat.ChatRoomMessageDTO;
import com.study_group_service.study_group_service.entity.chat.ChatRoom;
import com.study_group_service.study_group_service.entity.chat.ChatRoomMessages;
import com.study_group_service.study_group_service.entity.user.Users;
import com.study_group_service.study_group_service.mapper.chat.ChatMessageMapper;
import com.study_group_service.study_group_service.message.ErrorMessage;
import com.study_group_service.study_group_service.repository.chat.ChatMessageJpaRepository;
import com.study_group_service.study_group_service.repository.chat.ChatRoomJpaRepository;
import com.study_group_service.study_group_service.repository.users.UsersJpaRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class ChatMessageServiceImpl implements ChatMessageService {

    private final ChatRoomJpaRepository chatRoomJpaRepository;
    private final UsersJpaRepository usersJpaRepository;
    private final ChatMessageJpaRepository chatMessageJpaRepository;
    private final ChatMessageMapper chatMessageMapper;
    private final ErrorMessage errorMessage;

    // 채팅 메세지 저장
    @Override
    @Transactional
    public void saveMessage(ChatRoomMessageDTO dto) {
        ChatRoom chatRoom = chatRoomJpaRepository.findById(dto.getChatRoomId())
                .orElseThrow(() -> new EntityNotFoundException(errorMessage.showNoChatRoomMessage()));
        Users user = usersJpaRepository.findById(dto.getUserId())
                .orElseThrow(() -> new EntityNotFoundException(errorMessage.showNoUserMessage()));

        ChatRoomMessages message = chatMessageMapper.toEntity(dto, chatRoom, user);
        chatMessageJpaRepository.save(message);
    }

    // 채팅방 마지막 메세지 반환 -> 프론트에서 채팅방에 마지막 메세지 출력 가능
    @Override
    @Transactional
    public Optional<ChatRoomMessageDTO> getChatMessagesByRoomId(Long chatRoomId) {
        List<ChatRoomMessages> messages = chatMessageJpaRepository.findAllByChatRoomIdOrderBySentAtAsc(chatRoomId);
        if (messages.isEmpty()) return Optional.empty();
        return chatMessageMapper.toDtoOptional(Optional.of(messages.get(messages.size() - 1)));
    }

    // 채팅방 아이디 모든 메세지 조회
    @Override
    @Transactional
    public List<ChatRoomMessageDTO> getAllMessagesByChatRoomId(Long chatRoomId) {
        List<ChatRoomMessages> messages = chatMessageJpaRepository.findAllByChatRoomIdOrderBySentAtAsc(chatRoomId);
        return messages.stream()
                .map(chatMessageMapper::toDto)
                .toList();
    }
}
