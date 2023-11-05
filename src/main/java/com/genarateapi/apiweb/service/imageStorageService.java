package com.genarateapi.apiweb.service;

import com.genarateapi.apiweb.models.Product;
import com.genarateapi.apiweb.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
    @Autowired
    ProductRepository repository;
    private  final Path storageFolder = Paths.get("upload");
    //constructor
    public imageStorageService(){
        try {
            Files.createDirectories(storageFolder);
        }catch (IOException exception){
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
    public String storeFile(MultipartFile file) {
        try {
            System.out.println("haha");
            if (file.isEmpty()){
                throw new RuntimeException("Failes to store empty file");
            }
            //check file is image?
            if(!isImageFile(file)){
                throw new RuntimeException("You can only upload file.");
            }
            //file must be < 5mb
            float fileSizeInMegabytes = file.getSize()/ 1_000_000.0f;
            if(fileSizeInMegabytes>5.0f){
                throw new RuntimeException("Files must be <= 5Mb");
            }

            //file must be rename

            String fileExtension = FilenameUtils.getExtension(file.getOriginalFilename());
            String generatedFileName = UUID.randomUUID().toString().replace("-", "");
            generatedFileName = generatedFileName+"."+fileExtension;
            Path destinationFilePath = this.storageFolder.resolve(Paths.get(generatedFileName)).normalize().toAbsolutePath();
            if(!destinationFilePath.getParent().equals(this.storageFolder.toAbsolutePath())){
                //this is a security check
                throw new RuntimeException("Cannot store file outside current directory");
            }
            try(InputStream inputStream =file.getInputStream()){
                Files.copy(inputStream, destinationFilePath, StandardCopyOption.REPLACE_EXISTING);
            }
            return  generatedFileName;
        }
        catch (IOException exception){
            throw new RuntimeException("Failes to store empty file", exception);
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
