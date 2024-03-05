package com.example.be8arm.domain.chat.chatMessage.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.be8arm.domain.chat.chatMessage.entity.ChatMessage;

public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {
	List<ChatMessage> findByChatRoomIdAndIdAfter(long roomId, long afterId);
}
