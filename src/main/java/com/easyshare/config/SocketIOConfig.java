package com.easyshare.config;

import com.corundumstudio.socketio.*;
import com.corundumstudio.socketio.annotation.SpringAnnotationScanner;
import com.corundumstudio.socketio.protocol.JacksonJsonSupport;
import com.easyshare.listener.SocketIoAuthListener;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SocketIOConfig {
    @Value("${socketio.host}")
    private String host;
    @Value("${socketio.port}")
    private Integer port;
    @Value("${socketio.bossCount}")
    private Integer bossCount;
    @Value("${socketio.workCount}")
    private Integer workCount;
    @Value("${socketio.allowCustomRequests}")
    private Boolean allowCustomRequests;
    @Value("${socketio.upgradeTimeout}")
    private Integer upgradeTimeout;
    @Value("${socketio.pingTimeout}")
    private Integer pingTimeout;
    @Value("${socketio.pingInterval}")
    private Integer pingInterval;
    @Value("${socketio.maxFramePayloadLength}")
    private Integer maxFramePayloadLength;
    @Value("${socketio.maxHttpContentLength}")
    private Integer maxHttpContentLength;

    @Autowired
    private SocketIoAuthListener socketIoAuthListener;

    @Bean
    public SocketIOServer socketIOServer() {
        SocketConfig socketConfig = new SocketConfig();
        socketConfig.setTcpNoDelay(true);
        socketConfig.setSoLinger(0);
        com.corundumstudio.socketio.Configuration config = new com.corundumstudio.socketio.Configuration();
        config.setSocketConfig(socketConfig);
        config.setHostname(host);
        config.setPort(port);
        config.setMaxFramePayloadLength(maxFramePayloadLength);
        config.setMaxHttpContentLength(maxHttpContentLength);
        config.setBossThreads(bossCount);
        config.setWorkerThreads(workCount);
        config.setAllowCustomRequests(allowCustomRequests);
        config.setUpgradeTimeout(upgradeTimeout);
        config.setPingTimeout(pingTimeout);
        config.setPingInterval(pingInterval);
        config.setAuthorizationListener(socketIoAuthListener);
        config.setJsonSupport(new JacksonJsonSupport(new JavaTimeModule()));
        SocketIOServer server = new SocketIOServer(config);
        return server;
    }

    /* 用于扫描netty-socketio的注解，比如 @OnConnect、@OnEvent */
    @Bean
    public SpringAnnotationScanner springAnnotationScanner(SocketIOServer socketIOServer) {
        return new SpringAnnotationScanner(socketIOServer);
    }
}
