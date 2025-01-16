package moonduck.server.service.s3;

import lombok.extern.slf4j.Slf4j;
import moonduck.server.exception.ErrorCode;
import moonduck.server.exception.ErrorException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.ObjectCannedACL;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
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

    public List<String> uploadFiles(MultipartFile[] files, Long userId) {
        if (files == null || files.length == 0) {
            return null;
        }
        try {
            List<String> keys = new ArrayList<>();
            for (MultipartFile file : files) {
                if (file != null && !file.isEmpty()) {
                    String key = uploadFile(file, userId);
                    keys.add(key);
                }
            }
            return keys;
        } catch (IOException e) {
            throw new ErrorException(ErrorCode.FILE_ERROR);
        }
    }

    public void deleteFile(String key) {
        String deleteKey = extractKeyFromUrl(key);

        DeleteObjectRequest deleteObjectRequest = DeleteObjectRequest.builder()
                .bucket(bucketName)
                .key(deleteKey)
                .build();

        s3Client.deleteObject(deleteObjectRequest);
    }

    public void deleteFiles(List<String> keys) {
        if (keys == null || keys.isEmpty()) {
            return;
        }
        for (String key : keys) {
            deleteFile(key);
        }
    }

    private String extractKeyFromUrl(String url) {
        // 예: https://your-endpoint/bucket-name/userId/uuidFileName.extension
        // -> userId/uuidFileName.extension
        return url.substring(url.indexOf(bucketName) + bucketName.length() + 1);
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
