package com.easyshare.socketio;

import com.corundumstudio.socketio.SocketIOClient;
import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;

@Component
public class SocketIOManager {
    private static ConcurrentHashMap<Long, SocketIOClient> clientsMap = new ConcurrentHashMap<>();

    public void addClient(Long userId, SocketIOClient client) {
        clientsMap.put(userId, client);
    }

    public SocketIOClient getClient(Long userId) {
        return clientsMap.get(userId);
    }

    public SocketIOClient removeClient(Long userId) {
        SocketIOClient client = clientsMap.remove(userId);
        return client;
    }
}
