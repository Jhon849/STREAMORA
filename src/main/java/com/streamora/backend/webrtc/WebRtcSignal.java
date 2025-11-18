package com.streamora.backend.webrtc;

import lombok.Data;

@Data
public class WebRtcSignal {
    private Long roomId;
    private Long userId;
    private String type; // offer, answer, ice
    private String sdp;
    private Object iceCandidate;
}
