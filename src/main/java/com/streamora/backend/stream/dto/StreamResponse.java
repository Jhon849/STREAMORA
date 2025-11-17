package com.streamora.backend.stream.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class StreamResponse {
    private Long id;
    private String title;
    private String description;
    private String category;
    private String thumbnailUrl;
    private String streamKey;
    private boolean live;
    private String rtmpUrl;
    private String hlsUrl;
}
