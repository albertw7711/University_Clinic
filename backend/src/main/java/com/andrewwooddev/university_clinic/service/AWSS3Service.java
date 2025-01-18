package com.andrewwooddev.university_clinic.service;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.andrewwooddev.university_clinic.exception.DefaultException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

@Service
public class AWSS3Service {
  private final String BUCKET_NAME = "uni-clinic-imgs2";

  @Value("${aws.s3.access.key}")
  private String awsS3AccessKey;

  @Value("${aws.s3.secret.key}")
  private String awsS3SecretKey;

  public String uploadImg(MultipartFile photo) {
    String imgUrl;

    try {
      String s3ImgName = photo.getOriginalFilename();

      BasicAWSCredentials awsCredentials = new BasicAWSCredentials(awsS3AccessKey, awsS3SecretKey);
      AmazonS3 s3Client = AmazonS3ClientBuilder.standard()
          .withCredentials(new AWSStaticCredentialsProvider(awsCredentials))
          .withRegion(Regions.US_EAST_2)
          .build();

      InputStream inStream = photo.getInputStream();

      ObjectMetadata meta = new ObjectMetadata();
      meta.setContentType("image/jpeg");

      PutObjectRequest putRequest = new PutObjectRequest(BUCKET_NAME, s3ImgName, inStream, meta);
      s3Client.putObject(putRequest);
      return "https://" + BUCKET_NAME + ".s3.amazonaws.com/" + s3ImgName;

    } catch (Exception e) {
      e.printStackTrace();
      throw new DefaultException("Unable to upload image to s3 bucket" + e.getMessage());
    }
  }
}
