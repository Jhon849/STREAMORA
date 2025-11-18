package com.streamora.backend.stream.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class StartStreamRequest {
    private String title;
    private String description;
}
