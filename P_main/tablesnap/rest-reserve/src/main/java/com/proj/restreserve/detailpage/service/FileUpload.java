package com.proj.restreserve.detailpage.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.PutObjectResult;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FileUpload {
    @Value("${cloud.aws.s3.bucketName}")
    private String bucketName; //버킷 이름
    private final AmazonS3 amazonS3;

    private File convert(MultipartFile file) throws IOException{
        File convertFile = new File(file.getOriginalFilename());
        if(convertFile.createNewFile()) { //이름 중복체크 후 파일 생성
            FileOutputStream fos = new FileOutputStream(convertFile);
            fos.write(file.getBytes());//파일에 데이터 입력
        }
        return convertFile;
    }
    public String uploadImageToS3(MultipartFile file,String useServicename,String filename) { //이미지를 S3에 업로드하고 이미지의 url을 반환
        try {
            PutObjectResult putObjectResult = amazonS3.putObject(new PutObjectRequest(
                    bucketName, useServicename + "/" +filename, convert(file)
            ).withCannedAcl(CannedAccessControlList.PublicRead));//서버에 이미지 업로드

        } catch (IOException e) {
            throw new RuntimeException("이미지 업로드 중 오류 발생: " + e.getMessage());
        }
        return amazonS3.getUrl(bucketName,useServicename + "/" + filename).toString(); //데이터베이스에 저장할 이미지가 저장된 주소
    }
}
