package moonduck.server.controller;

import lombok.RequiredArgsConstructor;
import moonduck.server.s3.S3Service;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/test/file")
@RequiredArgsConstructor
public class TestFileController {

    private final S3Service s3Service;

    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) {
        try {
            String key = s3Service.uploadFile(file);
            return ResponseEntity.ok("파일 업로드 완료 key : " + key);
        } catch (IOException e) {
            return ResponseEntity.status(500).body("파일 업로드 실패 : " + e.getMessage());
        }
    }
}