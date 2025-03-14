package com.Master.Auction.Service;

import com.amazonaws.services.s3.model.DeleteObjectRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.multipart.MultipartRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.stereotype.Service;
import com.amazonaws.services.s3.AmazonS3;
import com.Master.Auction.Config.S3Config;
import lombok.RequiredArgsConstructor;
import java.io.InputStream;
import java.io.IOException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ImageService {
    private final S3Config s3Config;
    private final AmazonS3 amazonS3;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    public String imageUpload(MultipartRequest request) throws IOException {

        MultipartFile file = request.getFile("upload");

        String s3Url = imageUploadFromFile(file);

        return s3Url;
    }

    public String imageUploadFromFile(MultipartFile file) throws IOException {

        String fileName = file.getOriginalFilename();
        String ext = fileName.substring(fileName.lastIndexOf("."));

        String uuidFileName = UUID.randomUUID() + ext;

        InputStream inputStream = file.getInputStream();

        s3Config.amazonS3Client().putObject(
                new PutObjectRequest(bucket, uuidFileName, inputStream, null));

        String s3Url = s3Config.amazonS3Client().getUrl(bucket, uuidFileName).toString();

        inputStream.close();

        return s3Url;
    }

    public void deleteImage(String fileName) {
        amazonS3.deleteObject(new DeleteObjectRequest(bucket, fileName));
    }
}
