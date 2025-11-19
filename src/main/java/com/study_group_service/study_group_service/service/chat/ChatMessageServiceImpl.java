package com.study_group_service.study_group_service.service;

import com.study_group_service.study_group_service.dto.ChatRoomMessageDTO;
import com.study_group_service.study_group_service.entity.chat.ChatRoom;
import com.study_group_service.study_group_service.entity.chat.ChatRoomMessages;
import com.study_group_service.study_group_service.entity.user.Users;
import com.study_group_service.study_group_service.mapper.ChatRoomMessageMapper;
import com.study_group_service.study_group_service.message.ErrorMessage;
import com.study_group_service.study_group_service.repository.ChatMessageJpaRepository;
import com.study_group_service.study_group_service.repository.ChatRoomJpaRepository;
import com.study_group_service.study_group_service.repository.users.UsersJpaRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class ChatMessageServiceImpl implements ChatMessageService {

    private final ChatRoomJpaRepository chatRoomJpaRepository;
    private final UsersJpaRepository usersJpaRepository;
    private final ChatMessageJpaRepository chatMessageJpaRepository;
    private final ChatRoomMessageMapper chatMessageMapper;
    private final ErrorMessage errorMessage;

    @Override
    public void saveMessage(ChatRoomMessageDTO dto) {
        ChatRoom chatRoom = chatRoomJpaRepository.findById(dto.getChatRoomId())
                .orElseThrow(() -> new EntityNotFoundException(errorMessage.showNoChatRoomMessage()));
        Users user = usersJpaRepository.findById(dto.getUserId())
                .orElseThrow(() -> new EntityNotFoundException(errorMessage.showNoUserMessage()));

        ChatRoomMessages message = chatMessageMapper.toEntity(dto, chatRoom, user);
        chatMessageJpaRepository.save(message);
    }

    @Override
    @Transactional
    public Optional<ChatRoomMessageDTO> getChatMessagesByRoomId(Long chatRoomId) {
        Optional<ChatRoomMessages> messages = chatMessageJpaRepository.findById(chatRoomId);
        return chatMessageMapper.toDtoOptional(messages);
    }
}
