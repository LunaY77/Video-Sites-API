package com.iflove;

import com.iflove.utils.JacksonMapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

@SpringBootApplication
@MapperScan("com.iflove.mapper")
public class MainApplication {

    public static void main(String[] args) {
        SpringApplication.run(MainApplication.class, args);
    }

    /**
     * MappingJackson2HttpMessageConverter 实现了HttpMessageConverter 接口，
     * httpMessageConverters.getConverters() 返回的对象里包含了MappingJackson2HttpMessageConverter
     * @return
     */
    @Bean
    public MappingJackson2HttpMessageConverter getMappingJackson2HttpMessageConverter() {
        return new MappingJackson2HttpMessageConverter(new JacksonMapper());
    }
}
