package com.streamora.backend.moderation;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ModerationLogRepository extends JpaRepository<ModerationLog, Long> {
}
