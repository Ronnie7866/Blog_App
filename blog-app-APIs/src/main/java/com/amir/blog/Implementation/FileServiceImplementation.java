package com.amir.blog.Implementation;

import com.amir.blog.Services.FileService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Service
public class FileServiceImplementation implements FileService {
    @Override
    public String uploadImage(String path, MultipartFile file) {
        // file name
        String name = file.getOriginalFilename();

        // generate random file name
        String randomId = UUID.randomUUID().toString();
        String imageExtention = randomId.concat(name.substring(name.lastIndexOf(".")));


        // full path
        String imagePath = path + File.separator + imageExtention;

        // create folder if not created
        File directory = new File(path);
        if (!directory.exists()) {
            directory.mkdirs();
        }

        // copy file
        try {
            Files.copy(file.getInputStream(), Paths.get(imageExtention), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return imageExtention;
    }

    @Override
    public InputStream getResource(String path, String fileName) throws FileNotFoundException {
        String fullPath = path + File.separator + fileName;
        return new FileInputStream(fullPath);
    }
}
