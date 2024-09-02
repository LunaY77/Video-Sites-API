package com.iflove.utils;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author 苍镜月
 * @version 1.0
 * @implNote
 */
@Component
@Data
@ConfigurationProperties(prefix = "file.upload")
public class FileConfig {
    private List<String> allowPicTypes;
    private List<String> allowVideoTypes;
}
