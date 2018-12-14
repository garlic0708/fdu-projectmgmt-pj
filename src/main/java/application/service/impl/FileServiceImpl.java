package application.service.impl;

import application.service.FileService;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Creator: DreamBoy
 * Date: 2018/12/4.
 */
@Service
public class FileServiceImpl implements FileService {
    @Override
    public void uploadFile(byte[] file, String filePath, String fileName) throws IOException {
        File targetFile = new File(filePath);
        if (!targetFile.exists()) {
            targetFile.mkdirs();
        }
        FileOutputStream out = new FileOutputStream(filePath + fileName);
        out.write(file);
        out.flush();
        out.close();
    }

    @Override
    public Resource getImage(String imagePath) throws Exception {
        return new ByteArrayResource(Files.readAllBytes(Paths.get(imagePath)));
    }
}
