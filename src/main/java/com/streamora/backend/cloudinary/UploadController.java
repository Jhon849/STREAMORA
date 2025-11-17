package com.streamora.backend.cloudinary;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/media")
public class UploadController {

    @Autowired
    private CloudinaryService cloudinaryService;

    @PostMapping("/upload/image")
    public String uploadImage(@RequestParam("file") MultipartFile file) throws Exception {
        return cloudinaryService.uploadImage(file);
    }

    @PostMapping("/upload/video")
    public String uploadVideo(@RequestParam("file") MultipartFile file) throws Exception {
        return cloudinaryService.uploadVideo(file);
    }
}
