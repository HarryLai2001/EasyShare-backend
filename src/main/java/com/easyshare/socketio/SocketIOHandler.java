package com.easyshare.socketio;

import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.annotation.OnConnect;
import com.corundumstudio.socketio.annotation.OnDisconnect;
import com.easyshare.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Component
public class SocketIOHandler {
    @Autowired
    private SocketIOManager socketIOManager;
    @Autowired
    private JwtUtils jwtUtils;
    @Autowired
    private RedisTemplate redisTemplate;

    @OnConnect
    public void onConnect(SocketIOClient client) {
        Long userId = Long.valueOf(client.getHandshakeData().getHttpHeaders().get("userId"));
        if (userId != null) {
            socketIOManager.addClient(userId, client);
        }
    }

    @OnDisconnect
    public void onDisconnect(SocketIOClient client) {
        Long userId = Long.valueOf(client.getHandshakeData().getHttpHeaders().get("userId"));
        SocketIOClient removedClient = socketIOManager.removeClient(userId);
    }
}
