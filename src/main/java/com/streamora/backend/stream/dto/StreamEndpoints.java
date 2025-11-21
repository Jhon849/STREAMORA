package com.streamora.backend.stream.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StreamEndpoints {

    private String rtmpUrl;       // RTMP server
    private String streamKey;     // User unique stream key

    private String playerUrl;     // Livepush player URL
    private String thumbnailUrl;  // Thumbnail URL
    private String playbackUrl;   // HLS playback URL
}

