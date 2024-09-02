package com.iflove.videositesapi;

import com.iflove.utils.FileUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class VideoSitesApiApplicationTests {

    @Autowired
    private FileUtil fileUtil;

    private MultipartFile testFile;

    @BeforeEach
    public void setUp() throws IOException {
        // 创建一个测试文件
        File file = new File("C:/Users/IFLOVE/Pictures/物语/1.jpg");
        testFile = new MockMultipartFile("test-image.jpg", "test-image.jpg", "image/jpeg", new FileInputStream(file));
    }

    @Test
    public void testSaveUserAvatar() {
        String contentType = testFile.getContentType();
        System.out.println(contentType);
        assertNotNull(contentType);
        // 确认 testFile 不为 null
        assertNotNull(testFile, "测试文件不能为 null");

        String savedFilePath = fileUtil.saveFile(testFile);

        // 断言文件保存路径不为空
        assertNotNull(savedFilePath, "文件保存路径不能为 null");

        // 断言文件确实存在
        File savedFile = new File(savedFilePath);
        assertTrue(savedFile.exists(), "保存的文件不存在");
        System.out.println(savedFilePath);
        // 清理测试文件
         savedFile.delete();
    }
}
