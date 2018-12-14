package application.service;

import org.springframework.core.io.Resource;

import java.io.IOException;

/**
 * Creator: DreamBoy
 * Date: 2018/12/4.
 */
public interface FileService {
    void uploadFile(byte[] file, String filePath, String fileName) throws IOException;

    Resource getImage(String imagePath) throws Exception;
}
