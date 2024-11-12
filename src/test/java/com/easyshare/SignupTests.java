package com.easyshare;

import com.easyshare.controller.UserController;
import com.easyshare.pojo.dto.SignupDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.*;

@SpringBootTest
class SignupTests {
    @Autowired
    private UserController userController;

    @Test
    public void signupTest() throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new FileReader("D:\\EasyShare(react+springboot+electron)\\test-data\\user_data.txt"));
        String line = bufferedReader.readLine();   // 跳过第一行表头
        String[] arr;
        SignupDto user = new SignupDto();
        while ((line = bufferedReader.readLine()) != null) {
            arr = line.split("\t");
            user.setUsername(arr[0]);
            user.setEmail(arr[1]);
            user.setPassword(arr[2]);
            userController.signup(user);
        }
    }
}



