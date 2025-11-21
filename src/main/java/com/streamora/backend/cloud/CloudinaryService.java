package com.streamora.backend.cloud;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CloudinaryService {

    private final Cloudinary cloudinary;

    // =========================================
    // 🖼️ SUBIR IMAGEN (Ya existía - no se toca)
    // =========================================
    public String upload(MultipartFile file) throws IOException {
        Map uploadResult = cloudinary.uploader()
                .upload(file.getBytes(), ObjectUtils.emptyMap());

        return uploadResult.get("secure_url").toString();
    }

    // =========================================
    // 🎥 SUBIR VIDEO (Nuevo)
    // =========================================
    public String uploadVideo(MultipartFile file) throws IOException {

        Map uploadResult = cloudinary.uploader().upload(
                file.getBytes(),
                ObjectUtils.asMap(
                        "resource_type", "video" // 🔥 Necesario para videos
                )
        );

        return uploadResult.get("secure_url").toString();
    }

    // =========================================
    // 🖼️ GENERAR THUMBNAIL AUTOMÁTICO DEL VIDEO
    // =========================================
    public String generateThumbnail(String videoUrl) {
        // Cloudinary genera un frame como thumbnail
        return videoUrl.replace("/upload/", "/upload/so_1,du_1/");
    }
}



