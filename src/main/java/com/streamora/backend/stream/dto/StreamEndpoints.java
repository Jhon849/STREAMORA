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

    private String rtmpUrl;       // rtmp://stream.livepush.io/live/
    private String streamKey;     // clave única del streamer
    private String playerUrl;     // https://player.livepush.io/?token=...
    private String thumbnailUrl;  // thumbnail generado por Livepush
    private String playbackUrl;   // HLS o grabación
}
