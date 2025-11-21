package com.streamora.backend.stream;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface StreamRepository extends JpaRepository<Stream, Long> {

    Stream findByUserIdAndLiveTrue(String userId);

    List<Stream> findByLiveTrue();
}












