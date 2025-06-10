package com.study_group_service.study_group_service;

import com.study_group_service.study_group_service.dto.study.StudyRoomCategoryDTO;
import com.study_group_service.study_group_service.dto.study.StudyRoomDTO;
import com.study_group_service.study_group_service.dto.users.UsersDTO;
import com.study_group_service.study_group_service.entity.study.StudyRoom;
import com.study_group_service.study_group_service.entity.user.Users;
import com.study_group_service.study_group_service.enums.Role;
import com.study_group_service.study_group_service.service.study.StudyCategoryService;
import com.study_group_service.study_group_service.service.study.StudyRoomService;
import com.study_group_service.study_group_service.service.users.UsersService;
import jakarta.transaction.Transactional;
import org.apache.catalina.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class StudyTests {

	@Autowired private StudyRoomService studyRoomService;
	@Autowired private UsersService usersService;
	@Autowired private StudyCategoryService studyCategoryService;

	private UsersDTO defaultUser;
	private StudyRoomCategoryDTO defaultCategory;

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
	@DisplayName("모든_스터디_조회")
	@Transactional
	void findAllStudyRoom() {
		// when
		List<StudyRoomDTO> studyRooms = studyRoomService.getStudyRooms();

		// then
		assertThat(studyRooms).hasSize(2);
		assertThat(studyRooms).extracting("name").containsExactlyInAnyOrder("TestName1", "TestName2");
	}

	@Test
	@DisplayName("스터디룸_생성+채팅방_생성")
	@Transactional
	void create_studyRoom() {
		// given
		StudyRoomDTO studyRoomDTO = createRoomDTO("TestName");

		// when
		StudyRoomDTO savedRoom = studyRoomService.setStudyRoom(studyRoomDTO);

		// then
		assertThat(savedRoom.getId()).isNotNull();
		assertThat(savedRoom.getName()).isEqualTo("TestName");
	}

	@Test
	@DisplayName("스터디룸의_채팅룸(ID)_조회")
	@Transactional
	void findByChatRoomId_success() {
		// given
		StudyRoomDTO savedRoom = studyRoomService.setStudyRoom(createRoomDTO("ChatRoomTest"));

		// then
		assertThat(studyRoomService.findByChatRoomId(savedRoom.getId())).isNotNull();
	}

	@Test
	@DisplayName("특정_이름을_가진_스터디룸_검색")
	@Transactional
	void findByStudyName() {
		// given
		studyRoomService.setStudyRoom(createRoomDTO("SearchTestRoom"));

		// then
		assertThat(studyRoomService.getStudyRoom("SearchTestRoom").getName()).isEqualTo("SearchTestRoom");
	}

	@Test
	@DisplayName("특정_아이디를_가진_스터디룸_삭제")
	@Transactional
	void deleteStudyRoom(){
	    //given
		StudyRoomDTO studyRoomDTO = createRoomDTO("DeleteTestRoom");

		// when
		StudyRoomDTO savedRoom = studyRoomService.setStudyRoom(studyRoomDTO);
		studyRoomService.deleteStudyRoom(savedRoom.getId());
	    //then
		assertThat(savedRoom.getId()).isNotNull();
		assertThat(savedRoom.getChatId()).isNotNull();
	}

	@Test
	@DisplayName("스터디룸_규칙_설정_테스트")
	@Transactional
	void setStudyRoomRules(){
	    //given
		StudyRoomDTO studyRoomDTO = createRoomDTO("RulesTestRoom");
		StudyRoom studyRoom = new StudyRoom();
		String rule = "테스트 규칙";
		//when
	    StudyRoomDTO savedRoom = studyRoomService.setStudyRoom(studyRoomDTO);
		studyRoomService.setStudyRoomRules(savedRoom);
		studyRoom.updateRules(rule);
		//then
		assertThat(studyRoom.getRules()).isEqualTo("테스트 규칙");
	}

	@Test
	@DisplayName("스터디룸_참가_인원(id)_조회")
	void findAllByStudyRoomId(){
	    //given
		Users user1 = new Users(1L,"test@test","1234","test","010-1111-1111",LocalDateTime.now(), Role.USER);
	    //when

	    //then
	}
}
