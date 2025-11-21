package com.streamora.backend.stream;

import com.streamora.backend.stream.dto.StartStreamRequest;
import java.util.List;

public interface StreamService {

    Stream startStream(String userId, StartStreamRequest request);

    Stream stopStream(String userId);

    List<Stream> getActiveStreams();

    Stream addViewer(Long streamId);

    Stream removeViewer(Long streamId);

    Stream getStreamById(Long id);
}











