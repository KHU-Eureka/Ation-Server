package com.eureka.ationserver.service;

import com.eureka.ationserver.dto.chat.SocketChatRequest;
import com.eureka.ationserver.dto.chat.SocketChatResponse;
import com.eureka.ationserver.model.lounge.Lounge;
import com.eureka.ationserver.model.lounge.LoungeChat;
import com.eureka.ationserver.model.persona.Persona;
import com.eureka.ationserver.repository.lounge.LoungeChatRepository;
import com.eureka.ationserver.repository.lounge.LoungeRepository;
import com.eureka.ationserver.repository.persona.PersonaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ChatService {

  private final LoungeChatRepository loungeChatRepository;
  private final PersonaRepository personaRepository;
  private final LoungeRepository loungeRepository;

  @Transactional
  public SocketChatResponse sendChat(Long loungeId, SocketChatRequest socketChatRequest){
    Persona persona = personaRepository.getById(socketChatRequest.getPersonaId());
    Lounge lounge = loungeRepository.getById(loungeId);
    LoungeChat loungeChat = loungeChatRepository.save(
        LoungeChat.builder()
            .lounge(lounge)
            .persona(persona)
            .content(socketChatRequest.getContent())
            .build()
    );
    return new SocketChatResponse(loungeChat);
  }

}
