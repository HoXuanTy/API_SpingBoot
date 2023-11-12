package com.genarateapi.apiweb.controller;

import com.genarateapi.apiweb.service.IStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import com.genarateapi.apiweb.models.ResposeObject;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Controller
@RequestMapping(path = "/api/v1/fileupload")
public class FileUploadController {
    // Inject storage service here
    @Autowired
    private IStorageService storageService;
    @PostMapping("")
    public ResponseEntity<ResposeObject> uploadFile(@RequestParam("file")MultipartFile file){
        try{
            String customPath = "anhgai";
            //save file to folder -> user a service
            String generatedFileName = storageService.storeFile(file, customPath);
            return  ResponseEntity.status(HttpStatus.OK).body(
                    new ResposeObject("ok","upload file successfully", generatedFileName)
            );
        }catch (Exception exception){
            return  ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(
                    new ResposeObject("ok", exception.getMessage(), "")
            );
        }
    }

    // get image url
    @GetMapping("/files/{fileName:.+}")
    public ResponseEntity<byte[]> readDetailFile(@PathVariable String fileName){
        try {
            byte[] bytes = storageService.readFileContent(fileName);
            return  ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(bytes);
        }catch (Exception exception){
            return ResponseEntity.noContent().build();
        }
    }

}
