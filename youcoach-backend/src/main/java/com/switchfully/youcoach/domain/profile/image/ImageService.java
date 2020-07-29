package com.switchfully.youcoach.domain.profile.image;

import com.switchfully.youcoach.file.DBFile;
import com.switchfully.youcoach.file.DBFileService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

@Service
public class ImageService {

    private DBFileService dbFileService;

    public ImageService(DBFileService dbFileService) {
        this.dbFileService = dbFileService;
    }

    public void saveProfileImage(Long profileId, MultipartFile multipartFile) {
        dbFileService.uploadFile(toProfileImageFileName(profileId), multipartFile);
    }

    private String toProfileImageFileName(Long profileId) {
        return String.format("profile_image%s.image", profileId);
    }

    public Optional<DBFile> getProfileImage(Long profileId) {
        return dbFileService.downloadFile(toProfileImageFileName(profileId));
    }
}
