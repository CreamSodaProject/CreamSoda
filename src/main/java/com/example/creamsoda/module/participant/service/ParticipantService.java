package com.example.creamsoda.module.participant.service;

import com.example.creamsoda.module.participant.model.Participant;
import com.example.creamsoda.module.participant.model.ParticipantRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class ParticipantService {

   private final ParticipantRepository participantRepository;


    public ParticipantService(ParticipantRepository participantRepository) {
        this.participantRepository = participantRepository;
    }

    public Optional<Participant> getParticipant(Integer id) {
        return participantRepository.findById(id);
    }
}
