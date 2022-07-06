package org.example.config;

import cn.hutool.core.lang.Assert;
import lombok.extern.slf4j.Slf4j;
import org.example.client.JwtClient;
import org.example.common.Constant;
import org.example.common.CurrentUser;
import org.example.common.UserInfo;
import org.example.utils.UserInfoUtil;
import org.jetbrains.annotations.NotNull;
import org.slf4j.interceptors.MDCHandlerInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * web配置
 *
 * @author: 张鹏
 * @date: 2022/1/4 20:34
 */
@Slf4j
@Configuration
public class WebMcvConfig implements WebMvcConfigurer {

    @Autowired
    private JwtClient jwtClient;


    /**
     * 实现拦截器
     *
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new MDCHandlerInterceptor())
                .addPathPatterns("/**");
        registry.addInterceptor(interceptors())
                .addPathPatterns("/**")
                .excludePathPatterns("/doc.html", "/webjars/**", "/favicon.ico", "/swagger-resources/**");
    }


    /**
     * 参数解析器
     *
     * @param resolvers
     */
    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(argumentResolvers());
    }


    /**
     * 自定义拦截器
     *
     * @return
     */
    private HandlerInterceptor interceptors() {
        return new HandlerInterceptor() {
            @Override
            public boolean preHandle(@NotNull HttpServletRequest request, @NotNull HttpServletResponse response, @NotNull Object handler) throws Exception {
                List<String> whiteList = jwtClient.white();
                String path = request.getRequestURI();
                // 判断是否白名单
                if (whiteList.contains(path)) {
                    return true;
                }
                // 获取token
                String token = request.getHeader(Constant.TOKEN);
                Assert.notBlank(token, "未登录");
                // 解析
                UserInfo userInfo = jwtClient.parseToken(token);
                userInfo.setPath(path);
                // 鉴权
                jwtClient.security(userInfo);
                // 储存到隔离线程
                UserInfoUtil.set(userInfo);
                // 放行
                log.info("userId: {}, username: {}, path: {}", userInfo.getId(), userInfo.getUsername(), userInfo.getPath());
                return true;
            }

            @Override
            public void afterCompletion(@NotNull HttpServletRequest request, @NotNull HttpServletResponse response, @NotNull Object handler, Exception ex) throws Exception {
                // 删除用户信息,防止内存泄露
                UserInfoUtil.remove();
            }
        };
    }


    /**
     * 自定义参数解析器
     *
     * @return
     */
    private HandlerMethodArgumentResolver argumentResolvers() {
        return new HandlerMethodArgumentResolver() {
            @Override
            public boolean supportsParameter(@NotNull MethodParameter methodParameter) {
                // 判断控制层是否存在指定注解和实体对象
                return methodParameter.hasParameterAnnotation(CurrentUser.class) && UserInfo.class.isAssignableFrom(methodParameter.getParameterType());
            }

            @Override
            public Object resolveArgument(@NotNull MethodParameter methodParameter, ModelAndViewContainer modelAndViewContainer, @NotNull NativeWebRequest nativeWebRequest, WebDataBinderFactory webDataBinderFactory) throws Exception {
                // 获取并返回用户数据
                return UserInfoUtil.get();
            }
        };
    }


}
