package com.example.be8arm.domain.chat.chatRoom.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.be8arm.domain.chat.chatRoom.dto.ChatRoomListDto;
import com.example.be8arm.domain.chat.chatRoom.dto.ChatRoomMemberInfoDto;
import com.example.be8arm.domain.chat.chatRoom.entity.ChatRoom;
import com.example.be8arm.domain.chat.chatRoom.entity.ChatRoomMember;
import com.example.be8arm.domain.chat.chatRoom.entity.ChatRoomMemberId;
import com.example.be8arm.domain.chat.chatRoom.repository.ChatRoomMemberRepository;
import com.example.be8arm.domain.chat.chatRoom.repository.ChatRoomRepository;
import com.example.be8arm.domain.member.member.entity.Member;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ChatRoomService {
	private final ChatRoomRepository chatRoomRepository;
	private final ChatRoomMemberRepository chatRoomMemberRepository;

	public List<ChatRoomListDto> findByMemberId(Long memberId) {
		List<Object[]> results = chatRoomRepository.findChatRoomsAndLatestMessageByMemberId(memberId);
		List<ChatRoomListDto> chatRooms = new ArrayList<>();

		for (Object[] result : results) {
			chatRooms.add(new ChatRoomListDto(
				(Long)result[0], // chatRoomId
				(String)result[1], // name
				(Long)result[2], // latestMessageId
				(String)result[3], // lastMessage
				(LocalDateTime)result[4],//lastDate
				(String)result[5], //lastWriter
				(String)result[6] //imgUrl
			));
		}
		return chatRooms;
	}

	public Long findChatRoom(Long myId, Long theirId) {
		HashSet<Long> myRoomIdSet = chatRoomMemberRepository.findAllByMemberIdAsSet(myId);
		List<Long> theirRoomIdList = chatRoomMemberRepository.findAllByMemberIdAsList(theirId);

		//상대와 중복되는 채팅방이 있는지
		for (Long theirRoomId : theirRoomIdList) {
			if (myRoomIdSet.contains(theirRoomId))
				return theirRoomId;
		}
		return -1L; //채팅방이 없음
	}

	@Transactional
	public Long makeChatRoom(ChatRoomMemberInfoDto myInfoDto, ChatRoomMemberInfoDto theirInfoDto) {
		ChatRoom chatRoom = ChatRoom.builder().build();

		Long chatRoomId = chatRoomRepository.save(chatRoom).getId();
		//복합키 생성
		ChatRoomMemberId myRoomId = ChatRoomMemberId.builder()
			.chatRoomId(chatRoomId)
			.memberId(myInfoDto.getId())
			.build();

		ChatRoomMemberId theirRoomId = ChatRoomMemberId.builder()
			.chatRoomId(chatRoomId)
			.memberId(theirInfoDto.getId())
			.build();

		//중간 테이블 데이터 생성
		String chatRoomName = theirInfoDto.getName() + ", " + myInfoDto.getName();

		ChatRoomMember myChatRoomMember = ChatRoomMember.builder()
			.id(myRoomId)
			.member(memberRepository.findById(myInfoDto.getId())
				.orElseThrow(() -> new RuntimeException("Member not found"))) // 멤버 엔티티 참조 설정
			.chatRoom(chatRoom)
			.chatRoomName(chatRoomName)
			.imgUrl(theirInfoDto.getImgUrl())
			.build();

		ChatRoomMember theirChatRoomMember = ChatRoomMember.builder()
			.id(theirRoomId)
			.member(memberRepository.findById(theirInfoDto.getId())
				.orElseThrow(() -> new RuntimeException("Member not found"))) // 멤버 엔티티 참조 설정
			.chatRoom(chatRoom)
			.chatRoomName(chatRoomName)
			.imgUrl(myInfoDto.getimgUrl())
			.build();

		chatRoomMemberRepository.save(myChatRoomMember);
		chatRoomMemberRepository.save(theirChatRoomMember);

		return chatRoomId;
	}

	public Optional<ChatRoom> findById(long roomId) {
		return chatRoomRepository.findById(roomId);
	}

	public boolean isIncludeMe(long memberId, long roomId) {
		return chatRoomMemberRepository.existsByChatRoomIdAndMemberId(roomId, memberId);
	}

	public ChatRoomMemberInfoDto createInfoDtoByEmail(String email) {
		Member member = memberRepository.findByEmail(email)
			.orElseThrow(() -> new NoSuchElementException("email이 존재하지 않습니다."));

		return new ChatRoomMemberInfoDto(member.getId(), member.getName(), member.getImgUrl());
	}

	public ChatRoomMember findChatRoomMemberByChatRoomIdAndMemberId(long memberId, long roomId) {
		return chatRoomMemberRepository.findChatRoomMemberByChatRoomIdAndMemberId(roomId, memberId)
			.orElseThrow(() -> new NoSuchElementException("채팅방 정보가 존재하지 않습니다."));
	}

	@Transactional
	public boolean deleteChatRoomByMemberIdAndChatRoomId(long memberId, long chatRoomId) {
		//삭제된 chatRoomMember의 개수를 리턴해서 0보다 크다면 삭제되었다고 판단
		Long successCode = chatRoomMemberRepository.deleteChatRoomMemberByChatRoomIdAndMemberId(chatRoomId, memberId);

		if (countMemberInChatRoom(chatRoomId) == 0) {
			chatRoomRepository.deleteById(chatRoomId);
			//채팅방에 남은 멤버가 없으면 채팅방을 제거
		}
		return successCode > 0;
	}

	public Long countMemberInChatRoom(long chatRoomId) {
		return chatRoomMemberRepository.countByChatRoomId(chatRoomId);
	}

	@Transactional
	public void modifyChatRoomName(long memberId, long chatRoomId, String chatRoomName) {
		ChatRoomMember chatRoomMember = chatRoomMemberRepository.findChatRoomMemberByChatRoomIdAndMemberId(chatRoomId,
			memberId).orElseThrow();
		chatRoomMember.setChatRoomName(chatRoomName);
		chatRoomMemberRepository.save(chatRoomMember);
	}
}
