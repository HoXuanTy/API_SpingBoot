package com.genarateapi.apiweb.service;

import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.util.stream.Stream;

public interface IStorageService {
    public String storeFile(MultipartFile file, String customPath);
    public Stream<Path> loadAll();
    public  byte[] readFileContent(String fileName);
    public  void deleteAllFiles();
}
