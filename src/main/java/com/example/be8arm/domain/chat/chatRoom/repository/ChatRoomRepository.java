package com.example.be8arm.domain.chat.chatRoom.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.be8arm.domain.chat.chatRoom.dto.ChatRoomDetailDto;
import com.example.be8arm.domain.chat.chatRoom.entity.ChatRoom;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {

	@Query("""
		SELECT new com.example.be8arm.domain.chat.chatRoom.dto.ChatRoomDetailDto(
			crm.chatRoom.id,
			crm.chatRoomName,
			crm.imgUrl,
			cm.content,
			cm.writerName,
			cm.createDate,
			(
		 		SELECT COUNT(cm2)
		 		FROM ChatMessage cm2
		 		WHERE cm2.chatRoom.id = crm.chatRoom.id AND cm2.id > crm.lastViewMessageId))
		 FROM ChatRoomMember crm
		 LEFT JOIN crm.chatRoom.chatMessages cm
		 WHERE crm.member.id = :memberId
		 AND cm.id = (SELECT MAX(cm3.id)
		 	FROM ChatMessage cm3
		 	WHERE cm3.chatRoom.id = crm.chatRoom.id)
		 ORDER BY cm.id DESC
		""")
	List<ChatRoomDetailDto> findChatRoomDetailsByMemberId(@Param("memberId") Long memberId);
}
