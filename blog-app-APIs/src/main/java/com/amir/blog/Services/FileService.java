package com.amir.blog.Services;

import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.InputStream;

public interface FileService {

    String uploadImage(String path, MultipartFile file);

    InputStream getResource(String path, String fullPath) throws FileNotFoundException;
}
