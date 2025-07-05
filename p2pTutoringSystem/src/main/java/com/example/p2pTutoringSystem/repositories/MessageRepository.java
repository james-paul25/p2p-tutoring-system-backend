package com.example.p2pTutoringSystem.repositories;

import com.example.p2pTutoringSystem.entities.Messages;
import com.example.p2pTutoringSystem.entities.Session;
import jakarta.mail.MessagingException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional(readOnly = true)
public interface MessageRepository extends JpaRepository<Messages, Long> {
    Optional<Messages> findByMessageId(Long id);
    Optional<Messages> findBySession(Session session);
    List<Messages> findBySession_SessionIdOrderBySendAtAsc(long sessionId);
}
