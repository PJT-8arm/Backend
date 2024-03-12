package com.example.be8arm.domain.chat.chatRoom.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.be8arm.domain.chat.chatRoom.entity.ChatRoom;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {

	Optional<ChatRoom> findById(Long memberId);

	@Query(value = """
		SELECT cr.id AS chatRoomId, crm.chat_room_name AS name, latest_cm.max_id AS latestMessageId, m.content AS lastMessage, m.create_date AS lastDate m.writer_name AS lastWriter, crm.img_url AS imgUrl
		                                 FROM chat_room cr
		                                 INNER JOIN chat_room_member crm ON cr.id = crm.chat_room_id
		                                 LEFT JOIN (
		                                     SELECT cm.chat_room_id, MAX(cm.id) AS max_id
		                                     FROM chat_message cm
		                                     GROUP BY cm.chat_room_id
		                                 ) latest_cm ON cr.id = latest_cm.chat_room_id
		                                 LEFT JOIN chat_message m ON latest_cm.max_id = m.id
		                                 WHERE crm.member_id = :memberId
		                                 ORDER BY latest_cm.max_id DESC
		   """, nativeQuery = true)
	List<Object[]> findChatRoomsAndLatestMessageByMemberId(@Param("memberId") Long memberId);

	//List<ChatRoomMember> findByMemberId(long memberId);
}
