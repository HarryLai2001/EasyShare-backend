package com.easyshare.utils;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.PutObjectRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.InputStream;

@Component
public class AliyunOssUtils {
    @Value("${aliyun.oss.endpoint}")
    private String endpoint;
    @Value("${aliyun.oss.accessKeyId}")
    private String accessKeyId;
    @Value("${aliyun.oss.accessKeySecret}")
    private String accessKeySecret;
    @Value("${aliyun.oss.bucketName}")
    private String bucketName;

    public String upload(String objectName, InputStream inputStream) throws Exception {
        // 创建OSSClient实例
        OSS ossClient = new OSSClientBuilder().build("https://" + endpoint, accessKeyId, accessKeySecret);
        // 创建PutObjectRequest对象
        PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, objectName, inputStream);
        // 上传文件
        ossClient.putObject(putObjectRequest);
        String url = "https://" + bucketName + "." + endpoint + "/" + objectName;
        return url;
    }
}
