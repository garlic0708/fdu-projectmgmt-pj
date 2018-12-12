package application.service.impl;

import application.service.FileService;
import org.springframework.stereotype.Service;

import java.io.*;

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
    public byte[] getImage(String imagePath) throws Exception {
        byte[] buffer = null;

        File file = new File(imagePath);
        FileInputStream fis = new FileInputStream(file);
        ByteArrayOutputStream bos = new ByteArrayOutputStream(1000);
        byte[] b = new byte[1000];
        int n;
        while ((n = fis.read(b)) != -1) {
            bos.write(b, 0, n);
        }
        fis.close();
        bos.close();
        buffer = bos.toByteArray();
        return buffer;
    }
}
