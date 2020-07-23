package com.switchfully.youcoach.file;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@Service
public class DBFileService {

    private DBFileRepository dbFileRepository;

    public DBFileService(DBFileRepository dbFileRepository) {
        this.dbFileRepository = dbFileRepository;
    }

    public void uploadFile(String filename, MultipartFile multipartFile) {
        try {
            dbFileRepository.save(new DBFile(filename, multipartFile.getOriginalFilename(), multipartFile.getBytes()));
        } catch (IOException e) {
            throw new RuntimeException("Could not read multipart file", e);
        }
    }

    public Optional<DBFile> downloadFile(String name) {
        return dbFileRepository.findById(name);
    }
}
