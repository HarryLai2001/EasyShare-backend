package com.easyshare;

import com.easyshare.utils.AliyunOssUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

@SpringBootTest
public class UploadAvatarTest {
    @Autowired
    AliyunOssUtils aliyunOssUtils;

    @Test
    public void UploadDefaultAvatarTest() throws Exception {
        File avatar = new File("D:\\EasyShare(react+springboot+electron)\\avatar_img\\default-avatar.png");
        InputStream avatarInputStream = new FileInputStream(avatar);
        String url = aliyunOssUtils.upload("avatar/default-avatar.png", avatarInputStream);
        System.out.println(url);
    }
}
