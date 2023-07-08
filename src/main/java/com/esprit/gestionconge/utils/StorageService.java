package com.esprit.gestionconge.utils;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Random;

@Service
public class StorageService {

    private final Path rootLocation = Paths.get("upload-dir");


   /* public void store(MultipartFile file) {
        try {

           // String fileName = Integer.toString(new Random().nextInt(1000000000));
            String ext=file.getOriginalFilename().substring(file.getOriginalFilename().indexOf('.'),
                    file.getOriginalFilename().length());
            String name=file.getOriginalFilename().substring(0,file.getOriginalFilename().indexOf('.'));
            String original=name+ext;//+fileName;
            Files.copy(file.getInputStream(), this.rootLocation.resolve(original));
        } catch (Exception e) {
            throw new RuntimeException("FAIL!");
        }
    }*/

    public String store(MultipartFile file) {
        try {
            String fileName = generateRandomFileName(file.getOriginalFilename());
            Files.copy(file.getInputStream(), this.rootLocation.resolve(fileName));
            return fileName;
        } catch (Exception e) {
            throw new RuntimeException("Failed to store file!");
        }
    }

    private String generateRandomFileName(String originalFilename) {
        String name = originalFilename.substring(0, originalFilename.lastIndexOf('.'));
        String ext = originalFilename.substring(originalFilename.lastIndexOf('.'));
        String random = Integer.toString(new Random().nextInt(1000000000));
        return name + random + ext;
    }

    public Resource loadFile(String filename) {
        try {
            Path file = rootLocation.resolve(filename);
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new RuntimeException("FAIL!");
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException("FAIL!");
        }
    }


    public void deleteAll() {
        FileSystemUtils.deleteRecursively(rootLocation.toFile());
    }

    public void init() {
        try {
            Files.createDirectory(rootLocation);
        } catch (IOException e) {
            throw new RuntimeException("Could not initialize storage!");
        }
    }
}