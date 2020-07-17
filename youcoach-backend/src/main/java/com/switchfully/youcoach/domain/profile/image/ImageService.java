package com.switchfully.youcoach.domain.profile.image;

import com.switchfully.youcoach.file.DBFile;
import com.switchfully.youcoach.file.DBFileService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ImageService {

    private DBFileService dbFileService;

    public ImageService(DBFileService dbFileService) {
        this.dbFileService = dbFileService;
    }

    public void uploadFile(Long profileId, MultipartFile multipartFile) {
        dbFileService.uploadFile(toProfileImageFileName(profileId), multipartFile);
    }

    private String toProfileImageFileName(Long profileId) {
        return String.format("profile_image%s.image", profileId);
    }

    public DBFile downLoadFile(Long profileId) {
        return dbFileService.downloadFile(toProfileImageFileName(profileId));
    }
}
