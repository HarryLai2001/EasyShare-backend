package com.easyshare.listener;

import com.corundumstudio.socketio.AuthorizationListener;
import com.corundumstudio.socketio.AuthorizationResult;
import com.corundumstudio.socketio.HandshakeData;
import com.easyshare.pojo.common.Result;
import com.easyshare.pojo.common.ResultCode;
import com.easyshare.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class SocketIoAuthListener implements AuthorizationListener {
    @Autowired
    private JwtUtils jwtUtils;
    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public AuthorizationResult getAuthorizationResult(HandshakeData handshakeData) {
        String token = handshakeData.getSingleUrlParam("token");
        try {
            Map<String, Object> claims = jwtUtils.parseToken(token);
            Long userId = Long.valueOf(claims.get("id").toString());
            String redisToken = (String) redisTemplate.opsForValue().get("token::" + userId);
            if (redisToken == null || !redisToken.equals(token)) {
                return new AuthorizationResult(false);
            }
            handshakeData.getHttpHeaders().add("userId", userId);
            return new AuthorizationResult(true);
        } catch (Exception ex) {
            ex.printStackTrace();
            return new AuthorizationResult(false);
        }
    }
}
