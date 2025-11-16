package com.streamora.backend.config;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Configuration
public class CloudinaryConfig {

    @Bean
    public Cloudinary cloudinaryConfig() {
        Map<String, String> config = ObjectUtils.asMap(
                "cloud_name", "dwb2dsar7",
                "api_key", "744373562112669",
                "api_secret", "5pWysNIFMzygLBUkWNUKKF-3yuw",
                "secure", true
        );

        return new Cloudinary(config);
    }
}
