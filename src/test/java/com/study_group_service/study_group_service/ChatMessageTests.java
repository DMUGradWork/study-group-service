package com.study_group_service.study_group_service;

import com.study_group_service.study_group_service.dto.chat.ChatRoomDTO;
import com.study_group_service.study_group_service.dto.chat.ChatRoomMessageDTO;
import com.study_group_service.study_group_service.dto.study.StudyRoomCategoryDTO;
import com.study_group_service.study_group_service.dto.study.StudyRoomDTO;
import com.study_group_service.study_group_service.dto.study.StudyRoomParticipantDTO;
import com.study_group_service.study_group_service.dto.users.UsersDTO;
import com.study_group_service.study_group_service.service.chat.ChatMessageService;
import com.study_group_service.study_group_service.service.chat.ChatRoomService;
import com.study_group_service.study_group_service.service.study.StudyCategoryService;
import com.study_group_service.study_group_service.service.study.StudyRoomService;
import com.study_group_service.study_group_service.service.users.UsersService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.assertj.core.api.Assertions.assertThat;


import java.time.LocalDateTime;
import java.util.List;

@SpringBootTest
public class ChatMessageTests {

    private UsersDTO defaultUser;
    private StudyRoomCategoryDTO defaultCategory;

    @Autowired
    private UsersService usersService;

    @Autowired
    private StudyRoomService studyRoomService;

    @Autowired
    private StudyCategoryService studyCategoryService;

    @Autowired
    private ChatMessageService chatMessageService;

    @BeforeEach
    void setup() {
        defaultUser = usersService.setUsers(createUsersDTO(1L, "TestUserName"));
        defaultCategory = studyCategoryService.setStudyCategory(createCategoryDTO(1L, "TestCategoryName"));

        studyRoomService.setStudyRoom(createRoomDTO("TestName1"));
        studyRoomService.setStudyRoom(createRoomDTO("TestName2"));
    }

    private StudyRoomDTO createRoomDTO(String name) {
        return StudyRoomDTO.builder()
                .name(name)
                .studyRoomHostId(defaultUser.getId())
                .categoriesId(defaultCategory.getId())
                .chatId(null)
                .created_at(LocalDateTime.now())
                .peopleCount(5)
                .rules("테스트 규칙")
                .notification("테스트 공지")
                .build();
    }

    private StudyRoomCategoryDTO createCategoryDTO(Long id, String name) {
        return StudyRoomCategoryDTO.builder().id(id).name(name).build();
    }

    private UsersDTO createUsersDTO(Long id, String name) {
        return UsersDTO.builder().id(id).name(name).build();
    }

    @Test
    @DisplayName("메세지_전송")
    @Transactional
    void sendMessage() {
        // given
        StudyRoomDTO studyRoomDTO = createRoomDTO("TestName");
        StudyRoomDTO savedRoom = studyRoomService.setStudyRoom(studyRoomDTO);

        StudyRoomDTO chatRoom = studyRoomService.findByChatRoomId(savedRoom.getId());
        Long chatRoomId = chatRoom.getId();
        Long userId = defaultUser.getId();

        ChatRoomMessageDTO messageDTO = ChatRoomMessageDTO.builder()
                .chatRoomId(chatRoomId)
                .userId(userId)
                .sender(defaultUser.getName())
                .content("테스트 메시지")
                .sentAt(LocalDateTime.now())
                .build();

        // when
        chatMessageService.saveMessage(messageDTO);

        // then
        var savedMessageOptional = chatMessageService.getChatMessagesByRoomId(chatRoomId);
        assertThat(savedMessageOptional).isPresent();
        assertThat(savedMessageOptional.get().getContent()).isEqualTo("테스트 메시지");
    }

    @Test
    @DisplayName("채팅방_전체_조회")
    @Transactional
    void getAllMessagesTest() {
        // given
        StudyRoomDTO savedRoom = studyRoomService.setStudyRoom(createRoomDTO("채팅방-전체조회"));

        StudyRoomDTO chatRoom = studyRoomService.findByChatRoomId(savedRoom.getId());
        Long chatRoomId = chatRoom.getId();
        Long userId = defaultUser.getId();

        for (int i = 1; i <= 3; i++) {
            ChatRoomMessageDTO message = ChatRoomMessageDTO.builder()
                    .chatRoomId(chatRoomId)
                    .userId(userId)
                    .sender(defaultUser.getName())
                    .content("테스트 메시지 " + i)
                    .sentAt(LocalDateTime.now())
                    .build();
            chatMessageService.saveMessage(message);
        }

        // when
        List<ChatRoomMessageDTO> messages = chatMessageService.getAllMessagesByChatRoomId(chatRoomId);

        // then
        assertThat(messages).hasSize(3);
        assertThat(messages.get(0).getContent()).isEqualTo("테스트 메시지 1");
        assertThat(messages.get(1).getContent()).isEqualTo("테스트 메시지 2");
        assertThat(messages.get(2).getContent()).isEqualTo("테스트 메시지 3");
    }

}
