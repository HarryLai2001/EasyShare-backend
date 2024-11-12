package com.easyshare.resolver;

import com.alibaba.fastjson2.JSON;
import com.easyshare.annotation.RequestBodyParam;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.beanutils.ConvertUtils;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Map;

@Component
public class RequestBodyParamArgumentResolver implements HandlerMethodArgumentResolver {
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        RequestBodyParam requestBodyParam = parameter.getParameterAnnotation(RequestBodyParam.class);
        return (requestBodyParam != null && StringUtils.hasText(requestBodyParam.value()));
    }

    @Override
    public Object resolveArgument(MethodParameter parameter,
                                  ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest,
                                  WebDataBinderFactory binderFactory) throws IOException {
        HttpServletRequest servletRequest = webRequest.getNativeRequest(HttpServletRequest.class);
        String contentType = servletRequest.getContentType();
        if (!StringUtils.hasText(contentType) || !contentType.contains("application/json")) {
            throw new RuntimeException("解析参数异常，contentType需为application/json");
        }
        if (!servletRequest.getMethod().equalsIgnoreCase("post") && !servletRequest.getMethod().equalsIgnoreCase("put")) {
            throw new RuntimeException("解析参数异常，请求类型必须为post或put");
        }
        RequestBodyParam requestBodyParam = parameter.getParameterAnnotation(RequestBodyParam.class);
        Class<?> parameterType = parameter.getParameterType();
        String requestBody = getRequestBody(servletRequest);
        Map<String, Object> params = JSON.parseObject(requestBody);
        String paramName = requestBodyParam.value();
        if (paramName.isBlank()) {
            throw new RuntimeException("参数解析异常，参数名不能为空");
        }
        Object value = params.get(paramName);
        return ConvertUtils.convert(value.toString(), parameterType);
    }

    private String getRequestBody(HttpServletRequest servletRequest) throws IOException {
        BufferedReader reader = servletRequest.getReader();
        StringBuilder stringBuilder = new StringBuilder();
        String line = reader.readLine();
        while(line != null){
            stringBuilder.append(line);
            line = reader.readLine();
        }
        reader.close();
        return stringBuilder.toString();
    }
}
