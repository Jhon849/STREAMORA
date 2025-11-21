package com.streamora.backend.stream;

import com.streamora.backend.stream.dto.StreamEndpoints;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Service
@RequiredArgsConstructor
@Slf4j
public class LivepushService {

    @Value("${livepush.rtmp-url}")
    private String baseRtmpUrl;

    @Value("${livepush.player-url}")
    private String basePlayerUrl;

    @Value("${livepush.thumbnail-url}")
    private String baseThumbnailUrl;

    @Value("${livepush.playback-url}")
    private String playbackBaseUrl;

    // ======================
    // 🔥 Generate endpoints
    // ======================
    public StreamEndpoints getEndpoints(String userId, String streamKey) {
        try {
            String encodedKey = URLEncoder.encode(streamKey, StandardCharsets.UTF_8);

            String rtmpUrl = baseRtmpUrl;  
            String playerUrl = basePlayerUrl + encodedKey;
            String thumbnailUrl = baseThumbnailUrl + encodedKey + ".jpg";
            String playbackUrl = playbackBaseUrl + encodedKey + "/index.m3u8";

            return StreamEndpoints.builder()
                    .rtmpUrl(rtmpUrl)
                    .streamKey(streamKey)
                    .playerUrl(playerUrl)
                    .thumbnailUrl(thumbnailUrl)
                    .playbackUrl(playbackUrl)
                    .build();

        } catch (Exception e) {
            log.error("Error creating stream endpoints: {}", e.getMessage());
            throw new RuntimeException("Failed to generate endpoints");
        }
    }
}


