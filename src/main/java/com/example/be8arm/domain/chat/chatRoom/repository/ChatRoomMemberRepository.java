package com.example.be8arm.domain.chat.chatRoom.repository;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.example.be8arm.domain.chat.chatRoom.entity.ChatRoomMember;

public interface ChatRoomMemberRepository extends JpaRepository<ChatRoomMember, Long> {

	@Query("SELECT e.id.chatRoomId FROM ChatRoomMember e WHERE e.id.memberId = :memberId")
	HashSet<Long> findAllByMemberIdAsSet(Long memberId);

	@Query("SELECT e.id.chatRoomId FROM ChatRoomMember e WHERE e.id.memberId = :memberId")
	List<Long> findAllByMemberIdAsList(Long memberId);

	boolean existsByChatRoomIdAndMemberId(long roomId, long memberId);

	Long deleteByChatRoomIdAndMemberId(long roomId, long memberId); //삭제된 수 리턴

	List<ChatRoomMember> findByChatRoomId(Long chatRoomId);

	Optional<ChatRoomMember> findByChatRoomIdAndMemberId(long chatRoomId, long memberId);

	Long countByChatRoomId(Long chatRoomId);

	@Transactional
	@Modifying
	@Query(value = "UPDATE ChatRoomMember crm SET crm.lastViewMessageId = (SELECT MAX(cm.id) FROM ChatMessage cm WHERE cm.chatRoom.id = :chatRoomId) WHERE crm.chatRoom.id = :chatRoomId")
	void updateLastViewMessageIdWhenEnterRoom(Long chatRoomId);

	@Transactional
	@Modifying
	@Query("UPDATE ChatRoomMember crm SET crm.lastViewMessageId = :lastMessageId WHERE crm.id.chatRoomId = :roomId")
	void setLastViewMessageIdToCurrentIdByRoomId(long roomId, Long lastMessageId);
}

