package moonduck.server.s3;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.ObjectCannedACL;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class S3Service {

    private final S3Client s3Client;

    @Value("${ncp.storage.bucket}")
    private String bucketName;

    @Value("${ncp.storage.endpoint}")
    private String endpoint;

    public S3Service(S3Client s3Client) {
        this.s3Client = s3Client;
    }

    public String uploadFile(MultipartFile file, Long userId) throws IOException {
        String fileExtension = getFileExtension(file.getOriginalFilename());
        String uuidFileName = UUID.randomUUID().toString() + fileExtension;

        String key = userId + "/" + uuidFileName;

        PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                .bucket(bucketName)
                .key(key)
                .contentType(file.getContentType())
                .acl(ObjectCannedACL.PUBLIC_READ)
                .build();

        s3Client.putObject(putObjectRequest, RequestBody.fromInputStream(file.getInputStream(), file.getSize()));

        return generateFileUrl(key);
    }

    public List<String> uploadFiles(MultipartFile[] files, Long userId) throws IOException {
        List<String> keys = new ArrayList<>();
        for (MultipartFile file : files) {
            String key = uploadFile(file, userId);
            keys.add(key);
        }
        return keys;
    }

    private String generateFileUrl(String key) {
        return String.format("%s/%s/%s", endpoint, bucketName, key);
    }

    private String getFileExtension(String fileName) {
        if (fileName == null || !fileName.contains(".")) {
            return "";
        }
        return fileName.substring(fileName.lastIndexOf("."));
    }
}
