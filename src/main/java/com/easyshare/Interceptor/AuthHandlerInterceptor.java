package com.easyshare.Interceptor;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONWriter;
import com.easyshare.pojo.common.Result;
import com.easyshare.pojo.common.ResultCode;
import com.easyshare.utils.JwtUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.io.IOException;
import java.util.Map;

@Component
public class AuthHandlerInterceptor implements HandlerInterceptor {
    @Autowired
    private JwtUtils jwtUtils;
    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {
        String token = request.getHeader("Authorization");
        response.setCharacterEncoding("UTF-8");
        try {
            Map<String, Object> claims = jwtUtils.parseToken(token);
            String redisToken = (String) redisTemplate.opsForValue().get("token::" + claims.get("id").toString());
            if (redisToken == null) {
                Result result = new Result(ResultCode.AUTH_FAIL.getCode(), "登录已过期", null);
                response.getWriter().println(JSON.toJSONString(result, JSONWriter.Feature.WriteMapNullValue));
                return false;
            }
            if (!redisToken.equals(token)) {
                Result result = new Result(ResultCode.AUTH_FAIL.getCode(), "非有效登录", null);
                response.getWriter().println(JSON.toJSONString(result, JSONWriter.Feature.WriteMapNullValue));
                return false;
            }
            return true;
        } catch (Exception ex) {
            Result result = new Result(ResultCode.AUTH_FAIL.getCode(), "访问失败，请重试", null);
            response.getWriter().println(JSON.toJSONString(result, JSONWriter.Feature.WriteMapNullValue));
            return false;
        }
    }
}
