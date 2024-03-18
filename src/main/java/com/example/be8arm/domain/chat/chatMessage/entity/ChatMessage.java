package com.example.be8arm.domain.chat.chatMessage.entity;

import static lombok.AccessLevel.*;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import net.minidev.json.annotate.JsonIgnore;

import com.example.be8arm.domain.chat.chatRoom.entity.ChatRoom;
import com.example.be8arm.global.IdEntity;

import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Entity
@Setter
@Getter
@AllArgsConstructor(access = PROTECTED)
@NoArgsConstructor(access = PROTECTED)
@SuperBuilder
@ToString(callSuper = true)
@EntityListeners(AuditingEntityListener.class)
public class ChatMessage extends IdEntity {
	@CreatedDate
	@Getter
	private LocalDateTime createDate;

	@ManyToOne
	@JsonIgnore
	private ChatRoom chatRoom;

	private String writerName;

	private String content;

	private Long senderId;
}
