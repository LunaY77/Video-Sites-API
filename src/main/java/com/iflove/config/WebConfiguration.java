package com.iflove.config;

import com.iflove.utils.JacksonMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

/**
 * @author 苍镜月
 * @version 1.0
 * @implNote
 */
@Configuration
public class WebConfiguration implements WebMvcConfigurer {
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Autowired
    private HttpMessageConverters httpMessageConverters;

    @Override
    public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.addAll(httpMessageConverters.getConverters());
    }
}
