package com.easyshare.socketio;

import com.corundumstudio.socketio.SocketIOServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class SocketIOServerRunner implements CommandLineRunner {
    @Autowired
    private SocketIOServer socketIOServer;

    /* 项目启动时，自动启动socket服务，服务端开始工作 */
    @Override
    public void run(String... args) throws Exception {
        socketIOServer.start();
    }
}
