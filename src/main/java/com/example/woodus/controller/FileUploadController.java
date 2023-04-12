package com.example.woodus.controller;

import com.example.woodus.model.Image;
import com.example.woodus.service.FileService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.Deflater;

@RestController
public class FileUploadController {

    FileService fileService;

    public FileUploadController(FileService fileService){
        this.fileService=fileService;
    }

    @PostMapping("/images")
    public Long uploadImage(@RequestParam("imageFile")MultipartFile file) throws IOException {
        Image image = new Image(file.getOriginalFilename(),file.getContentType(),compressBytes(file.getBytes()));
        return fileService.addImage(image);
    }

    public static byte[] compressBytes(byte[] data){
        Deflater deflater = new Deflater();
        deflater.setInput(data);
        deflater.finish();

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
        byte[] buffer = new byte[1024];
        while (!deflater.finished()) {
            int count = deflater.deflate(buffer);
            outputStream.write(buffer,0,count);

            try{
                outputStream.close();
            }catch (IOException e){ }
        }
        return outputStream.toByteArray();
    }
}