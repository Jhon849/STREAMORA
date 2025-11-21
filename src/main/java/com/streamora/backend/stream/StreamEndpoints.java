package com.streamora.backend.stream;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class StreamEndpoints {
    private String rtmpUrl;
    private String streamKey;
    private String playbackUrl;
}
