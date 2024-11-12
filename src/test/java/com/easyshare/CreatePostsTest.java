package com.easyshare;

import com.easyshare.controller.PostController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

@SpringBootTest
public class CreatePostsTest {
    @Autowired
    private PostController postController;

    @Test
    public void createPostTest() throws IOException {
        InputStreamReader inputStreamReader = new InputStreamReader(new FileInputStream("D:\\EasyShare(react+springboot+electron)\\test-data\\post_data.txt"), "GB2312");
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        String line = bufferedReader.readLine();   // 跳过第一行表头
        String[] arr;
        Long uid;
        String content;
        while ((line = bufferedReader.readLine()) != null) {
            arr = line.split("\t");
            uid = Long.parseLong(arr[1]);
            content = arr[0];
            postController.createPost(uid, content);
        }
    }
}
