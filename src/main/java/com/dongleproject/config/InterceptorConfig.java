package com.dongleproject.config;

import com.dongleproject.utils.EncryptUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class InterceptorConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new CustomInterceptor())
                .addPathPatterns("/**") // 拦截所有请求
                .excludePathPatterns("/demo/**") // 放行 /demo 下的所有请求

                // 放行 Knife4j 相关接口
                .excludePathPatterns("/doc.html") // 放行Swagger UI页面
                .excludePathPatterns("/webjars/**") // 放行Swagger相关静态资源
                .excludePathPatterns("/swagger-resources/**") // 放行Swagger资源配置
                .excludePathPatterns("/v3/api-docs/**"); // 放行API文档接口
    }

    public static class CustomInterceptor implements HandlerInterceptor {

        @Override
        public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

            // 调用 EncryptUtil 的 checkDongle 方法
            String result = EncryptUtil.checkDongle();
            
            // 检查返回结果是否包含错误标志
            if (!result.startsWith("true")) {
                // 抛出自定义异常，异常信息为返回的 result 内容
                throw new RuntimeException(result);
            }
            
            // 放行请求
            return true;
        }
    }
}