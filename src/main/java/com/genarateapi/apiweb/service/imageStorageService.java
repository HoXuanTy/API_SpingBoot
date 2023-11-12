package com.genarateapi.apiweb.service;

import com.genarateapi.apiweb.models.Product;
import com.genarateapi.apiweb.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
import java.util.UUID;
import java.util.stream.Stream;
import org.apache.commons.io.FilenameUtils;

@Service
public class imageStorageService implements IStorageService{

    ProductRepository repository;
    private final Path storageFolder;
    //constructor
    @Autowired
    public imageStorageService(@Value("${image.storage.path:upload}") String storagePath){
        this.storageFolder = Paths.get(storagePath).toAbsolutePath();
        try {
            Files.createDirectories(storageFolder);
        } catch (IOException exception) {
            throw new RuntimeException("Cannot initialize storage", exception);
        }
    }
    public boolean isImageFile(MultipartFile file){
        //Let install FilenameUtils
        String fileExtension = FilenameUtils.getExtension(file.getOriginalFilename());
        return Arrays.asList(new String[] {"png", "jpg", "jpeg"})
                .contains(fileExtension.trim().toLowerCase());
    }
    @Override
    public String storeFile(MultipartFile file, String customPath) {
        try {
            if (file.isEmpty()) {
                throw new RuntimeException("Failed to store empty file");
            }

            // Check file is an image
            if (!isImageFile(file)) {
                throw new RuntimeException("You can only upload image files.");
            }

            // File must be < 5mb
            float fileSizeInMegabytes = file.getSize() / 1_000_000.0f;
            if (fileSizeInMegabytes > 5.0f) {
                throw new RuntimeException("Files must be <= 5Mb");
            }

            // File must be renamed
            String fileExtension = FilenameUtils.getExtension(file.getOriginalFilename());
            String generatedFileName = UUID.randomUUID().toString().replace("-", "") + "." + fileExtension;

            Path destinationFilePath;
            if (customPath != null && !customPath.isEmpty()) {
                // Use custom path if provided
                destinationFilePath = Paths.get(storageFolder.toString(), customPath, generatedFileName).normalize().toAbsolutePath();
            } else {
                // Use default storage folder
                destinationFilePath = this.storageFolder.resolve(generatedFileName).normalize().toAbsolutePath();
            }

            try (InputStream inputStream = file.getInputStream()) {
                Files.copy(inputStream, destinationFilePath, StandardCopyOption.REPLACE_EXISTING);
            }

            return destinationFilePath.toString();
        } catch (IOException exception) {
            throw new RuntimeException("Failed to store file", exception);
        }
    }


    @Override
    public Stream<Path> loadAll() {
        return null;
    }

    @Override
    public byte[] readFileContent(String fileName) {
       try {
           Path file = storageFolder.resolve(fileName);
           Resource resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()){
                byte[] bytes = StreamUtils.copyToByteArray(resource.getInputStream());
                return bytes;
            }
            else {
                throw  new RuntimeException(
                        "Could not read file: "+ fileName
                );
            }
       }catch (IOException exception){
           throw new RuntimeException("Could not read file: "+ fileName, exception);
       }
    }

    @Override
    public void deleteAllFiles() {

    }
}
