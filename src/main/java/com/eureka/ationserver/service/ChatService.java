package com.eureka.ationserver.service;

import com.eureka.ationserver.dto.socket.LoungeSocketDto;
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
  public LoungeSocketDto.ChatOut sendChat(Long loungeId, LoungeSocketDto.ChatIn in) {
    Persona persona = personaRepository.getById(in.getPersonaId());
    Lounge lounge = loungeRepository.getById(loungeId);
    LoungeChat loungeChat = loungeChatRepository.save(
        LoungeChat.builder()
            .lounge(lounge)
            .persona(persona)
            .content(in.getContent())
            .build()
    );
    return LoungeSocketDto.toChatOut(loungeChat);
  }

}
