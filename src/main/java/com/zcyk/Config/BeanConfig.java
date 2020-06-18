package com.zcyk.Config;


import com.zcyk.filter.CustomMultipartResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.HiddenHttpMethodFilter;
import org.springframework.web.filter.HttpPutFormContentFilter;
import org.springframework.web.multipart.MultipartResolver;

/**
 * @author WuJieFeng
 * @date 2019/10/28 13:48
 */
@Configuration
public class BeanConfig {


    @Bean(name = "multipartResolver")
    public MultipartResolver multipartResolver()
    {
        return new CustomMultipartResolver();
    }

    /*put接受不到参数使用*/
    @Bean
    public HttpPutFormContentFilter httpPutFormContentFilter() {
        return new HttpPutFormContentFilter();     }

}
