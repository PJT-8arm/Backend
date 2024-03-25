package com.example.be8arm.domain.chat.chatMessage.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.be8arm.domain.chat.chatMessage.dto.ChatMessagesDto;
import com.example.be8arm.domain.chat.chatMessage.entity.ChatMessage;

public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {
	@Query(value = """
		SELECT MAX(cm.id) FROM ChatMessage cm
		WHERE cm.chatRoom.id = :roomId
		""")
	Long findLastChatMessageIdInChatRoom(long roomId);

	@Query("""
		SELECT new com.example.be8arm.domain.chat.chatMessage.dto.ChatMessagesDto(cm.id, cm.createDate, cm.writerName, cm.content, cm.senderId)
		FROM ChatMessage cm WHERE cm.chatRoom.id = :roomId AND cm.id < :lastMessageId
		ORDER BY cm.id DESC
		""")
	Slice<ChatMessagesDto> findByChatRoomIdAndIdLessThanOrderByIdDesc(long roomId, long lastMessageId,
		Pageable pageable);
}
