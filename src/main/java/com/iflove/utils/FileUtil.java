package com.iflove.utils;

import com.iflove.entity.Const;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;

/**
 * @author 苍镜月
 * @version 1.0
 * @implNote 生成 uuid + 当前时间 作为文件名，按照年月日创建文件夹，存储文件
 */
@Component
public class FileUtil {
    @Resource
    FileConfig fileConfig;

    public String saveFile(MultipartFile file) {
        String contentType = file.getContentType();
        // 检查文件格式是否正确
        if (!fileConfig.getAllowPicTypes().contains(contentType) && !fileConfig.getAllowVideoTypes().contains(contentType)) {
            return null;
        }

        // 生成UUID避免图片名字重复
        String originalFilename = file.getOriginalFilename();
        String fileExtension = originalFilename.substring(originalFilename.lastIndexOf("."));
        String filename = UUID.randomUUID().toString() + System.currentTimeMillis() + fileExtension;
        // 创建文件夹
        String datePath = getPath(contentType);

        // 创建目录
        File directory = new File(datePath);
        if (!directory.exists()) {
            directory.mkdirs();
        }

        // 保存文件
        try {
            byte[] bytes = file.getBytes();
            Path path = Paths.get(datePath + File.separator + filename);
            Files.write(path, bytes);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        // 返回文件保存路径
        return datePath + File.separator + filename;
    }

    private String getPath(String contentType) {
        Calendar cad = Calendar.getInstance();
        String month = String.valueOf(cad.get(Calendar.MONTH) + 1);
        if (month.length() < 2) {
            month = "0" + month;
        }
        String day = String.valueOf(cad.get(Calendar.DAY_OF_MONTH));

        // 构建文件保存路径
        String basePath = fileConfig.getAllowPicTypes().contains(contentType) ? Const.PIC_ROOT : Const.VIDEO_ROOT;
        return basePath + File.separator + cad.get(Calendar.YEAR) + File.separator + month + File.separator + day;
    }
}
