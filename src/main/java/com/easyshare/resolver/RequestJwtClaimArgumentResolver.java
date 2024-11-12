package com.easyshare.resolver;

import com.easyshare.annotation.RequestJwtClaim;
import com.easyshare.utils.JwtUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.util.Map;

@Component
public class RequestJwtClaimArgumentResolver implements HandlerMethodArgumentResolver {
    @Autowired
    private JwtUtils jwtUtils;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        RequestJwtClaim requestJwtClaim = parameter.getParameterAnnotation(RequestJwtClaim.class);
        return (requestJwtClaim != null && StringUtils.hasText(requestJwtClaim.value()));
    }

    @Override
    public Object resolveArgument(MethodParameter parameter,
                                  ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest,
                                  WebDataBinderFactory binderFactory) {
        RequestJwtClaim requestJwtClaim = parameter.getParameterAnnotation(RequestJwtClaim.class);
        String token = webRequest.getHeader("Authorization");
        Map<String, Object> claims = jwtUtils.parseToken(token);
        Object paramValue = claims.get(requestJwtClaim.value());
        if (paramValue == null) {
            throw new RuntimeException("解析结果中未找到相应字段");
        }
        Class<?> parameterType = parameter.getParameterType();
        return ConvertUtils.convert(paramValue.toString(), parameterType);
    }
}
