package com.zcyk.Config;

import org.aspectj.apache.bcel.util.ClassPath;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.io.File;

@Configuration
@EnableWebMvc
public class MyConfiguration implements WebMvcConfigurer {
    /* */

    @Value("${allowedOriginsIP}")
    String allowedOriginsIP;




    /* *
     * 添加允许任何网址跨域
     * @param registry
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
//                .allowedOrigins("*").allowedMethods("*").maxAge(3600).allowCredentials(true);
                .allowedOrigins("*").allowedMethods("*").maxAge(3600).allowCredentials(true)
                .allowedHeaders("*")
                .exposedHeaders("token");

//                .allowedOrigins("localhost:8080/*").allowedMethods("*").maxAge(3600).allowCredentials(true);
    }



    /**
     * 功能描述：跨域
     * 开发人员： lyx
     * 创建时间： 2019/8/15 9:58
     */
   /* @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry){
                registry.addMapping("/**")
                        .allowedOrigins("*")
                        .allowedMethods("*")
                        .allowedHeaders("DNT,X-CustomHeader,Keep-Alive,User-Agent,X-Requested-With,If-Modified-Since,Cache-Control,Content-Type,Content-Range,Range,Account_Token,code")
                        .exposedHeaders("DNT,X-CustomHeader,Keep-Alive,User-Agent,X-Requested-With,If-Modified-Since,Cache-Control,Content-Type,Content-Range,Range,Account_Token,code")
                        .allowCredentials(true)
                        .maxAge(3600);
            }

        };
    }
*/


    /**
     * 静态文件映射
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        //将所有/static/** 访问都映射到classpath:/static/ 目录下
        registry.addResourceHandler("/temp/**").addResourceLocations("file:D://home//cloud//file//temp//");
        registry.addResourceHandler("/video/**").addResourceLocations("file:D://home//cloud//file//video//");
        registry.addResourceHandler("/**").addResourceLocations("classpath:/static/");

    }


}
