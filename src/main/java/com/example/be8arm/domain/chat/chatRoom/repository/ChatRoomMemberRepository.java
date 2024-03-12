package com.example.be8arm.domain.chat.chatRoom.repository;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.be8arm.domain.chat.chatRoom.entity.ChatRoom;
import com.example.be8arm.domain.chat.chatRoom.entity.ChatRoomMember;

public interface ChatRoomMemberRepository extends JpaRepository<ChatRoomMember, Long> {
	List<ChatRoom> findByMemberId(Long memberId);

	@Query("SELECT e.id.chatRoomId FROM ChatRoomMember e WHERE e.id.memberId = :memberId")
	HashSet<Long> findAllByMemberIdAsSet(Long memberId);

	@Query("SELECT e.id.chatRoomId FROM ChatRoomMember e WHERE e.id.memberId = :memberId")
	List<Long> findAllByMemberIdAsList(Long memberId);

	// MemberId로 ChatRoom 목록 조회
	//    @Query("SELECT crm.chatRoom FROM ChatRoomMember crm WHERE crm.member.id = :memberId")
	//    List<ChatRoom> findChatRoomListByMemberId(Long memberId);

	boolean existsByChatRoomIdAndMemberId(long roomId, long memberId);

	Long deleteChatRoomMemberByChatRoomIdAndMemberId(long roomId, long memberId); //삭제된 수 리턴

	List<ChatRoomMember> findChatRoomMemberByChatRoomId(Long chatRoomId);

	Optional<ChatRoomMember> findChatRoomMemberByChatRoomIdAndMemberId(long chatRoomId, long memberId);

	Long countByChatRoomId(Long chatRoomId);
}
