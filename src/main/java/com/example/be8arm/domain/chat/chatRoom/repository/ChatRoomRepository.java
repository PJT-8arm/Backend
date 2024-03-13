package com.example.be8arm.domain.chat.chatRoom.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.be8arm.domain.chat.chatRoom.dto.ChatRoomDetailDto;
import com.example.be8arm.domain.chat.chatRoom.entity.ChatRoom;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {

	@Query(value = """
		SELECT 
		crm.chat_room_id AS chatRoomId,
		crm.chat_room_name AS name, 
		latest_cm.max_id AS latestMessageId, 
		m.content AS lastMessage, 
		m.create_date AS lastDate, 
		m.writer_name AS lastWriter,
		crm.img_url AS imgUrl,
		(
		    SELECT COUNT(cm2.id)
		    FROM chat_message cm2
		    WHERE cm2.chat_room_id = crm.chat_room_id
		    AND cm2.id > crm.last_view_message_id
		) AS unreadMessagesCount
		FROM chat_room_member crm
		LEFT JOIN (
			SELECT cm.chat_room_id, MAX(cm.id) AS max_id
			FROM chat_message cm
			GROUP BY cm.chat_room_id
		) latest_cm 
		ON cr.id = latest_cm.chat_room_id
		LEFT JOIN chat_message m ON latest_cm.max_id = m.id
		WHERE crm.member_id = :memberId
		ORDER BY latest_cm.max_id DESC
		""", nativeQuery = true)
	List<Object[]> findChatRoomsAndLatestMessageByMemberId(@Param("memberId") Long memberId);

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
