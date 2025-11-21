package com.streamora.backend.stream;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StreamEndpoints {

    private String rtmpUrl;        // <--- corregido
    private String streamKey;

    private String playerUrl;     
    private String thumbnailUrl;  
    private String playbackUrl;   
}




