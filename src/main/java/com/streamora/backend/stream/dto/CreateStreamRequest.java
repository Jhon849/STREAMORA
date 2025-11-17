package com.streamora.backend.stream.dto;

import lombok.Data;

@Data
public class CreateStreamRequest {
    private String title;
    private String description;
    private String category;
}
