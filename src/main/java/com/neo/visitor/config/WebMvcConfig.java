package com.neo.visitor.config;

import java.util.Arrays;
import java.util.List;

import com.neo.visitor.config.intercepter.UserInfoIntercepter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    // @Value("${img.savePath}")
	// private String SAVE_PATH;

    @Autowired
	@Qualifier(value = "userInfoIntercepter")
	private UserInfoIntercepter interceptor;

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
        List<String> excludePathPatterns =
            Arrays.asList("/static/css/**", "/static/js/**", "/static/imgaes/**", "/login", "/upload/**", "/mailsend", "/smssend", "/host-list");
        registry.addInterceptor(interceptor)
                .addPathPatterns("/**")
		 		.excludePathPatterns(excludePathPatterns);
    }
    
    // 파일업로드 시 저장 될 경로 설정
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        //registry.addResourceHandler("/upload/**").addResourceLocations("file:///C:/upload/test/");
        registry.addResourceHandler("/upload/**").addResourceLocations("file:///D:/tomcat/upload/");
    }
}